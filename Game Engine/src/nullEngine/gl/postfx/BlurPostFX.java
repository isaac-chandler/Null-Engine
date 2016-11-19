package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.HBlurShader;
import nullEngine.gl.shader.postfx.PostFXShader;
import nullEngine.gl.shader.postfx.VBlurShader;

/**
 * Gaussian blur postfx
 */
public class BlurPostFX extends PostFX {

	/**
	 * Create a new blur postfx
	 * @param colors The colors input
	 */
	public BlurPostFX(PostFXOutput colors) {
		this(colors, 1);
	}

	/**
	 * Create a new blur postfx
	 * @param colors The colors input
	 */
	public BlurPostFX(PostFXOutput colors, int downScale) {
		super(HBlurShader.INSTANCE, downScale, 1, new VBlurPostFX(colors, downScale));
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
		HBlurShader.INSTANCE.updateUniforms(1f / buffer.getWidth());
	}

	private static class VBlurPostFX extends PostFX {

		VBlurPostFX(PostFXOutput colors, int downScale) {
			super(VBlurShader.INSTANCE, 1, downScale, colors);
		}

		@Override
		public void updateUniforms(PostFXShader shader) {
			VBlurShader.INSTANCE.updateUniforms(1f / buffer.getHeight());
		}
	}
}
