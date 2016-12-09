package nullEngine.control.layer;

import math.Vector4f;
import nullEngine.control.Application;
import nullEngine.graphics.renderer.DeferredRenderer;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.component.graphics.camera.Camera;

/**
 * Deferred rendering layer
 */
public class LayerDeferred extends Layer {

	private float fov, near, far;

	/**
	 * Create a new deferred layer
	 * @param camera The camera
	 * @param fov the field of view in radians
	 * @param near The near plane
	 * @param far The far plane
	 */
	public LayerDeferred(Camera camera, float fov, float near, float far, boolean hdr) {
		super(camera);
		flags.set(DEFERRED_RENDER_BIT, true);
		this.fov = fov;
		this.near = near;
		this.far = far;
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
		renderer = new DeferredRenderer(Application.get().getWidth(), Application.get().getHeight(), far, near, hdr);
	}

	public void render(Renderer passRenderer) {
		super.render(passRenderer);
		if (flags.get(MOUSE_PICK_RENDER_BIT)) {
			flags.set(MOUSE_PICK_RENDER_BIT, false);
		}
	}

	public void setAmbientColor(Vector4f ambientColor) {
		((DeferredRenderer) renderer).setAmbientColor(ambientColor);
	}

	@Override
	public void postResize(PostResizeEvent event) {
		super.postResize(event);
		projectionMatrix.setPerspective(fov, (float) event.width / (float) event.height, near, far);
	}

	public void mousePickNextFrame() {
		flags.set(MOUSE_PICK_RENDER_BIT, true);
	}
}
