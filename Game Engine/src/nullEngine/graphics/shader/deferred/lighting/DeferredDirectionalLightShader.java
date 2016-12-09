package nullEngine.graphics.shader.deferred.lighting;

import math.Matrix4f;
import nullEngine.object.component.graphics.light.DirectionalLight;

/**
 * Deferred firectional light shader
 */
public class DeferredDirectionalLightShader extends DeferredLightShader {

	/**
	 * Singleton instance
	 */
	public static final DeferredDirectionalLightShader INSTANCE = new DeferredDirectionalLightShader();

	private int location_lightColor;
	private int location_direction;
	private int location_viewMatrix;

	private DeferredDirectionalLightShader() {
		super("default/deferred/lighting/deferred-directional");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_direction = getUniformLocation("direction");
		location_viewMatrix = getUniformLocation("viewMatrix");
	}

	/**
	 * Load a light
	 * @param light The light
	 */
	public void loadLight(DirectionalLight light) {
		loadVec3(location_direction, light.getDirection());
		loadVec3(location_lightColor, light.getLightColor());
	}

	/**
	 * load the view matrix
	 * @param viewMarix The view matrix
	 */
	public void loadViewMatrix(Matrix4f viewMarix) {
		loadMat4(location_viewMatrix, viewMarix);
	}
}
