package nullEngine.gl.shader.deferred.lighting;

import math.Matrix4f;
import nullEngine.object.component.SpotLight;

public class DeferredSpotLightShader extends DeferredLightShader {

	public static final DeferredSpotLightShader INSTANCE = new DeferredSpotLightShader();

	private int location_lightColor;
	private int location_direction;
	private int location_attenuation;
	private int location_viewMatrix;
	private int location_modelMatrix;

	private DeferredSpotLightShader() {
		super("default/deferred/lighting/deferred-spot");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_direction = getUniformLocation("direction");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_attenuation = getUniformLocation("attenuation");
		location_viewMatrix = getUniformLocation("viewMatrix");

	}

	public void loadLight(SpotLight light) {
		loadVec3(location_lightColor, light.getLightColor());
		loadVec3(location_direction, light.getDirection());
		loadVec4(location_attenuation, light.getSquared(), light.getLinear(), light.getConstant(), light.getCutoff());
		loadMat4(location_modelMatrix, light.getParent().getTransform().getMatrix());
	}

	public void loadViewMatrix(Matrix4f viewMarix) {
		loadMat4(location_viewMatrix, viewMarix);
	}
}
