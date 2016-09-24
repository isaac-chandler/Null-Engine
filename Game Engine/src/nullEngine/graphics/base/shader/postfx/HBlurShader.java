package nullEngine.graphics.base.shader.postfx;

public class HBlurShader extends PostFXShader {

	public static final HBlurShader INSTANCE = new HBlurShader();

	private int location_colors;
	private int location_pixelSize;

	private HBlurShader() {
		super("default/postfx/hblur", "default/postfx/blur");
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
