package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.AddShader;
import nullEngine.gl.shader.postfx.BrightFilterShader;
import nullEngine.gl.shader.postfx.PostFXShader;

/**
 * Bloom postfx
 */
public class BrightFilterBloomPostFX extends PostFX {

	private float strength;

	/**
	 * Create a new bloom postfx
	 * @param colors The colors input
	 * @param strength The strength
	 */
	public BrightFilterBloomPostFX(PostFXOutput colors, float strength) {
		super(AddShader.INSTANCE, colors, new BlurPostFX(new BlurPostFX(new BrightFilterPostFX(colors), 4), 8));
		this.strength = strength;
	}

	/**
	 * Set the strength
	 * @param strength The new strength
	 */
	public void setStrength(float strength) {
		this.strength = strength;
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
		AddShader.INSTANCE.updateUniforms(1, strength);
	}

	private static class BrightFilterPostFX extends PostFX {

		BrightFilterPostFX(PostFXOutput colors) {
			super(BrightFilterShader.INSTANCE, colors);
		}

		@Override
		public void updateUniforms(PostFXShader shader) {
		}
	}
}
