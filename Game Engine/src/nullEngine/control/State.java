package nullEngine.control;

import nullEngine.control.layer.Layer;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.EventHandler;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * A game state
 */
public class State extends EventHandler {

	private List<Layer> layers = new ArrayList<>();

	/**
	 * Add a layer to the state
	 * @param layer The layer to add
	 */
	public void addLayer(Layer layer) {
		layers.add(layer);
		addListener(layer);
	}

	/**
	 * Render this state
	 * @param renderer The renderer
	 */
	public void render(Renderer renderer) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for (int i = layers.size() - 1; i >= 0; i--) {
			layers.get(i).render(renderer);
		}
	}

	/**
	 * Update this state
	 * @param delta The time since update was last called
	 */
	public void update(double delta) {
		for (int i = layers.size() - 1; i >= 0; i--) {
			layers.get(i).update(delta);
		}
	}
}
