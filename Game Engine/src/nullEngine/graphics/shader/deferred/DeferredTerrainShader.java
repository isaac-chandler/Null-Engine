package nullEngine.graphics.shader.deferred;

public class DeferredTerrainShader extends DeferredShader {

	/**
	 * Singleton instance
	 */
	public static final DeferredTerrainShader INSTANCE = new DeferredTerrainShader();

	private DeferredTerrainShader() {
		super("default/deferred/deferred-terrain");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindFragData(0, "outColor");
		bindFragData(1, "outPosition");
		bindFragData(2, "outNormal");
		bindFragData(3, "outSpecular");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		addUserVector("reflectivity");
		addUserVector("shineDamper");
		addUserVector("lightingAmount");
		addUserFloat("tileCount");
		addUserTexture("blend");
		addUserTexture("aTexture");
		addUserTexture("rTexture");
		addUserTexture("gTexture");
		addUserTexture("bTexture");
		addUserTexture("height");
		addUserFloat("size");
		addUserFloat("offset");
	}
}
