package nullEngine.gl.shader.postfx;

/**
 * PostFX grayscale shader
 */
public class GrayscaleShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final GrayscaleShader INSTANCE = new GrayscaleShader();

	private int location_colors;

	private GrayscaleShader() {
		super("default/postfx/grayscale");
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
