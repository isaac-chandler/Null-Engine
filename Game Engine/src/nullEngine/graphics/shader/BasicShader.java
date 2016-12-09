package nullEngine.graphics.shader;

/**
 * Basic shader
 */
public class BasicShader extends Shader {

	/**
	 * Singleton instance
	 */
	public static final BasicShader INSTANCE = new BasicShader();

	private BasicShader() {
		super("default/basic");
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
