package nullEngine.gl.shader.postfx;

/**
 * PostFX bright filter shader
 */
public class BrightFilterShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final BrightFilterShader INSTANCE = new BrightFilterShader();

	private int location_colors;

	private BrightFilterShader() {
		super("default/postfx/bright-filter");
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
