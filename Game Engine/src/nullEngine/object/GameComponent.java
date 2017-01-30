package nullEngine.object;

import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.EventAdapter;

public abstract class GameComponent extends EventAdapter {

	public static final ListOperator.ListOperatorPool<GameComponent> GAME_COMPONENT_LIST_OPERATORS = new ListOperator.ListOperatorPool<>();

	private PhysicsEngine physics;
	private Renderer renderer;
	private GameObject parent;
	private boolean enabled = true;
	private boolean addedToRenderer = false;
	private boolean addedToPhysics = false;

	/**
	 * Setup this object
	 *
	 * @param parent   This objects parent
	 * @param physics
	 * @param renderer
	 */
	public void onAdded(GameObject parent, Renderer renderer, PhysicsEngine physics) {
		this.parent = parent;
		this.renderer = renderer;
		this.physics = physics;
		if (renderer != null) {
			renderer.add(this);
			addedToRenderer = true;
		}
		if (physics != null && enabled) {
			physics.add(this);
			addedToPhysics = true;
		}
	}

	public void onRemoved() {
		if (renderer != null && addedToRenderer) {
			renderer.remove(this);
			addedToRenderer = false;
		}

		if (physics != null && addedToPhysics) {
			physics.remove(this);
			addedToPhysics = false;
		}

		this.parent = null;
		this.renderer = null;
		this.physics = null;
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
	 * @param object   The object this component is attached to
	 * @see Renderer
	 */
	public void render(GameObject object) {

	}

		/**
		 * Update this component
		 * @param object The object this component is attached to
		 * @param delta  The time since update was last called
		 */

	public void update(GameObject object, double delta) {

	}

	/**
	 * Synchronize this component for multithreading
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

	public void onDisable() {

	}

	public void onEnable() {

	}

	public void matrixUpdated() {
	}

	public PhysicsEngine getPhysics() {
		return physics;
	}

	public Renderer getRenderer() {
		return renderer;
	}
}
