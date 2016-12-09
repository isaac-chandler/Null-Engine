package nullEngine.graphics.shader.postfx;

import math.Matrix4f;
import nullEngine.graphics.shader.Shader;

/**
 * PostFX shader
 */
public class PostFXShader extends Shader {

	private int location_viewMatrix;

	/**
	 * Create a new postfx shader
	 *
	 * @param shader The shader name
	 * @see Shader#Shader(String) Format details
	 */
	public PostFXShader(String shader) {
		super(shader);
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		location_viewMatrix = getUniformLocation("viewMatrix");
	}

	/**
	 * Update the view matrix
	 * @param viewMatrix The view matrix
	 */
	public void updateViewMatrix(Matrix4f viewMatrix) {
		loadMat4(location_viewMatrix, viewMatrix);
	}

	/**
	 * Bind the attributes
	 */
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}
}
