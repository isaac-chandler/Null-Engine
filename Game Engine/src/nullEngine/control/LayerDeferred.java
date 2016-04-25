package nullEngine.control;

import math.Vector4f;
import nullEngine.gl.renderer.DeferredRenderer;
import nullEngine.input.ResizeEvent;
import nullEngine.object.component.Camera;

public class LayerDeferred extends Layer {

	private float fov, near, far;

	public LayerDeferred(Camera camera, float fov, float near, float far) {
		super(camera);
		this.fov = fov;
		this.near = near;
		this.far = far;
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
		renderer = new DeferredRenderer(Application.get().getWidth(), Application.get().getHeight(), far, near);
	}

	public void setAmbientColor(Vector4f ambientColor) {
		((DeferredRenderer) renderer).setAmbientColor(ambientColor);
	}

	@Override
	public void postResize(ResizeEvent event) {
		super.postResize(event);
		projectionMatrix.setPerspective(fov, (float) event.width / (float) event.height, near, far);
	}
}
