package nullEngine.control;

import math.Matrix4f;
import nullEngine.graphics.base.renderer.Renderer;
import nullEngine.input.*;
import nullEngine.object.GameObject;
import nullEngine.object.RootObject;
import nullEngine.object.component.Camera;

public abstract class Layer implements EventListener {
	protected Matrix4f projectionMatrix = new Matrix4f();
	protected Renderer renderer;

	private Camera camera;
	private GameObject root = new RootObject(this);
	private boolean enabled = true;

	public Layer(Camera camera) {
		this.camera = camera;
	}

	public void render(Renderer passRenderer) {
		Renderer useRenderer = renderer == null ? passRenderer : renderer;
		if (enabled) {

			if (camera != null)
				useRenderer.setViewMatrix(camera.getViewMatrix());
			else
				useRenderer.setViewMatrix(Matrix4f.IDENTITY);

			useRenderer.setProjectionMatrix(projectionMatrix);
			useRenderer.preRender();
			root.render(useRenderer);
			useRenderer.postRender();
		}
	}

	public void update(float delta) {
		if (enabled)
			root.update(delta);
	}

	public GameObject getRoot() {
		return root;
	}

	@Override
	public boolean keyRepeated(KeyEvent event) {
		if (enabled) {
			return root.keyRepeated(event);
		}
		return false;
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (enabled) {
			return root.keyPressed(event);
		}
		return false;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		if (enabled) {
			return root.keyReleased(event);
		}
		return false;
	}

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

	@Override
	public boolean mouseScrolled(MouseEvent event) {
		if (enabled) {
			return root.mouseScrolled(event);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		if (enabled) {
			return root.mouseMoved(event);
		}
		return false;
	}

	@Override
	public boolean charTyped(CharEvent event) {
		if (enabled) {
			return root.charTyped(event);
		}
		return false;
	}

	@Override
	public void postResize(ResizeEvent event) {
		root.postResize(event);
		renderer.postResize(event);
	}

	@Override
	public void preResize() {
		root.preResize();
		renderer.preResize();
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Renderer getRenderer() {
		return renderer;
	}
}
