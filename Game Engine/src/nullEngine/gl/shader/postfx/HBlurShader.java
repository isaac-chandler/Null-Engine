package nullEngine.gl.shader.postfx;

/**
 * PostFX horizontal blur shader
 */
public class HBlurShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final HBlurShader INSTANCE = new HBlurShader();

	private int location_colors;
	private int location_pixelSize;

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
		location_pixelSize = getUniformLocation("pixelSize");
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
	 * @param pixelSize The pixel size
	 */
	public void updateUniforms(float pixelSize) {
		loadFloat(location_pixelSize, pixelSize);
	}
}
