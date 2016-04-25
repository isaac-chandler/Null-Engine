package nullEngine.gl.shader.deferred;

import math.Matrix4f;
import nullEngine.gl.shader.Shader;

public class DeferredShader extends Shader {

	private int location_modelMatrix;
	private int location_mvp;

	public DeferredShader(String vertex, String fragment) {
		super(vertex, fragment);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
		bindAttribute(2, "inNormal");
		bindFragData(0, "outColor");
		bindFragData(1, "outPosition");
		bindFragData(2, "outNormal");
		bindFragData(3, "outSpecular");
	}

	@Override
	protected void getUniformLocations() {
		location_modelMatrix = getUniformLocation("modelMatrix");

	}

	public void loadModelMatrix(Matrix4f modelMatrix) {
		loadMat4(location_modelMatrix, modelMatrix);
	}
}
