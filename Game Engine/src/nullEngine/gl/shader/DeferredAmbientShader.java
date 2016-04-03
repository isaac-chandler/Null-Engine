package nullEngine.gl.shader;

import math.Vector4f;

public class DeferredAmbientShader extends DeferredLightingShader {

	public static final DeferredAmbientShader INSTANCE = new DeferredAmbientShader();

	private int location_ambientColor;

	private DeferredAmbientShader() {
		super("default/deferred/deferred-ambient", "default/deferred/deferred-ambient");
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
