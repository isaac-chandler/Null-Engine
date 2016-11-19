package nullEngine.gl.postfx;

import math.Vector4f;
import nullEngine.gl.shader.postfx.FogShader;
import nullEngine.gl.shader.postfx.PostFXShader;

/**
 * Fog postfx
 */
public class FogPostFX extends PostFX {

	private Vector4f skyColor = new Vector4f();
	private float density = 0.004f;
	private float gradient = 4f;

	/**
	 * Create a new fog postfx
	 * @param colors The colors input
	 * @param positions The positions input
	 */
	public FogPostFX(PostFXOutput colors, PostFXOutput positions) {
		super(FogShader.INSTANCE, colors, positions);

	}

	/**
	 * Set the sky color
	 * @param skyColor The new sky color
	 */
	public void setSkyColor(Vector4f skyColor) {
		this.skyColor = skyColor;
	}

	/**
	 * Set the density
	 * @param density The new density
	 */
	public void setDensity(float density) {
		this.density = density;
	}

	/**
	 * Set the gradient
	 * @param gradient The new gradient
	 */
	public void setGradient(float gradient) {
		this.gradient = gradient;
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
		((FogShader) shader).loadSkyData(skyColor, density, gradient);
	}
}
