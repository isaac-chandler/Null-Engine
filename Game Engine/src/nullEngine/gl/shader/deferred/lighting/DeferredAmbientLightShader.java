package nullEngine.gl.shader.deferred.lighting;

import math.Vector4f;

/**
 * Deferred ambient light shader
 */
public class DeferredAmbientLightShader extends DeferredLightShader {

	/**
	 * Singleton instance
	 */
	public static final DeferredAmbientLightShader INSTANCE = new DeferredAmbientLightShader();

	private int location_ambientColor;

	private DeferredAmbientLightShader() {
		super("default/deferred/lighting/deferred-ambient");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_ambientColor = getUniformLocation("ambientColor");
	}

	/**
	 * Load the ambient color
	 * @param color The ambient color
	 */
	public void loadAmbientColor(Vector4f color) {
		loadVec3(location_ambientColor, color);
	}
}
