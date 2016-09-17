package nullEngine.gl.renderer;

import math.MathUtil;
import math.Matrix4f;
import math.Vector4f;
import nullEngine.control.Application;
import nullEngine.gl.Material;
import nullEngine.gl.PostProcessing;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.framebuffer.FramebufferDeferred;
import nullEngine.gl.model.Quad;
import nullEngine.gl.shader.BasicShader;
import nullEngine.gl.shader.deferred.*;
import nullEngine.input.ResizeEvent;
import nullEngine.object.GameComponent;
import nullEngine.object.component.DirectionalLight;
import nullEngine.object.component.ModelComponent;
import nullEngine.object.component.PointLight;
import nullEngine.object.component.SpotLight;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeferredRenderer extends Renderer {

	private DeferredShader shader = DeferredBasicShader.INSTANCE;

	private static final float LOD_DROPOFF_FACTOR = 0.75f;
	private HashMap<Material, ArrayList<ModelComponent>> models = new HashMap<Material, ArrayList<ModelComponent>>();
	private Vector4f ambientColor = new Vector4f();
	private ArrayList<DirectionalLight> directionalLights = new ArrayList<DirectionalLight>();
	private ArrayList<PointLight> pointLights = new ArrayList<PointLight>();
	private ArrayList<SpotLight> spotLights = new ArrayList<SpotLight>();

	private FramebufferDeferred dataBuffer;
	private Framebuffer2D lightBuffer;
	private float far;
	private float near;

	private boolean wireframe = false;

	private ArrayList<PostProcessing> postFX = new ArrayList<PostProcessing>();

	public DeferredRenderer(int width, int height, float far, float near) {
		dataBuffer = new FramebufferDeferred(width, height);
		lightBuffer = new Framebuffer2D(width, height);
		this.far = far;
		this.near = near;
	}

	@Override
	public void add(GameComponent component) {
		if (component instanceof ModelComponent) {
			ModelComponent model = (ModelComponent) component;
			ArrayList<ModelComponent> list = models.get(model.getMaterial());
			if (list == null) {
				models.put(model.getMaterial(), list = new ArrayList<ModelComponent>());
			}
			list.add(model);
		} else if (component instanceof DirectionalLight) {
			directionalLights.add((DirectionalLight) component);
		} else if (component instanceof PointLight) {
			pointLights.add((PointLight) component);
		} else if (component instanceof SpotLight) {
			spotLights.add((SpotLight) component);
		}
	}

	@Override
	public void postRender() {
		dataBuffer.bind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		if (wireframe) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
		for (Map.Entry<Material, ArrayList<ModelComponent>> components : models.entrySet()) {
			shader = components.getKey().getShader();
			shader.bind();
			shader.loadMaterial(components.getKey());

			for (ModelComponent model : components.getValue()) {
				setModelMatrix(model.getParent().getTransform().getMatrix());
				Vector4f pos = getViewMatrix().transform(model.getParent().getTransform().getWorldPos(), (Vector4f) null);
				float radius = modelMatrix.transform(new Vector4f(model.getModel().getRadius(), 0, 0, 0)).length();
				pos.z += radius;
				if (-pos.z <= far || components.getKey().isAlwaysRender()) {
					int lod = MathUtil.clamp(
							(int) Math.floor(Math.pow(-pos.z / far, LOD_DROPOFF_FACTOR) * model.getModel().getLODCount()) + model.getLodBias(),
							0, model.getModel().getLODCount() - 1);
					model.getModel().render(lod);
				}
			}
		}
		if (wireframe) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
		models.clear();

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		dataBuffer.unbind();

		lightBuffer.bind();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

		DeferredAmbientShader.INSTANCE.bind();
		DeferredAmbientShader.INSTANCE.loadAmbientColor(ambientColor);

		dataBuffer.render();

		DeferredDirectionalLightShader.INSTANCE.bind();
		DeferredDirectionalLightShader.INSTANCE.loadViewMatrix(viewMatrix);
		for (DirectionalLight light : directionalLights) {
			DeferredDirectionalLightShader.INSTANCE.loadLight(light);
			dataBuffer.render();
		}
		directionalLights.clear();

		DeferredPointLightShader.INSTANCE.bind();
		DeferredPointLightShader.INSTANCE.loadViewMatrix(viewMatrix);
		for (PointLight light : pointLights) {
			DeferredPointLightShader.INSTANCE.loadLight(light);
			dataBuffer.render();
		}
		pointLights.clear();

		DeferredSpotLightShader.INSTANCE.bind();
		DeferredSpotLightShader.INSTANCE.loadViewMatrix(viewMatrix);
		for (SpotLight light : spotLights) {
			DeferredSpotLightShader.INSTANCE.loadLight(light);
			dataBuffer.render();
		}
		spotLights.clear();

		GL11.glDisable(GL11.GL_BLEND);
		int out = lightBuffer.getColorTextureID();
		Quad.get().preRender();
		for (PostProcessing effect : postFX) {
			out = effect.render(out, dataBuffer.getPositionTextureID(), dataBuffer.getNormalTextureID(), dataBuffer.getSpecularTextureID(), dataBuffer.getDepthTexutreID(), viewMatrix);
		}
		Quad.get().postRender();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if (Application.get().getRenderTarget() != null) {
			Application.get().getRenderTarget().bind();
		} else {
			Framebuffer2D.unbind();
		}
		BasicShader.INSTANCE.bind();
		BasicShader.INSTANCE.loadProjectionMatrix(Matrix4f.IDENTITY);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, out);
		Quad.get().render();
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void preRender() {

	}

	@Override
	public void cleanup() {
		dataBuffer.delete();
		lightBuffer.delete();
	}

	public void setAmbientColor(Vector4f ambientColor) {
		this.ambientColor = ambientColor;
	}

	@Override
	public void setModelMatrix(Matrix4f modelMatrix) {
		super.setModelMatrix(modelMatrix);
		shader.loadModelMatrix(modelMatrix);
	}

	public void addPostFX(PostProcessing effect) {
		postFX.add(effect);
	}

	@Override
	public void postResize(ResizeEvent event) {
		dataBuffer = new FramebufferDeferred(event.width, event.height);
		lightBuffer = new Framebuffer2D(event.width, event.height);
		for (PostProcessing effect : postFX)
			effect.postResize(event);
	}

	@Override
	public void preResize() {
		dataBuffer.delete();
		lightBuffer.delete();
		for (PostProcessing effect : postFX)
			effect.preResize();
	}

	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}
}
