package nullEngine.graphics.base.shader.postfx;

public class InvertShader extends PostFXShader {

	public static final InvertShader INSTANCE = new InvertShader();

	private int location_colors;

	private InvertShader() {
		super("default/postfx/basic", "default/postfx/invert");
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
