package nullEngine.gl.shader.postfx;

public class ContrastShader extends PostFXShader {

	public static final ContrastShader INSTANCE = new ContrastShader();

	private int location_colors;
	private int location_contrast;

	private ContrastShader() {
		super("default/postfx/basic", "default/postfx/contrast");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_contrast = getUniformLocation("contrast");
		setSystemTextures(1);
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
	}

	public void updateUniforms(float contrast) {
		loadFloat(location_contrast, contrast);
	}
}
