package nullEngine.object;

import com.sun.istack.internal.Nullable;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.EventAdapter;
import util.BitFieldInt;

public abstract class GameComponent extends EventAdapter {

	private GameObject parent;
	private boolean enabled = true;

	/**
	 * Setup this object
	 *
	 * @param parent This objects parent
	 */
	public void onAdded(GameObject parent) {
		this.parent = parent;
	}

	public void onRemoved(GameObject parent) {
		this.parent = null;
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
	 * @param physics
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	public abstract void update(@Nullable PhysicsEngine physics, GameObject object, double delta);

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

	/**
	 * Wether this component should be rendered, updated and receive non crucial events
	 */
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if (this.enabled != enabled) {
			this.enabled = enabled;
			if (enabled)
				onEnable();
			else
				onDisable();
		}
	}

	public void onDisable() {}

	public void onEnable() {}

	public void matrixUpdated() {}
}
