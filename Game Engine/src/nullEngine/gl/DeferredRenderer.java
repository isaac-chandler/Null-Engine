package nullEngine.gl;

import math.Matrix4f;
import math.Vector4f;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.framebuffer.FramebufferDeferred;
import nullEngine.gl.shader.BasicShader;
import nullEngine.gl.shader.DeferredAmbientShader;
import nullEngine.gl.shader.DeferredDiffuseShader;
import nullEngine.gl.shader.DeferredRenderShader;
import nullEngine.object.GameComponent;
import nullEngine.object.component.DirectionalLight;
import nullEngine.object.component.ModelComponent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeferredRenderer extends Renderer {

	private HashMap<Material, ArrayList<ModelComponent>> models = new HashMap<Material, ArrayList<ModelComponent>>();
	private Vector4f ambientColor = new Vector4f();
	private ArrayList<DirectionalLight> lights = new ArrayList<DirectionalLight>();

	private FramebufferDeferred dataBuffer;
	private Framebuffer2D lightBuffer;

	private Vector4f cameraPos = new Vector4f();

	public DeferredRenderer(int width, int height) {
		dataBuffer = new FramebufferDeferred(width, height);
		lightBuffer = new Framebuffer2D(width, height);
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
				model.getModel().render();
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

		lightBuffer.unbind();
		BasicShader.INSTANCE.bind();

		BasicShader.INSTANCE.loadProjectionMatrix(Matrix4f.IDENTITY);
		lightBuffer.render();
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
}
