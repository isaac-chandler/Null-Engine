package nullEngine.gl;

import math.Matrix4f;
import nullEngine.gl.shader.DeferredRenderShader;
import org.lwjgl.opengl.GL11;
import nullEngine.util.logs.Logs;

public class MasterRenderer {

	private DeferredRenderShader shader;
	private Matrix4f modelMatrix = Matrix4f.IDENTITY;
	private Matrix4f projectionMatrix = Matrix4f.IDENTITY;
	private Matrix4f viewMatrix = Matrix4f.IDENTITY;

	private static MasterRenderer current;

	public static MasterRenderer get() {
		return current;
	}

	public MasterRenderer() {
		shader = DeferredRenderShader.INSTANCE;
	}

	public void viewport(int x, int y, int width, int height) {
		Logs.d(String.format("Viewport: %dx%d @%d, %d", width, height, x, y));
		GL11.glViewport(x, y, width, height);
	}
	public void init() {
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public void preRender() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.bind();
	}

	public void cleanup() {
		shader.delete();
	}

	public void setModelMatrix(Matrix4f modelMatrix) {
		this.modelMatrix = modelMatrix;
		shader.loadModelMatrix(modelMatrix);
		setMVP();
	}

	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
		setMVP();
	}

	public void bind() {
		current = this;
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;
		setMVP();
	}

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public void setModelViewIdentity() {
		setModelMatrix(Matrix4f.IDENTITY);
		setViewMatrix(Matrix4f.IDENTITY);
	}

	private static final Matrix4f internal = new Matrix4f();

	private void setMVP() {
		projectionMatrix.mul(viewMatrix, internal).mul(modelMatrix);
		shader.loadMVP(internal);
	}
}
