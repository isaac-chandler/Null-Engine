package nullEngine.gl.postfx;

import nullEngine.control.Application;
import nullEngine.gl.shader.postfx.HBlurShader;
import nullEngine.gl.shader.postfx.PostFXShader;
import nullEngine.gl.shader.postfx.VBlurShader;

/**
 * Gaussian blur postfx
 */
public class BlurPostFX extends PostFX {

	private float radius;

	/**
	 * Create a new blur postfx
	 * @param colors The colors input
	 * @param downScale the output down scale factor
	 * @param radius The blur radius
	 */
	public BlurPostFX(PostFXOutput colors, int downScale, float radius) {
		super(HBlurShader.INSTANCE, downScale, 1, new VBlurPostFX(colors, downScale, radius));
		this.radius = radius;
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
		HBlurShader.INSTANCE.updateUniforms(radius);
	}

	private static class VBlurPostFX extends PostFX {

		private float radius;

		VBlurPostFX(PostFXOutput colors, int downScale, float radius) {
			super(VBlurShader.INSTANCE, 1, downScale, colors);
			this.radius = radius;
		}

		@Override
		public void updateUniforms(PostFXShader shader) {
			VBlurShader.INSTANCE.updateUniforms(radius / Application.get().getWidth() * Application.get().getHeight());
		}
	}
}
