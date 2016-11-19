package nullEngine.control;

import math.Matrix4f;
import nullEngine.gl.renderer.GuiRenderer;

public class LayerGUI extends Layer {

	/**
	 * Create a new GUI layer
	 */
	public LayerGUI() {
		super(null);
		projectionMatrix = Matrix4f.IDENTITY;
		renderer = new GuiRenderer();
	}
}
