package nullEngine.graphics.shader.postfx;

/**
 * PostFX invert colors shader
 */
public class InvertShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final InvertShader INSTANCE = new InvertShader();

	private int location_colors;

	private InvertShader() {
		super("default/postfx/invert");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		setSystemTextures(1);
	}

	/**
	 * Bind this shader
	 */
	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
	}
}
