package nullEngine.graphics.base.postfx;

import math.Vector4f;
import nullEngine.graphics.base.shader.postfx.FogShader;
import nullEngine.graphics.base.shader.postfx.PostFXShader;

public class FogPostFX extends PostFX {

	private Vector4f skyColor = new Vector4f();
	private float density = 0.0035f;
	private float gradient = 5f;

	public FogPostFX(PostFXOutput colors, PostFXOutput positions) {
		super(FogShader.INSTANCE, new PostFXOutput[]{colors, positions});

	}

	public void setSkyColor(Vector4f skyColor) {
		this.skyColor = skyColor;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public void setGradient(float gradient) {
		this.gradient = gradient;
	}

	@Override
	public void updateUniforms(PostFXShader shader) {
		((FogShader) shader).loadSkyData(skyColor, density, gradient);
	}
}
