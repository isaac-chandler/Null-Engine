package nullEngine.object.component;

import nullEngine.gl.Material;
import nullEngine.gl.Renderer;
import nullEngine.gl.model.Model;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;

public class ModelComponent extends GameComponent {

	private Material material;
	private Model model;

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

	@Override
	public void render(Renderer renderer, GameObject object) {
		renderer.add(this);
	}

	@Override
	public void update(float delta, GameObject object) {

	}
}
