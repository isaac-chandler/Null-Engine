package nullEngine.gl.shader.deferred.lighting;

import math.Matrix4f;
import nullEngine.object.component.light.PointLight;

public class DeferredPointLightShader extends DeferredLightShader {

	public static final DeferredPointLightShader INSTANCE = new DeferredPointLightShader();

	private int location_lightColor;
	private int location_attenuation;
	private int location_viewMatrix;
	private int location_modelMatrix;

	private DeferredPointLightShader() {
		super("default/deferred/lighting/deferred-point");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_attenuation = getUniformLocation("attenuation");
		location_viewMatrix = getUniformLocation("viewMatrix");

	}

	public void loadLight(PointLight light) {
		loadVec3(location_lightColor, light.getLightColor());
		loadVec3(location_attenuation, light.getSquared(), light.getLinear(), light.getConstant());
		loadMat4(location_modelMatrix, light.getParent().getRenderMatrix());
	}

	public void loadViewMatrix(Matrix4f viewMarix) {
		loadMat4(location_viewMatrix, viewMarix);
	}
}
