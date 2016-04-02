package nullEngine.control;

import math.Matrix4f;
import math.Vector4f;
import org.lwjgl.opengl.GL11;
import nullEngine.gl.Renderer;
import nullEngine.gl.framebuffer.Framebuffer3D;
import nullEngine.object.component.Camera;

public class Layer3D extends Layer {

	private Framebuffer3D framebuffer = new Framebuffer3D(Application.get().getWidth(), Application.get().getHeight());

	private static final Matrix4f FLIP = new Matrix4f().setScale(new Vector4f(1, -1, 1));

	public Layer3D(Camera camera, float fov, float near, float far) {
		super(camera);
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
	}

	@Override
	protected void preRender(Renderer renderer) {
		framebuffer.bind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	protected void postRender(Renderer renderer) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		Framebuffer3D.unbind();
		renderer.setModelViewIdentity();
		renderer.setProjectionMatrix(Matrix4f.IDENTITY);
		framebuffer.render();
	}
}
