package nullEngine.gl;

import math.Matrix4f;
import nullEngine.object.GameComponent;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;

public abstract class Renderer {

	private static Renderer current;

	protected Matrix4f modelMatrix = Matrix4f.IDENTITY;
	protected Matrix4f projectionMatrix = Matrix4f.IDENTITY;
	protected Matrix4f viewMatrix = Matrix4f.IDENTITY;
	protected Matrix4f mvp = new Matrix4f();

	public static void viewport(int x, int y, int width, int height) {
		Logs.d(String.format("Viewport: %dx%d @%d, %d", width, height, x, y));
		GL11.glViewport(x, y, width, height);
	}

	public static Renderer get() {
		return current;
	}

	public abstract void add(GameComponent component);

	public abstract void render();

	public abstract void preRender();

	public abstract void cleanup();

	public void setModelMatrix(Matrix4f modelMatrix) {
		this.modelMatrix = modelMatrix;
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

	protected void setMVP() {
		projectionMatrix.mul(viewMatrix, mvp).mul(modelMatrix);
	}
}
