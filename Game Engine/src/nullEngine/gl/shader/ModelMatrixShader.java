package nullEngine.gl.shader;

import math.Matrix4f;

/**
 * A shader with a model matrix
 */
public abstract class ModelMatrixShader extends Shader {

	private int location_modelMatrix;

	/**
	 * Create a new model matrix shader
	 * @param shader The shader name
	 * @see Shader#Shader(String) Format details
	 */
	public ModelMatrixShader(String shader) {
		super(shader);
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		location_modelMatrix = getUniformLocation("modelMatrix");
	}

	/**
	 * Bind the model matrix
	 * @param modelMatrix The matrix
	 */
	public void loadModelMatrix(Matrix4f modelMatrix) {
		loadMat4(location_modelMatrix, modelMatrix);
	}
}
