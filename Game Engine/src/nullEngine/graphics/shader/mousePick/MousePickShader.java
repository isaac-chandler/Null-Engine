package nullEngine.graphics.shader.mousePick;

import nullEngine.graphics.shader.ModelMatrixShader;
import nullEngine.graphics.shader.Shader;

/**
 * Mouse picking shader
 */
public class MousePickShader extends ModelMatrixShader {

	private int location_color;

	/**
	 * Create a new mouse pick shader
	 *
	 * @param shader The shader name
	 * @see Shader#Shader(String) Format details
	 */
	public MousePickShader(String shader) {
		super(shader);
	}

	/**
	 * Bind the attributes
	 */
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindFragData(0, "outColor");
		bindFragData(1, "outWorldPosition");
		bindFragData(2, "outLocalPosition");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_color = getUniformLocation("color");
	}

	/**
	 * Load an id to an rgba color
	 * @param id The id
	 */
	public void loadIdToColor(int id) {
		int r = id >>> 24;
		int g = (id >>> 16) & 0xFF;
		int b = (id >>> 8) & 0xFF;
		int a = id & 0xFF;

		loadVec4(location_color, r / 255f, g / 255f, b / 255f, a / 255f);
	}
}
