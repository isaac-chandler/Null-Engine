package nullEngine.gl.postfx;

import math.Matrix4f;
import nullEngine.gl.shader.postfx.MatrixShader;
import nullEngine.gl.shader.postfx.PostFXShader;

/**
 * Multiply by matrix postfx
 */
public class MatrixPostFX extends PostFX {
	private Matrix4f matrix;

	/**
	 * Create a new matrix postfx
	 * @param colors The colors input
	 * @param matrix The matrix
	 */
	public MatrixPostFX(PostFXOutput colors, Matrix4f matrix) {
		super(MatrixShader.INSTANCE, colors);
		this.matrix = matrix;
	}

	/**
	 * Set the matrix
	 * @param matrix The new matrix
	 */
	public void setMatrix(Matrix4f matrix) {
		this.matrix = matrix;
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
		MatrixShader.INSTANCE.updateUniforms(matrix);
	}

	/**
	 * Get the matrix
	 * @return The matrix
	 */
	public Matrix4f getMatrix() {
		return matrix;
	}
}
