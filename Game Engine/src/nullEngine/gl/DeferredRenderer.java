package nullEngine.gl;

import math.Matrix4f;
import math.Vector4f;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.framebuffer.FramebufferDeferred;
import nullEngine.gl.model.Quad;
import nullEngine.gl.shader.BasicShader;
import nullEngine.gl.shader.deferred.DeferredAmbientShader;
import nullEngine.gl.shader.deferred.DeferredDiffuseShader;
import nullEngine.gl.shader.deferred.DeferredRenderShader;
import nullEngine.object.GameComponent;
import nullEngine.object.component.DirectionalLight;
import nullEngine.object.component.ModelComponent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeferredRenderer extends Renderer {

	private static final float LOD_DROPOFF_FACTOR = 1;
	private HashMap<Material, ArrayList<ModelComponent>> models = new HashMap<Material, ArrayList<ModelComponent>>();
	private Vector4f ambientColor = new Vector4f();
	private ArrayList<DirectionalLight> lights = new ArrayList<DirectionalLight>();

	private FramebufferDeferred dataBuffer;
	private Framebuffer2D lightBuffer;
	private float far;
	private float near;

	private ArrayList<PostProcessing> postFX = new ArrayList<PostProcessing>();

	private static final Matrix4f FLIP = Matrix4f.setScale(new Vector4f(1, -1, 1), null);

	private Vector4f cameraPos = new Vector4f();

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
			lights.add((DirectionalLight) component);
		}
	}

	@Override
	public void render() {
		dataBuffer.bind();
		DeferredRenderShader.INSTANCE.bind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glCullFace(GL11.GL_FRONT);

		for (Map.Entry<Material, ArrayList<ModelComponent>> components : models.entrySet()) {
			DeferredRenderShader.INSTANCE.loadMaterial(components.getKey());
			for (ModelComponent model : components.getValue()) {
				setModelMatrix(model.getParent().getTransform().getMatrix());
				Vector4f pos = getViewMatrix().transform(model.getParent().getTransform().getWorldPos(), (Vector4f) null);
				if (-pos.z <= far && -pos.z > near) {
					model.getModel().render((int) Math.floor(Math.pow(-pos.z / far, LOD_DROPOFF_FACTOR) * model.getModel().getLODCount()));
				}
			}
		}
		models.clear();


		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		dataBuffer.unbind();

		lightBuffer.bind();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

		DeferredAmbientShader.INSTANCE.bind();
		DeferredAmbientShader.INSTANCE.loadAmbientColor(ambientColor);

		dataBuffer.render();

		DeferredDiffuseShader.INSTANCE.bind();
		DeferredDiffuseShader.INSTANCE.loadCameraPos(cameraPos);
		for (DirectionalLight light : lights) {
			DeferredDiffuseShader.INSTANCE.loadLight(light);
			dataBuffer.render();
		}
		lights.clear();

		GL11.glDisable(GL11.GL_BLEND);
		int out = lightBuffer.getColorTextureID();
		for (PostProcessing effect : postFX) {
			out = effect.render(out, dataBuffer.getDepthTexutreID());
		}

		Framebuffer2D.unbind();
		BasicShader.INSTANCE.bind();
		BasicShader.INSTANCE.loadProjectionMatrix(FLIP);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, out);
		Quad.back().render();
//		dataBuffer.render();
	}

	@Override
	public void preRender() {

	}

	@Override
	public void cleanup() {

	}

	public void setAmbientColor(Vector4f ambientColor) {
		this.ambientColor = ambientColor;
	}

	@Override
	protected void setMVP() {
		super.setMVP();
		DeferredRenderShader.INSTANCE.loadMVP(mvp);
	}

	@Override
	public void setModelMatrix(Matrix4f modelMatrix) {
		super.setModelMatrix(modelMatrix);
		DeferredRenderShader.INSTANCE.loadModelMatrix(modelMatrix);
	}

	@Override
	public void setViewMatrix(Matrix4f viewMatrix) {
		super.setViewMatrix(viewMatrix);
		cameraPos.x = viewMatrix.m30;
		cameraPos.y = viewMatrix.m31;
		cameraPos.z = viewMatrix.m32;
	}

	public void addPostFX(PostProcessing effect) {
		postFX.add(effect);
	}
}
