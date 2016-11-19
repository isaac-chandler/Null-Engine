package nullEngine.object;

import nullEngine.control.Layer;

/**
 * The root GameObject, created by Layer, don't create directly
 *
 * @see GameObject
 */
public class RootObject extends GameObject {

	private Layer layer;

	/**
	 * Create a new root object
	 *
	 * @param layer The layer this is the root of
	 */
	public RootObject(Layer layer) {
		this.layer = layer;
	}

	/**
	 * Get the layer this object belongs to
	 *
	 * @return The layer
	 */
	@Override
	public Layer getLayer() {
		return layer;
	}

	/**
	 * Update the matrix for multithreading synchronization
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	@Override
	public void postUpdate() {
		super.postUpdate();
	}

	/**
	 * Update the matrix for multithreading synchronization
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	@Override
	public void preRender() {
		super.preRender();
	}
}
