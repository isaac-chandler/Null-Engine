package nullEngine.gl.shader.postfx;

/**
 * PostFX certical blur shader
 */
public class VBlurShader extends PostFXShader {

	/**
	 * Singleton instance
	 */
	public static final VBlurShader INSTANCE = new VBlurShader();

	private int location_colors;
	private int location_pixelSize;

	private VBlurShader() {
		super("default/postfx/vblur");
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
	 * @param pixelSize The pixelSize
	 */
	public void updateUniforms(float pixelSize) {
		loadFloat(location_pixelSize, pixelSize);
	}
}
