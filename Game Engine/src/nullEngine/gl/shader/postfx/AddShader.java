package nullEngine.gl.shader.postfx;

/**
 * PostFX add shader
 */
public class AddShader extends PostFXShader {
	/**
	 * Singleton instance
	 */
	public static final AddShader INSTANCE = new AddShader();

	private int location_colors0;
	private int location_colors1;
	private int location_strength;

	private AddShader() {
		super("default/postfx/add");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors0 = getUniformLocation("colors0");
		location_colors1 = getUniformLocation("colors1");
		location_strength = getUniformLocation("strength");
		setSystemTextures(2);
	}

	/**
	 * Bind this shader
	 */
	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors0, 0);
		loadInt(location_colors1, 1);
	}

	/**
	 * Update the uniforms
	 * @param strength0 Weight of texture 0
	 * @param strength1 Weight of texture 1
	 */
	public void updateUniforms(float strength0, float strength1) {
		loadVec2(location_strength, strength0, strength1);
	}
}
