package nullEngine.object;

import nullEngine.control.Layer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.input.EventHandler;
import util.BitFieldInt;

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

	public void render(Renderer renderer, BitFieldInt flags) {
		for (GameComponent component : components) {
			if (component.enabled)
				component.render(renderer, this, flags);
		}
		for (GameObject child : children)
			child.render(renderer, flags);
	}

	public void update(float delta) {
		for (GameComponent component : components) {
			if (component.enabled)
				component.update(delta, this);
		}
		for (GameObject child : children)
			child.update(delta);
	}

	public Transform getTransform() {
		return transform;
	}

	public Layer getLayer() {
		return parent.getLayer();
	}

	public ArrayList<GameObject> getChildren() {
		return children;
	}

	public ArrayList<GameComponent> getComponents() {
		return components;
	}

	public GameObject getParent() {
		return parent;
	}
}
