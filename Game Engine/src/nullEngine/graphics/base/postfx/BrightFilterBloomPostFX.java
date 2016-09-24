package nullEngine.graphics.base.postfx;

import nullEngine.graphics.base.shader.postfx.AddShader;
import nullEngine.graphics.base.shader.postfx.BrightFilterShader;
import nullEngine.graphics.base.shader.postfx.PostFXShader;

public class BrightFilterBloomPostFX extends PostFX {

	private float strength;

	public BrightFilterBloomPostFX(PostFXOutput colors, float strength) {
		super(AddShader.INSTANCE, new PostFXOutput[]{colors, new BlurPostFX(new BlurPostFX(new BrightFilterPostFX(colors), 2), 8)});
		this.strength = strength;
	}

	public void setStrength(float strength) {
		this.strength = strength;
	}

	@Override
	public void updateUniforms(PostFXShader shader) {
		AddShader.INSTANCE.updateUniforms(1, strength);
	}

	private static class BrightFilterPostFX extends PostFX {

		public BrightFilterPostFX(PostFXOutput colors) {
			super(BrightFilterShader.INSTANCE, new PostFXOutput[]{colors});
		}

		@Override
		public void updateUniforms(PostFXShader shader) {
		}
	}
}
