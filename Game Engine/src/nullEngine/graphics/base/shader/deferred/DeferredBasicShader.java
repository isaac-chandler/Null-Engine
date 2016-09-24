package nullEngine.graphics.base.shader.deferred;


public class DeferredBasicShader extends DeferredShader {

	public static final DeferredBasicShader INSTANCE = new DeferredBasicShader();

	private DeferredBasicShader() {
		super("default/deferred/deferred-basic", "default/deferred/deferred-basic");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		addUserTexture("diffuse");
		addUserFloat("reflectivity");
		addUserFloat("shineDamper");
		addUserFloat("lightingAmount");
	}
}
