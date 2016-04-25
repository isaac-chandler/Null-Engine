package nullEngine.gl.shader.postfx;

import math.Vector4f;

public class FogShader extends PostProcessingShader {

	public static final FogShader INSTANCE = new FogShader();

	private int location_skyColor;
	private int location_density;
	private int location_cutoff;

	private FogShader() {
		super("default/postfx/basic", "default/postfx/fog");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_skyColor = getUniformLocation("skyColor");
		location_density = getUniformLocation("density");
		location_cutoff = getUniformLocation("cutoff");
	}

	public void loadSkyData(Vector4f skyColor, float density, float cutoff) {
		loadVec3(location_skyColor, skyColor);
		loadFloat(location_density, density);
		loadFloat(location_cutoff, cutoff);
	}


}
