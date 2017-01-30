package nullEngine.object.component.graphics;

import nullEngine.control.layer.Layer;
import nullEngine.graphics.Material;
import nullEngine.graphics.model.Model;
import nullEngine.object.GameComponent;

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
	 * @param renderMethod The render flags
	 * @return The model
	 */
	public Model getModel(int renderMethod) {
		if (isEnabled()) {
			if (renderMethod == Layer.DEFERRED_RENDER_BIT)
				return model;
			else if (renderMethod == Layer.MOUSE_PICK_RENDER_BIT && MOUSE_PICKING_ENABLED_DEFAULT)
				return model;
		}
		return null;
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
}
