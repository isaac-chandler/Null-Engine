package nullEngine.gl;

import nullEngine.object.GameComponent;
import org.lwjgl.opengl.GL11;

public class MasterRenderer extends Renderer {
	public void init() {
		GL11.glClearColor(0, 0, 0, 0);
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
