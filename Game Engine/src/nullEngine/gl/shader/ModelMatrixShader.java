package nullEngine.gl.shader;

import math.Matrix4f;

public abstract class ModelMatrixShader extends Shader {

	private int location_modelMatrix;

	public ModelMatrixShader(String vertex, String geometry, String fragment) {
		super(vertex, geometry, fragment);
	}

	public ModelMatrixShader(String vertex, String fragment) {
		super(vertex, fragment);
	}


	@Override
	protected void getUniformLocations() {
		location_modelMatrix = getUniformLocation("modelMatrix");
	}


	public void loadModelMatrix(Matrix4f modelMatrix) {
		loadMat4(location_modelMatrix, modelMatrix);
	}
}
