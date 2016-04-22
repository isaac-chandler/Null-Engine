package nullEngine.gl;

import nullEngine.object.GameComponent;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;

public class MasterRenderer extends Renderer {

	public static void viewport(int x, int y, int width, int height) {
		Logs.d(String.format("Viewport: %dx%d @%d, %d", width, height, x, y));
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
	public void render() {

	}

	public void preRender() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void cleanup() {

	}
}
