package nullEngine.gl;


import math.Matrix4f;

public class TestShader extends Shader {

	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_modelMatrix;
	private int location_mvp;

	public TestShader() {
		super("default/test", "default/test");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "inTexCoords");
		bindAttribute(2, "inNormal");
	}

	@Override
	protected void getUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_mvp = getUniformLocation("mvp");
	}

	public void loadModelMatrix(Matrix4f mat) {
		loadMat4(location_modelMatrix, mat);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		loadMat4(location_projectionMatrix, projectionMatrix);
	}

	public void loadViewMatrix(Matrix4f viewMatrix) {
		loadMat4(location_viewMatrix, viewMatrix);
	}

	public void loadMVP(Matrix4f mvp) {
		loadMat4(location_mvp, mvp);
	}
}
