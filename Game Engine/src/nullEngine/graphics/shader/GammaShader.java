package nullEngine.graphics.shader;

/**
 * Basic shader
 */
public class GammaShader extends Shader {

	/**
	 * Singleton instance
	 */
	public static final GammaShader INSTANCE = new GammaShader();

	private GammaShader() {
		super("default/gamma");
	}

	/**
	 * Bind the attributes
	 */
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	/**
	 * Bind the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
	}
}
