package nullEngine.gl.shader.postfx;

import math.Vector4f;

public class FogShader extends PostFXShader {

	public static final FogShader INSTANCE = new FogShader();

	private int location_colors;
	private int location_positions;
	private int location_skyColor;
	private int location_density;
	private int location_gradient;

	private FogShader() {
		super("default/postfx/fog");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_colors = getUniformLocation("colors");
		location_positions = getUniformLocation("positions");
		location_skyColor = getUniformLocation("skyColor");
		location_density = getUniformLocation("density");
		location_gradient = getUniformLocation("gradient");
		setSystemTextures(2);
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		loadInt(location_positions, 1);
	}

	public void loadSkyData(Vector4f skyColor, float density, float gradient) {
		loadVec3(location_skyColor, skyColor);
		loadFloat(location_density, density);
		loadFloat(location_gradient, gradient);
	}


}
