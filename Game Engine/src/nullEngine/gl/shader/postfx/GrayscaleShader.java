package nullEngine.gl.shader.postfx;

public class GrayscaleShader extends PostFXShader {

	public static final GrayscaleShader INSTANCE = new GrayscaleShader();

	private int location_colors;

	private GrayscaleShader() {
		super("default/postfx/basic", "default/postfx/grayscale");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		setSystemTextures(1);
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
	}
}
