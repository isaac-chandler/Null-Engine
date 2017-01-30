package nullEngine.control.layer;

import com.sun.istack.internal.NotNull;
import math.Vector4f;
import nullEngine.control.Application;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.renderer.DeferredRenderer;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.EventListener;
import nullEngine.input.MousePickInfo;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.component.graphics.camera.Camera;

/**
 * Deferred rendering layer
 */
public class LayerDeferred extends Layer {

	private float fov, near, far;
	private boolean mousePick = false;

	/**
	 * Create a new deferred layer
	 *
	 * @param camera The camera
	 * @param fov    the field of view in radians
	 * @param near   The near plane
	 * @param far    The far plane
	 */
	public LayerDeferred(PhysicsEngine physics, Camera camera, float fov, float near, float far, boolean hdr) {
		super(camera, physics, new DeferredRenderer(Application.get().getWidth(), Application.get().getHeight(), far, near, hdr));
		flags.set(DEFERRED_RENDER_BIT, true);
		this.fov = fov;
		this.near = near;
		this.far = far;
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
	}

	public void render(Renderer passRenderer) {
		super.render(passRenderer);
		flags.set(MOUSE_PICK_RENDER_BIT, mousePick);
		if (mousePick)
			mousePick = false;
	}

	public void setAmbientColor(Vector4f ambientColor) {
		((DeferredRenderer) getRenderer()).setAmbientColor(ambientColor);
	}

	@Override
	public void postResize(PostResizeEvent event) {
		super.postResize(event);
		projectionMatrix.setPerspective(fov, (float) event.width / (float) event.height, near, far);
	}

	public void requestMousePick(int x, int y, @NotNull MousePickInfo info, @NotNull EventListener notify) {
		mousePick = true;
		((DeferredRenderer) getRenderer()).mousePick(x, y, info, notify);
	}
}
