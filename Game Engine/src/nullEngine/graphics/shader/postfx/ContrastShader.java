package nullEngine.graphics.shader.postfx;

/**
 * PostFX contrast shader
 */
public class ContrastShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final ContrastShader INSTANCE = new ContrastShader();

	private int location_colors;
	private int location_contrast;

	private ContrastShader() {
		super("default/postfx/contrast");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_contrast = getUniformLocation("contrast");
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

	/**
	 * Bind the contrast
	 * @param contrast The contrast
	 */
	public void loadContrast(float contrast) {
		loadFloat(location_contrast, contrast);
	}
}
