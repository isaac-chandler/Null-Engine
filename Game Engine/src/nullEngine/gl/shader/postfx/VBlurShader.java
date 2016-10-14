package nullEngine.gl.shader.postfx;

public class VBlurShader extends PostFXShader {

	public static final VBlurShader INSTANCE = new VBlurShader();

	private int location_colors;
	private int location_pixelSize;

	private VBlurShader() {
		super("default/postfx/vblur");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_pixelSize = getUniformLocation("pixelSize");
		setSystemTextures(1);
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
	}

	public void updateUniforms(float pixelSize) {
		loadFloat(location_pixelSize, pixelSize);
	}
}
