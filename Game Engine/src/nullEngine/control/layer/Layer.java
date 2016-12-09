package nullEngine.control.layer;

import com.sun.istack.internal.Nullable;
import math.Matrix4f;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.CharEvent;
import nullEngine.input.EventListener;
import nullEngine.input.KeyEvent;
import nullEngine.input.MouseEvent;
import nullEngine.input.NotificationEvent;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.GameObject;
import nullEngine.object.RootObject;
import nullEngine.object.component.graphics.camera.Camera;
import util.BitFieldInt;

import java.util.concurrent.locks.ReentrantLock;

/**
 * A layer
 */
public abstract class Layer implements EventListener {

	public PhysicsEngine physics = null;

	/**
	 * The flag for a deferred render
	 */
	public static final int DEFERRED_RENDER_BIT = 0;
	/**
	 * The flag for mouse picking
	 */
	public static final int MOUSE_PICK_RENDER_BIT = 1;

	/**
	 * The projection matrix
	 */
	protected Matrix4f projectionMatrix = new Matrix4f();
	/**
	 * The renderer
	 */
	protected Renderer renderer;

	private static volatile ReentrantLock matrixLock = new ReentrantLock();
	private Camera camera;
	private final RootObject root = new RootObject(this);
	/**
	 * Wether this layer is enabled
	 */
	public boolean enabled = true;
	/**
	 * The flags
	 */
	protected BitFieldInt flags = new BitFieldInt();

	/**
	 * Create a new layer
	 *
	 * @param camera The camera
	 */
	public Layer(@Nullable Camera camera) {
		this.camera = camera;
	}

	/**
	 * Render this layer
	 *
	 * @param passRenderer The renderer
	 */
	public void render(Renderer passRenderer) {
		Renderer useRenderer = renderer == null ? passRenderer : renderer;
		if (enabled) {

			if (camera != null)
				useRenderer.setViewMatrix(camera.getViewMatrix());
			else
				useRenderer.setViewMatrix(Matrix4f.IDENTITY);

			useRenderer.setProjectionMatrix(projectionMatrix);
			matrixLock.lock();
			{
				root.preRender();
			}
			matrixLock.unlock();
			useRenderer.preRender(flags);
			root.render(useRenderer, flags);
			useRenderer.postRender(flags);
		}
	}

	/**
	 * Update this layer
	 *
	 * @param delta The time since update was last called
	 */
	public void update(double delta) {
		if (enabled) {
			root.update(physics, delta);
			matrixLock.lock();
			{
				root.postUpdate();
			}
			matrixLock.unlock();
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get the root object
	 *
	 * @return The root
	 */
	public GameObject getRoot() {
		return root;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean keyRepeated(KeyEvent event) {
		if (enabled) {
			return root.keyRepeated(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean keyPressed(KeyEvent event) {
		if (enabled) {
			return root.keyPressed(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean keyReleased(KeyEvent event) {
		if (enabled) {
			return root.keyReleased(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean mousePressed(MouseEvent event) {
		if (enabled) {
			return root.mousePressed(event);
		}
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		if (enabled) {
			return root.mouseReleased(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean mouseScrolled(MouseEvent event) {
		if (enabled) {
			return root.mouseScrolled(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean mouseMoved(MouseEvent event) {
		if (enabled) {
			return root.mouseMoved(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public boolean charTyped(CharEvent event) {
		if (enabled) {
			return root.charTyped(event);
		}
		return false;
	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 * @return what the root returned
	 */
	@Override
	public void notified(NotificationEvent event) {

	}

	/**
	 * Pass the event to the root
	 *
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		root.postResize(event);
		if (renderer != null)
			renderer.postResize(event);
	}

	/**
	 * Pass the event to the root
	 */
	@Override
	public void preResize() {
		root.preResize();
		if (renderer != null)
		renderer.preResize();
	}

	/**
	 * Get the camera
	 * @return The camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Set the camera
	 * @param camera The new camera
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * Get the renderer
	 * @return The renderer
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	/**
	 * Set a flag
	 * @param flag the flag
	 * @param value The value
	 */
	public void setFlag(int flag, boolean value) {
		flags.set(flag, value);
	}
}
