package nullEngine.gl.shader.postfx;

import math.Matrix4f;

public class MatrixShader extends PostFXShader {

	public static final MatrixShader INSTANCE = new MatrixShader();

	private int location_colors;
	private int location_contrast;

	private MatrixShader() {
		super("default/postfx/basic", "default/postfx/matrix");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_contrast = getUniformLocation("matrix");
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		setSystemTextures(1);
	}

	public void updateUniforms(Matrix4f matrix) {
		loadMat4(location_contrast, matrix);
	}
}
