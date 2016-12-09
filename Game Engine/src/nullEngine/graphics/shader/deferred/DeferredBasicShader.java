package nullEngine.graphics.shader.deferred;

/**
 * Deferred rendering basic shader
 */
public class DeferredBasicShader extends DeferredShader {

	/**
	 * Singleton instance
	 */
	public static final DeferredBasicShader INSTANCE = new DeferredBasicShader();

	private DeferredBasicShader() {
		super("default/deferred/deferred-basic");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		addUserTexture("diffuse");
		addUserFloat("reflectivity");
		addUserFloat("shineDamper");
		addUserFloat("lightingAmount");
	}
}
