package nullEngine.gl.shader;

import math.Vector4f;

/**
 * Shader for the GUI
 */
public class GuiShader extends Shader {
	private int location_color;
	private int location_pos;
	private int location_size;

	/**
	 * Create a new GUI shader
	 *
	 * @param shader The shader name
	 * @see Shader#Shader(String) Format details
	 */
	public GuiShader(String shader) {
		super(shader);
	}

	/**
	 * Bind the attributes
	 */
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		location_color = getUniformLocation("color");
		location_pos = getUniformLocation("pos");
		location_size = getUniformLocation("size");
	}

	/**
	 * Load the color
	 * @param color The color
	 */
	public void loadColor(Vector4f color) {
		loadVec4(location_color, color);
	}

	/**
	 * Load the position
	 * @param x The x
	 * @param y The y
	 */
	public void loadPosition(float x, float y) {
		loadVec2(location_pos, x, y);
	}

	/**
	 * Load the size
	 * @param width The width
	 * @param height The height
	 */
	public void loadSize(float width, float height) {
		loadVec2(location_size, width, height);
	}
}
