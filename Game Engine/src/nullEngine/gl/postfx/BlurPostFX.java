package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.HBlurShader;
import nullEngine.gl.shader.postfx.PostFXShader;
import nullEngine.gl.shader.postfx.VBlurShader;

public class BlurPostFX extends PostFX {

	public BlurPostFX(PostFXOutput colors) {
		this(colors, 1);
	}

	public BlurPostFX(PostFXOutput colors, int downScale) {
		super(HBlurShader.INSTANCE, new PostFXOutput[]{new VBlurPostFX(colors, downScale)}, downScale, 1);
	}

	@Override
	public void updateUniforms(PostFXShader shader) {
		HBlurShader.INSTANCE.updateUniforms(1f / buffer.getWidth());
	}

	private static class VBlurPostFX extends PostFX {

		public VBlurPostFX(PostFXOutput colors, int downScale) {
			super(VBlurShader.INSTANCE, new PostFXOutput[]{colors}, 1, downScale);
		}

		@Override
		public void updateUniforms(PostFXShader shader) {
			VBlurShader.INSTANCE.updateUniforms(1f / buffer.getHeight());
		}
	}
}
