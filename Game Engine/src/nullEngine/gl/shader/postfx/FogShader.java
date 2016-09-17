package nullEngine.gl.shader.postfx;

import math.Vector4f;

public class FogShader extends PostProcessingShader {

	public static final FogShader INSTANCE = new FogShader();

	private int location_skyColor;
	private int location_density;
	private int location_gradient;

	private FogShader() {
		super("default/postfx/basic", "default/postfx/fog");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_skyColor = getUniformLocation("skyColor");
		location_density = getUniformLocation("density");
		location_gradient = getUniformLocation("gradient");
	}

	public void loadSkyData(Vector4f skyColor, float density, float gradient) {
		loadVec3(location_skyColor, skyColor);
		loadFloat(location_density, density);
		loadFloat(location_gradient, gradient);
	}


}
