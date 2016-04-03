package nullEngine.control;

import math.Matrix4f;
import math.Vector4f;
import nullEngine.gl.Renderer;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.framebuffer.FramebufferDeferred;
import nullEngine.gl.shader.BasicShader;
import nullEngine.gl.shader.DeferredAmbientShader;
import nullEngine.gl.shader.DeferredDiffuseShader;
import nullEngine.gl.shader.DeferredRenderShader;
import nullEngine.object.component.Camera;
import nullEngine.object.component.DirectionalLight;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Layer3D extends Layer {

	private FramebufferDeferred dataBuffer = new FramebufferDeferred(Application.get().getWidth(), Application.get().getHeight());
	private Framebuffer2D lightBuffer = new Framebuffer2D(Application.get().getWidth(), Application.get().getHeight());

	private ArrayList<DirectionalLight> lights = new ArrayList<DirectionalLight>();

	private static final Matrix4f FLIP = new Matrix4f().setScale(new Vector4f(1, -1, 1));

	private Vector4f ambientColor = new Vector4f();

	public Layer3D(Camera camera, float fov, float near, float far) {
		super(camera);
		projectionMatrix.setPerspective(fov, (float) Application.get().getWidth() / (float) Application.get().getHeight(),
				near, far);
	}

	@Override
	protected void preRender(Renderer renderer) {
		dataBuffer.bind();
		DeferredRenderShader.INSTANCE.bind();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glCullFace(GL11.GL_FRONT);
	}

	@Override
	protected void postRender(Renderer renderer) {
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		dataBuffer.unbind();

		lightBuffer.bind();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

		DeferredAmbientShader.INSTANCE.bind();
		DeferredAmbientShader.INSTANCE.loadAmbientColor(ambientColor);

		dataBuffer.render();

		DeferredDiffuseShader.INSTANCE.bind();
		for (DirectionalLight light : lights) {
			DeferredDiffuseShader.INSTANCE.loadLight(light);
			dataBuffer.render();
		}
		lights.clear();

		GL11.glDisable(GL11.GL_BLEND);

		lightBuffer.unbind();
		BasicShader.INSTANCE.bind();

		BasicShader.INSTANCE.loadProjectionMatrix(Matrix4f.IDENTITY);
		lightBuffer.render();
	}

	public void setAmbientColor(Vector4f ambientColor) {
		this.ambientColor = ambientColor;
	}

	public void add(DirectionalLight light) {
		lights.add(light);
	}
}
