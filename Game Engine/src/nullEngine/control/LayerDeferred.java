package nullEngine.control;

import math.Vector4f;
import nullEngine.gl.renderer.DeferredRenderer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.component.Camera;

public class LayerDeferred extends Layer {

	private float fov, near, far;


	public LayerDeferred(Camera camera, float fov, float near, float far) {
		super(camera);
		flags.set(DEFERRED_RENDER_BIT, true);
		this.fov = fov;
		this.near = near;
		this.far = far;
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
		renderer = new DeferredRenderer(Application.get().getWidth(), Application.get().getHeight(), far, near);
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
