package nullEngine.graphics.shader.postfx;

/**
 * PostFX horizontal blur shader
 */
public class HBlurShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final HBlurShader INSTANCE = new HBlurShader();

	private int location_colors;
	private int location_radius;

	private HBlurShader() {
		super("default/postfx/hblur");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_radius = getUniformLocation("radius");
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
	 * Update the uniforms
	 * @param radius The blur radius
	 */
	public void updateUniforms(float radius) {
		loadFloat(location_radius, radius);
	}
}
