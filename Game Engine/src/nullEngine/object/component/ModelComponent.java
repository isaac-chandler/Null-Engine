package nullEngine.object.component;

import nullEngine.control.Layer;
import nullEngine.gl.Material;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.model.Model;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class ModelComponent extends GameComponent {

	public static boolean MOUSE_PICKING_ENABLED_DEFAULT = false;

	private Material material;
	private Model model;
	private int lodBias = 0;
	public boolean enableMousePicking = MOUSE_PICKING_ENABLED_DEFAULT;

	public ModelComponent(Material material, Model model) {
		this.material = material;
		this.model = model;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public int getLodBias() {
		return lodBias;
	}

	public void setLodBias(int lodBias) {
		this.lodBias = lodBias;
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if (flags.get(Layer.MOUSE_PICK_RENDER_BIT) && enableMousePicking || flags.get(Layer.DEFERRED_RENDER_BIT))
			renderer.add(this);
	}

	@Override
	public void update(float delta, GameObject object) {

	}
}
