package nullEngine.gl.shader;

import math.Matrix4f;

public class BasicShader extends Shader {

	public static final BasicShader INSTANCE = new BasicShader();

	private BasicShader() {
		super("default/basic", "default/basic");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	@Override
	protected void getUniformLocations() {}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		loadMVP(projectionMatrix);
	}
}
