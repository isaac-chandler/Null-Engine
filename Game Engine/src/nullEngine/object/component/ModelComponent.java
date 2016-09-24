package nullEngine.object.component;

import nullEngine.graphics.base.Material;
import nullEngine.graphics.base.renderer.Renderer;
import nullEngine.graphics.base.model.Model;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;

public class ModelComponent extends GameComponent {

	private Material material;
	private Model model;
	private int lodBias = 0;

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
	public void render(Renderer renderer, GameObject object) {
		renderer.add(this);
	}

	@Override
	public void update(float delta, GameObject object) {

	}
}
