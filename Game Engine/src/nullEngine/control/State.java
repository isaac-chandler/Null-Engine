package nullEngine.control;

import nullEngine.gl.renderer.Renderer;
import nullEngine.input.EventHandler;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class State extends EventHandler {

	private ArrayList<Layer> layers = new ArrayList<Layer>();

	public void addLayer(Layer layer) {
		layers.add(layer);
		addListener(layer);
	}

	public void render(Renderer renderer) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for (int i = layers.size() - 1; i >= 0; i--) {
			layers.get(i).render(renderer);
		}
	}

	public void update(float delta) {
		for (int i = layers.size() - 1; i >= 0; i--) {
			layers.get(i).update(delta);
		}
	}
}
