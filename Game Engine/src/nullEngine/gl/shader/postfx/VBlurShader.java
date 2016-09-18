package nullEngine.gl.shader.postfx;

public class VBlurShader extends PostFXShader {

	public static final VBlurShader INSTANCE = new VBlurShader();

	private int location_colors;

	private VBlurShader() {
		super("default/postfx/basic", "default/postfx/vblur");
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
