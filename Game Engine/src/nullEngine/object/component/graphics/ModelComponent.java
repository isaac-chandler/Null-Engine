package nullEngine.object.component.graphics;

import nullEngine.control.physics.PhysicsEngine;
import nullEngine.control.layer.Layer;
import nullEngine.graphics.Material;
import nullEngine.graphics.model.Model;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * A component that renders a model
 */
public class ModelComponent extends GameComponent {

	/**
	 * Wether mouse picking is enabled for a component by default
	 * <strong>This will change</strong>
	 */
	public static boolean MOUSE_PICKING_ENABLED_DEFAULT = false;

	private Material material;
	private Model model;
	private int lodBias = 0;
	/**
	 * Wether this object should have mouse picking
	 */
	public boolean enableMousePicking = MOUSE_PICKING_ENABLED_DEFAULT;

	/**
	 * Create a new ModelComponent
	 *
	 * @param material The material to use
	 * @param model    The model to render
	 */
	public ModelComponent(Material material, Model model) {
		this.material = material;
		this.model = model;
	}

	/**
	 * Get the material
	 *
	 * @return The material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Set the material
	 *
	 * @param material The new material
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Get the model to be used with specific flags
	 *
	 * @param flags The render flags
	 * @return The model
	 */
	public Model getModel(BitFieldInt flags) {
		return model;
	}

	/**
	 * Set the model
	 *
	 * @param model The new model
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Get the level of detail bias on the model
	 *
	 * @return The level of detail bias
	 */
	public int getLodBias() {
		return lodBias;
	}

	/**
	 * Set the level of detail bias on the model
	 *
	 * @param lodBias The level of detail bias
	 */
	public void setLodBias(int lodBias) {
		this.lodBias = lodBias;
	}

	/**
	 * Render this model
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if ((flags.get(Layer.MOUSE_PICK_RENDER_BIT) && enableMousePicking) || flags.get(Layer.DEFERRED_RENDER_BIT))
			renderer.add(this);
	}

	/**
	 * Update this component
	 * @param physics
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(PhysicsEngine physics, GameObject object, double delta) {

	}
}
