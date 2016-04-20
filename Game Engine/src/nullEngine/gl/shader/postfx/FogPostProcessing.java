package nullEngine.gl.shader.postfx;

import math.Vector4f;
import nullEngine.gl.PostProcessing;

public class FogPostProcessing extends PostProcessing {

	private Vector4f skyColor = new Vector4f();
	private float density = 0.001f;
	private float cutoff = 0.3f;

	public FogPostProcessing(int width, int height) {
		super(FogShader.INSTANCE, width, height);
	}

	public void setSkyColor(Vector4f skyColor) {
		this.skyColor = skyColor;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

	@Override
	public void updateUniforms(PostProcessingShader shader) {
		((FogShader) shader).loadSkyData(skyColor, density, cutoff);
	}
}
