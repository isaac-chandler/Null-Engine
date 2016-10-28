package nullEngine.object;

import math.Matrix4f;
import nullEngine.control.Layer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.input.EventHandler;
import util.BitFieldInt;

import java.util.ArrayList;

public class GameObject extends EventHandler {
	private ArrayList<GameObject> children = new ArrayList<GameObject>();
	private ArrayList<GameComponent> components = new ArrayList<GameComponent>();
	private Transform transform = new Transform();
	private Matrix4f postUpdateMatrix = new Matrix4f();
	private Matrix4f preRenderMatrix = new Matrix4f();

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

	public void update(double delta) {
		for (GameComponent component : components) {
			if (component.enabled)
				component.update(delta, this);
		}
		for (GameObject child : children)
			child.update(delta);
	}

	protected void postUpdate() {
		for (GameObject child : children)
			child.postUpdate();
		postUpdateMatrix.set(transform.getMatrix());
	}

	protected void preRender() {
		for (GameObject child : children)
			child.preRender();
		preRenderMatrix.set(postUpdateMatrix);
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

	public Matrix4f getRenderMatrix() {
		return preRenderMatrix;
	}
}
