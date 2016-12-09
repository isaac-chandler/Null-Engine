package nullEngine.graphics.shader.deferred.lighting;

import math.Matrix4f;
import nullEngine.object.component.graphics.light.PointLight;

/**
 * Deferred point light shader
 */
public class DeferredPointLightShader extends DeferredLightShader {

	/**
	 * Singleton instance
	 */
	public static final DeferredPointLightShader INSTANCE = new DeferredPointLightShader();

	private int location_lightColor;
	private int location_attenuation;
	private int location_viewMatrix;
	private int location_modelMatrix;

	private DeferredPointLightShader() {
		super("default/deferred/lighting/deferred-point");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_attenuation = getUniformLocation("attenuation");
		location_viewMatrix = getUniformLocation("viewMatrix");

	}

	/**
	 * Load a light
	 * @param light The light
	 */
	public void loadLight(PointLight light) {
		loadVec3(location_lightColor, light.getLightColor());
		loadVec3(location_attenuation, light.getSquared(), light.getLinear(), light.getConstant());
		loadMat4(location_modelMatrix, light.getObject().getRenderMatrix());
	}

	/**
	 * Load the view matrix
	 * @param viewMarix The view matrix
	 */
	public void loadViewMatrix(Matrix4f viewMarix) {
		loadMat4(location_viewMatrix, viewMarix);
	}
}
