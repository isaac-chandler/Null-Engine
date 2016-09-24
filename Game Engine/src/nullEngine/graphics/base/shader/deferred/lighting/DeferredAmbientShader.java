package nullEngine.graphics.base.shader.deferred.lighting;

import math.Vector4f;

public class DeferredAmbientShader extends DeferredLightShader {

	public static final DeferredAmbientShader INSTANCE = new DeferredAmbientShader();

	private int location_ambientColor;

	private DeferredAmbientShader() {
		super("default/deferred/lighting/deferred-ambient", "default/deferred/lighting/deferred-ambient");
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
