package nullEngine.gl.renderer;

import nullEngine.input.PostResizeEvent;
import nullEngine.object.GameComponent;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;
import util.BitFieldInt;

public class MasterRenderer extends Renderer {

	/**
	 * Set the viewport
	 * @param x The x
	 * @param y The y
	 * @param width The width
	 * @param height Thw height
	 */
	public static void viewport(int x, int y, int width, int height) {
		Logs.i(String.format("Viewport: %dx%d @%d, %d", width, height, x, y));
		GL11.glViewport(x, y, width, height);
	}

	/**
	 * Initialize the renderer
	 */
	public void init() {
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glFrontFace(GL11.GL_CCW);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	/**
	 * Does nothing
	 * @param component The component
	 */
	@Override
	public void add(GameComponent component) {

	}

	/**
	 * Does nothing
	 * @param flags The render flags
	 */
	@Override
	public void postRender(BitFieldInt flags) {

	}

	/**
	 * Does nothing
	 * @param flags The rneder flags
	 */
	public void preRender(BitFieldInt flags) {

	}

	/**
	 * Does nothing
	 */
	public void cleanup() {

	}

	/**
	 * Does nothing
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {

	}

	/**
	 * Does nothing
	 */
	@Override
	public void preResize() {

	}
}
