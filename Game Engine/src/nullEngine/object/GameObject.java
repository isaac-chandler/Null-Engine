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
import java.util.List;

/**
 * Represents an object in the game
 */
public class GameObject implements EventListener {
	private List<GameObject> children = new ArrayList<>();
	private List<GameComponent> components = new ArrayList<>();
	private Transform transform = new Transform();
	private Matrix4f postUpdateMatrix = new Matrix4f();
	private Matrix4f preRenderMatrix = new Matrix4f();

	private GameObject parent;

	private void init(GameObject parent) {
		this.parent = parent;
		transform.setParent(parent.getTransform());
	}

	/**
	 * Add a child GameObject to this
	 *
	 * @param object The GameObject to add
	 */
	public void addChild(GameObject object) {
		children.add(object);
		object.init(this);
	}

	/**
	 * Add a component to this object
	 *
	 * @param component the component to add
	 */
	public void addComponent(GameComponent component) {
		components.add(component);
		component.init(this);
	}

	/**
	 * Render this object
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param flags    The render flags
	 * @see Renderer
	 */
	public void render(Renderer renderer, BitFieldInt flags) {
		for (GameComponent component : components) {
			if (component.enabled)
				component.render(renderer, this, flags);
		}
		for (GameObject child : children)
			child.render(renderer, flags);
	}

	/**
	 * Update this object
	 *
	 * @param delta The time since it was last updated
	 */
	public void update(double delta) {
		for (GameComponent component : components) {
			if (component.enabled)
				component.update(delta, this);
		}
		for (GameObject child : children)
			child.update(delta);
	}

	/**
	 * Update the matrix for multithreading synchronization
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	protected void postUpdate() {
		for (GameComponent component : components)
			if (component.enabled)
				component.postUpdate();
		for (GameObject child : children)
			child.postUpdate();
		postUpdateMatrix.set(transform.getMatrix());
	}

	/**
	 * Update the matrix for multithreading synchronization
	 * <strong>Do not run expensive code here as it is intended for copying data only, otherwise the performance will be bad</strong>
	 */
	protected void preRender() {
		for (GameComponent component : components)
			if (component.enabled)
				component.preRender();
		for (GameObject child : children)
			child.preRender();
		preRenderMatrix.set(postUpdateMatrix);
	}

	/**
	 * Get the transform of this object
	 *
	 * @return The transform
	 */
	public Transform getTransform() {
		return transform;
	}

	/**
	 * Get the layer this object belongs to
	 *
	 * @return The layer
	 */
	public Layer getLayer() {
		return parent.getLayer();
	}

	/**
	 * Get the objects children
	 *
	 * @return The children
	 */
	public List<GameObject> getChildren() {
		return children;
	}

	/**
	 * Get the objects components
	 *
	 * @return The components
	 */
	public List<GameComponent> getComponents() {
		return components;
	}

	/**
	 * Get the objects parent object
	 *
	 * @return The parent
	 */
	public GameObject getParent() {
		return parent;
	}

	/**
	 * Get the matrix used for rendering
	 *
	 * @return The matrix
	 */
	public Matrix4f getRenderMatrix() {
		return preRenderMatrix;
	}

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @return
	 * @see nullEngine.input.EventHandler
	 */
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

	/**
	 * @param event
	 * @see nullEngine.input.EventListener
	 */
	@Override
	public void notified(NotificationEvent event) {

	}

	/**
	 * @param event
	 * @see nullEngine.input.EventListener
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		for (GameComponent component : components)
			component.postResize(event);
		for (GameObject child : children)
			child.postResize(event);
	}

	/**
	 * @see nullEngine.input.EventListener
	 */
	@Override
	public void preResize() {
		for (GameComponent component : components)
			component.preResize();
		for (GameObject child : children)
			child.preResize();
	}
}
