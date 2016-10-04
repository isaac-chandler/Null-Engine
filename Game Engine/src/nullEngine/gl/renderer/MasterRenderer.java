package nullEngine.gl.renderer;

import nullEngine.input.ResizeEvent;
import nullEngine.object.GameComponent;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;

public class MasterRenderer extends Renderer {

	public static void viewport(int x, int y, int width, int height) {
		Logs.i(String.format("Viewport: %dx%d @%d, %d", width, height, x, y));
		GL11.glViewport(x, y, width, height);
	}

	public void init() {
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glFrontFace(GL11.GL_CCW);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	@Override
	public void add(GameComponent component) {

	}

	@Override
	public void postRender() {

	}

	public void preRender() {

	}

	public void cleanup() {

	}

	@Override
	public void postResize(ResizeEvent event) {

	}

	@Override
	public void preResize() {

	}
}
