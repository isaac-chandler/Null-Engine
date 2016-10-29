package nullEngine.object;

import math.Matrix4f;
import nullEngine.control.Layer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.input.CharEvent;
import nullEngine.input.EventListener;
import nullEngine.input.KeyEvent;
import nullEngine.input.MouseEvent;
import nullEngine.input.NotificationEvent;
import nullEngine.input.PostResizeEvent;
import util.BitFieldInt;

import java.util.ArrayList;

public class GameObject implements EventListener {
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
	}

	public void addComponent(GameComponent component) {
		components.add(component);
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
		for (GameComponent component : components)
			if (component.enabled)
				component.postUpdate();
		for (GameObject child : children)
			child.postUpdate();
		postUpdateMatrix.set(transform.getMatrix());
	}

	protected void preRender() {
		for (GameComponent component : components)
			if (component.enabled)
				component.preRender();
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

	@Override
	public boolean keyRepeated(KeyEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.keyRepeated(event))
				return true;
		for (GameObject child : children)
			if (child.keyRepeated(event))
				return true;
		return false;
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.keyPressed(event))
				return true;
		for (GameObject child : children)
			if (child.keyPressed(event))
				return true;
		return false;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.keyReleased(event))
				return true;
		for (GameObject child : children)
			if (child.keyReleased(event))
				return true;
		return false;
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.mousePressed(event))
				return true;
		for (GameObject child : children)
			if (child.mousePressed(event))
				return true;
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.mouseReleased(event))
				return true;
		for (GameObject child : children)
			if (child.mouseReleased(event))
				return true;
		return false;
	}

	@Override
	public boolean mouseScrolled(MouseEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.mouseScrolled(event))
				return true;
		for (GameObject child : children)
			if (child.mouseScrolled(event))
				return true;
		return false;
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.mouseMoved(event))
				return true;
		for (GameObject child : children)
			if (child.mouseMoved(event))
				return true;
		return false;
	}

	@Override
	public boolean charTyped(CharEvent event) {
		for (GameComponent component : components)
			if (component.enabled && component.charTyped(event))
				return true;
		for (GameObject child : children)
			if (child.charTyped(event))
				return true;
		return false;
	}

	@Override
	public void notified(NotificationEvent event) {

	}

	@Override
	public void postResize(PostResizeEvent event) {
		for (GameComponent component : components)
			component.postResize(event);
		for (GameObject child : children)
			child.postResize(event);
	}

	@Override
	public void preResize() {
		for (GameComponent component : components)
			component.preResize();
		for (GameObject child : children)
			child.preResize();
	}
}
