package nullEngine.object;

import nullEngine.control.Layer;
import nullEngine.graphics.base.renderer.Renderer;
import nullEngine.input.EventHandler;

import java.util.ArrayList;

public class GameObject extends EventHandler {
	private ArrayList<GameObject> children = new ArrayList<GameObject>();
	private ArrayList<GameComponent> components = new ArrayList<GameComponent>();
	private Transform transform = new Transform();

	private GameObject parent;

	private void init(GameObject parent) {
		this.parent = parent;
		transform.setParent(parent.getTransform());
	}

	public void addChild(GameObject object) {
		children.add(object);
		object.init(this);
		addListener(object);
	}

	public void addComponent(GameComponent component) {
		components.add(component);
		addListener(component);
		component.init(this);
	}

	public void render(Renderer renderer) {
		renderer.setModelMatrix(transform.getMatrix());
		for (GameComponent component : components) {
			component.render(renderer, this);
		} for (GameObject child : children)
			child.render(renderer);
	}

	public void update(float delta) {
		for (GameComponent component : components) {
			component.update(delta, this);
		} for (GameObject child : children)
			child.update(delta);
	}

	public Transform getTransform() {
		return transform;
	}

	public Layer getLayer() {
		return parent.getLayer();
	}
}
