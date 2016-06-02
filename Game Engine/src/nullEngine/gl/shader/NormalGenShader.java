package nullEngine.gl.shader;

import math.Matrix4f;

public class NormalGenShader extends Shader {

	public static final NormalGenShader INSTANCE = new NormalGenShader();

	private int location_maxHeight;
	private int location_offset;

	private NormalGenShader() {
		super("default/basic", "default/normal-gen");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	@Override
	protected void getUniformLocations() {
		location_maxHeight = getUniformLocation("maxHeight");
		location_offset = getUniformLocation("offset");
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		loadMVP(projectionMatrix);
	}

	public void updateUniforms(float maxHeight, int size) {
		loadMVP(Matrix4f.IDENTITY);
		loadFloat(location_maxHeight, maxHeight);
		loadVec2(location_offset, 1.0f / size, 0);
	}
}
