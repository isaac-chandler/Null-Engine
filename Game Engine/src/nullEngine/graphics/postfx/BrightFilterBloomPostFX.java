package nullEngine.graphics.postfx;

import nullEngine.graphics.shader.postfx.AddShader;
import nullEngine.graphics.shader.postfx.BrightFilterShader;
import nullEngine.graphics.shader.postfx.PostFXShader;

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
		super(AddShader.INSTANCE, colors, new BlurPostFX(new BlurPostFX(new BrightFilterPostFX(colors), 4, 0.01f), 8, 0.05f));
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
