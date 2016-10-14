package nullEngine.gl.shader.deferred.lighting;

import math.Vector4f;

public class DeferredAmbientLightShader extends DeferredLightShader {

	public static final DeferredAmbientLightShader INSTANCE = new DeferredAmbientLightShader();

	private int location_ambientColor;

	private DeferredAmbientLightShader() {
		super("default/deferred/lighting/deferred-ambient");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_ambientColor = getUniformLocation("ambientColor");
	}

	public void loadAmbientColor(Vector4f color) {
		loadVec3(location_ambientColor, color);
	}
}
