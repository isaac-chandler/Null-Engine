package nullEngine.object.component;

import math.Matrix4f;
import nullEngine.object.GameComponent;

/**
 * A camera
 */
public abstract class Camera extends GameComponent {

	/**
	 * The cameras view matrix
	 */
	protected Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f postUpdateMatrix = new Matrix4f();
	private Matrix4f preRenderMatrix = new Matrix4f();

	/**
	 * Get the view matrix
	 * @return The view matrix
	 */
	public Matrix4f getViewMatrix() {
		return preRenderMatrix;
	}

	/**
	 * Syncronize this component for multithreading
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	@Override
	public void postUpdate() {
		super.postUpdate();
		postUpdateMatrix.set(viewMatrix);
	}

	/**
	 * Syncronize this component for multithreading
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	@Override
	public void preRender() {
		preRenderMatrix.set(postUpdateMatrix);
	}
}
