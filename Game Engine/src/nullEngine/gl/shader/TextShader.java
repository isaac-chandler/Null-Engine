package nullEngine.gl.shader;

import math.Vector4f;

/**
 * A shader for text
 */
public interface TextShader {

	void loadTextSize(float textSize);

	/**
	 * Load the the character offset
	 * @param x The x
	 * @param y The y
	 */
	void loadOffset(float x, float y);

	/**
	 * Load the text color
	 * @param color The color
	 */
	void loadColor(Vector4f color);

	/**
	 * Load the text thickness
	 * @param width The width
	 * @param edge The edge size
	 */
	void loadThickness(float width, float edge);

	/**
	 * Load the border color
	 * @param borderColor The border color
	 */
	void loadBorderColor(Vector4f borderColor);

	/**
	 * Load the border thickness
	 * @param borderWidth The width
	 * @param borderEdge The edge size
	 */
	void loadBorderThickness(float borderWidth, float borderEdge);

	/**
	 * Load the border offset
	 * @param x The x
	 * @param y The y
	 */
	void loadBorderOffset(float x, float y);
}
