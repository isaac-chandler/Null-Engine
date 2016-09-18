package nullEngine.gl.shader.postfx;

public class BrightFilterShader extends PostFXShader {

	public static final BrightFilterShader INSTANCE = new BrightFilterShader();

	private int location_colors;

	private BrightFilterShader() {
		super("default/postfx/basic", "default/postfx/bright-filter");
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
