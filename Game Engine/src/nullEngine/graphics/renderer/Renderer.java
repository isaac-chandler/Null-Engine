package nullEngine.graphics.renderer;

import math.Matrix4f;
import nullEngine.graphics.shader.Shader;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.GameComponent;
import nullEngine.object.ListOperator;
import util.BitFieldInt;

/**
 * Rendering manager
 */
public abstract class Renderer {

	protected ListOperator.ListOperatorQueue<GameComponent> componentOps = new ListOperator.ListOperatorQueue<>(GameComponent.GAME_COMPONENT_LIST_OPERATORS);

	private static Renderer current;

	/**
	 * The model matrix
	 */
	protected Matrix4f modelMatrix = Matrix4f.IDENTITY;
	/**
	 * The projection matrix
	 */
	protected Matrix4f projectionMatrix = Matrix4f.IDENTITY;
	/**
	 * The view matrix
	 */
	protected Matrix4f viewMatrix = Matrix4f.IDENTITY;
	/**
	 * The modle view projection matrix
	 */
	protected Matrix4f mvp = new Matrix4f();

	/**
	 * Get the current renderer
	 * @return The current renderer
	 */
	public static Renderer get() {
		return current;
	}

	/**
	 * Add a component
	 * @param component The component
	 */
	public void add(GameComponent component) {
		componentOps.add(component);
	}

	public void remove(GameComponent component) {
		componentOps.remove(component);
	}



	/**
	 * Run after the render
	 * @param flags The render flags
	 */
	public abstract void postRender(BitFieldInt flags);

	/**
	 * Run before the render
	 * @param flags The render flags
	 */
	public abstract void preRender(BitFieldInt flags);

	/**
	 * Cleanup after this renderer
	 */
	public abstract void cleanup();

	/**
	 * Set the model matrix
	 * @param modelMatrix The model matrix
	 */
	public void setModelMatrix(Matrix4f modelMatrix) {
		this.modelMatrix = modelMatrix;
		setMVP();
	}

	/**
	 * Set the projection matrix
	 * @param projectionMatrix The projection matrix
	 */
	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
		setMVP();
	}

	/**
	 * Bind this renderer
	 */
	public void bind() {
		current = this;
	}

	/**
	 * Set the view matrix
	 * @param viewMatrix The view matrix
	 */
	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;

		setMVP();
	}

	/**
	 * Get the model matrix
	 * @return The model matrix
	 */
	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}

	/**
	 * Get the projection matrix
	 * @return The projection matrix
	 */
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	/**
	 * Get the view matrix
	 * @return The view matrix
	 */
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	/**
	 * Set the model and view matrices to the identity matrix
	 */
	public void setModelViewIdentity() {
		setModelMatrix(Matrix4f.IDENTITY);
		setViewMatrix(Matrix4f.IDENTITY);
	}

	/**
	 * Set the model view projection matrix
	 */
	protected void setMVP() {
		projectionMatrix.mul(viewMatrix, mvp).mul(modelMatrix, mvp);
		if (Shader.bound() != null)
			Shader.bound().loadMVP(mvp);
	}

	/**
	 * Called after the window resizes
	 * @param event The event
	 */
	public abstract void postResize(PostResizeEvent event);

	/**
	 * Called before the window resizes
	 */
	public abstract void preResize();

	/**
	 * Get the model view projection matrix
	 * @return The model view projection matrix
	 */
	public Matrix4f getMVP() {
		return mvp;
	}

}
