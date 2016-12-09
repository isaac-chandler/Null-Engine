package nullEngine.graphics.shader.postfx;

import math.Matrix4f;

/**
 * PostFX shader to multiply all pixels in an image by a matrix
 */
public class MatrixShader extends PostFXShader {
	/**
	 * Singleton instance
	 */
	public static final MatrixShader INSTANCE = new MatrixShader();

	private int location_colors;
	private int location_contrast;

	private MatrixShader() {
		super("default/postfx/matrix");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_contrast = getUniformLocation("matrix");
	}

	/**
	 * Bind this shader
	 */
	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		setSystemTextures(1);
	}

	/**
	 * Update the uniforms
	 * @param matrix The matrix
	 */
	public void updateUniforms(Matrix4f matrix) {
		loadMat4(location_contrast, matrix);
	}
}
