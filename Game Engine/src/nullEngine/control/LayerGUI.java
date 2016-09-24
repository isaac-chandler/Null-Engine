package nullEngine.control;

import math.Matrix4f;
import nullEngine.graphics.base.renderer.GuiRenderer;

public class LayerGUI extends Layer {

	public LayerGUI() {
		super(null);
//		projectionMatrix.setOrthographic(0, 1, 0, 1, -1, 1);
		projectionMatrix = Matrix4f.IDENTITY;
		renderer = new GuiRenderer();
	}
}
