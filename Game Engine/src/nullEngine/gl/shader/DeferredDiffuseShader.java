package nullEngine.gl.shader;

import nullEngine.object.component.DirectionalLight;

public class DeferredDiffuseShader extends DeferredLightingShader {

	public static final DeferredDiffuseShader INSTANCE = new DeferredDiffuseShader();

	private int location_lightColor;
	private int location_lightPos;

	private DeferredDiffuseShader() {
		super("default/deferred/deferred-diffuse", "default/deferred/deferred-diffuse");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_lightPos = getUniformLocation("lightPos");
	}

	public void loadLight(DirectionalLight light) {
		loadVec3(location_lightPos, light.getParent().getTransform().getWorldPos());
		loadVec4(location_lightColor, light.getLightColor());
	}
}
