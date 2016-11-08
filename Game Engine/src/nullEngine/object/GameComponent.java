package nullEngine.object;

import nullEngine.gl.renderer.Renderer;
import nullEngine.input.EventAdapter;
import util.BitFieldInt;

public abstract class GameComponent extends EventAdapter {

	private GameObject parent;
	/**
	 * Wether this component should be rendered, updated and receive non crucial events
	 */
	public boolean enabled = true;

	/**
	 * Setup this object
	 * @param parent This objects parent
	 */
	public void init(GameObject parent) {
		this.parent = parent;
	}

	/**
	 * Get the object this component is attahced to
	 *
	 * @return The object
	 */
	public GameObject getObject() {
		return parent;
	}

	/**
	 * Render this component
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 * @see Renderer
	 */
	public abstract void render(Renderer renderer, GameObject object, BitFieldInt flags);

	/**
	 * Update this component
	 *
	 * @param delta  The time since update was last called
	 * @param object The object this component is attached to
	 */
	public abstract void update(double delta, GameObject object);

	/**
	 * Syncronize this component for multithreading
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	public void preRender() {

	}

	/**
	 * Syncronize this component for multithreading
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	public void postUpdate() {

	}
}
