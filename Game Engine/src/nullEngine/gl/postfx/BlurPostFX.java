package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.HBlurShader;
import nullEngine.gl.shader.postfx.PostFXShader;
import nullEngine.gl.shader.postfx.VBlurShader;

public class BlurPostFX extends PostFX {

	public BlurPostFX(PostFXOutput colors) {
		super(HBlurShader.INSTANCE, new PostFXOutput[]{new VBlurPostFX(colors)}, 1, 1);
	}

	@Override
	public void updateUniforms(PostFXShader shader) {}

	private static class VBlurPostFX extends PostFX {

		public VBlurPostFX(PostFXOutput colors) {
			super(VBlurShader.INSTANCE, new PostFXOutput[]{colors});
		}

		@Override
		public void updateUniforms(PostFXShader shader) {

		}
	}
}
