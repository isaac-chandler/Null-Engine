package nullEngine.graphics.base.renderer;

import math.Matrix4f;
import nullEngine.graphics.base.shader.Shader;
import nullEngine.input.ResizeEvent;
import nullEngine.object.GameComponent;

public abstract class Renderer {

	private static Renderer current;

	protected Matrix4f modelMatrix = Matrix4f.IDENTITY;
	protected Matrix4f projectionMatrix = Matrix4f.IDENTITY;
	protected Matrix4f viewMatrix = Matrix4f.IDENTITY;
	protected Matrix4f mvp = new Matrix4f();

	public static Renderer get() {
		return current;
	}

	public abstract void add(GameComponent component);

	public abstract void postRender();

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
		projectionMatrix.mul(viewMatrix, mvp).mul(modelMatrix, mvp);
		if (Shader.bound() != null)
			Shader.bound().loadMVP(mvp);
	}

	public abstract void postResize(ResizeEvent event);

	public abstract void preResize();

	public Matrix4f getMVP() {
		return mvp;
	}
}
