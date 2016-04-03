package nullEngine.gl.shader;

import math.Vector4f;

public class DeferredDiffuseShader extends DeferredLightingShader {

	public static final DeferredDiffuseShader INSTANCE = new DeferredDiffuseShader();

	private int location_ambientColor;

	private DeferredDiffuseShader() {
		super("default/deferred/deferred-ambient", "default/deferred/deferred-ambient");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_ambientColor = getUniformLocation("ambientColor");
	}

	public void loadAmbientColor(Vector4f color) {
		loadVec4(location_ambientColor, color);
	}

	@Override
	public void bind() {
		super.bind();
	}
}
