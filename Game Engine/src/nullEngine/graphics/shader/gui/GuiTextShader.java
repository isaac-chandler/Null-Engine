package nullEngine.graphics.shader.gui;

import math.Vector4f;
import nullEngine.graphics.shader.TextShader;

/**
 * Shader for GUI text
 */
public class GuiTextShader extends GuiShader implements TextShader {

	/**
	 * Singleton instance
	 */
	public static final GuiTextShader INSTANCE = new GuiTextShader();

	private int location_offset;
	private int location_thickness;
	private int location_borderThickness;
	private int location_borderColor;
	private int location_borderOffset;
	private int location_textSize;

	private GuiTextShader() {
		super("default/gui/gui-text");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_offset = getUniformLocation("offset");

		location_thickness = getUniformLocation("thickness");
		location_borderThickness = getUniformLocation("borderThickness");

		location_borderColor = getUniformLocation("borderColor");

		location_borderOffset = getUniformLocation("borderOffset");

		location_textSize = getUniformLocation("textSize");
	}

	@Override
	public void loadTextSize(float textSize) {
		loadFloat(location_textSize, textSize);
	}

	/**
	 * Load the character offset
	 * @param x The x
	 * @param y The y
	 */
	@Override
	public void loadOffset(float x, float y) {
		loadVec2(location_offset, x, y);
	}

	/**
	 * Load the thickness
	 * @param width The width
	 * @param edge The edge size
	 */
	@Override
	public void loadThickness(float width, float edge) {
		loadVec2(location_thickness, width, edge);
	}

	/**
	 * Load the border color
	 * @param borderColor The border color
	 */
	@Override
	public void loadBorderColor(Vector4f borderColor) {
		loadVec4(location_borderColor, borderColor);
	}

	/**
	 * Load the border thickness
	 * @param borderWidth The width
	 * @param borderEdge The edge size
	 */
	@Override
	public void loadBorderThickness(float borderWidth, float borderEdge) {
		loadVec2(location_borderThickness, borderWidth, borderEdge);
	}

	/**
	 * Load the border offset
	 * @param x The x
	 * @param y The y
	 */
	@Override
	public void loadBorderOffset(float x, float y) {
		loadVec2(location_borderOffset, x, y);
	}
}
