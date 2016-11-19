package nullEngine.gl.shader.mousePick;

/**
 * Mouse picking terrain shader
 */
public class MousePickTerrainShader extends MousePickShader {

	/**
	 * Singleton instance
	 */
	public static final MousePickTerrainShader INSTANCE = new MousePickTerrainShader();

	private MousePickTerrainShader() {
		super("default/mousePick/mousePick-terrain");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		addUserTexture("height");
		addUserFloat("size");
	}
}
