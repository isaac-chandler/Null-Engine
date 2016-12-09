package nullEngine.input;

import math.Vector4f;
import nullEngine.object.component.graphics.ModelComponent;

/**
 * Struct class for mouse picking information
 */
public class MousePickInfo {
	/**
	 * The position in the world the mouse was at
	 */
	public Vector4f worldPosition;
	/**
	 * The position in the model the mouse was at
	 */
	public Vector4f localPosition;
	/**
	 * The model the mouse was on
	 */
	public ModelComponent model;
}
