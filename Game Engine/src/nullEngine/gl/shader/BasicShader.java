package nullEngine.gl.shader;

import math.Matrix4f;

public class BasicShader extends Shader {

	public static final BasicShader INSTANCE = new BasicShader();

	private int location_projectionMatrix;

	private BasicShader() {
		super("default/basic", "default/basic");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	@Override
	protected void getUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		loadMat4(location_projectionMatrix, projectionMatrix);
	}
}
