package nullEngine.control;

import math.Vector4f;
import nullEngine.gl.DeferredRenderer;
import nullEngine.gl.Renderer;
import nullEngine.object.component.Camera;

public class Layer3D extends Layer {

	private DeferredRenderer renderer;

	public Layer3D(Camera camera, float fov, float near, float far) {
		super(camera);
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
		renderer = new DeferredRenderer(Application.get().getWidth(), Application.get().getHeight());
	}

	@Override
	public void render(Renderer renderer) {
		super.render(this.renderer);
	}

	@Override
	protected void preRender(Renderer renderer) {

	}

	@Override
	protected void postRender(Renderer renderer) {
		this.renderer.render();
	}

	public void setAmbientColor(Vector4f ambientColor) {
		renderer.setAmbientColor(ambientColor);
	}
}
