package nullEngine.gl.shader.postfx;

public class HBlurShader extends PostFXShader {

	public static final HBlurShader INSTANCE = new HBlurShader();

	private int location_colors;

	private HBlurShader() {
		super("default/postfx/basic", "default/postfx/hblur");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		setSystemTextures(1);
	}
}
