package nullEngine.control;

import math.Matrix4f;
import nullEngine.gl.Renderer;
import nullEngine.input.CharEvent;
import nullEngine.input.EventListener;
import nullEngine.input.KeyEvent;
import nullEngine.input.MouseEvent;
import nullEngine.object.GameObject;
import nullEngine.object.RootObject;
import nullEngine.object.component.Camera;

public abstract class Layer implements EventListener {
	protected Matrix4f projectionMatrix = new Matrix4f();

	private Camera camera;
	private GameObject root = new RootObject(this);
	private boolean enabled = true;

	public Layer(Camera camera) {
		this.camera = camera;
	}

	protected abstract void preRender(Renderer renderer);

	protected abstract void postRender(Renderer renderer);

	public void render(Renderer renderer) {
		if (enabled) {
			preRender(renderer);

			if (camera != null)
				renderer.setViewMatrix(camera.getViewMatrix());
			else
				renderer.setViewMatrix(Matrix4f.IDENTITY);

			renderer.setProjectionMatrix(projectionMatrix);
			root.render(renderer);
			postRender(renderer);
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
}
