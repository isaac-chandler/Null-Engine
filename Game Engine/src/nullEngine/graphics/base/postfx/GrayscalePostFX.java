package nullEngine.graphics.base.postfx;

import nullEngine.graphics.base.shader.postfx.GrayscaleShader;
import nullEngine.graphics.base.shader.postfx.PostFXShader;

public class GrayscalePostFX extends PostFX {

	public GrayscalePostFX(PostFXOutput colors) {
		super(GrayscaleShader.INSTANCE, new PostFXOutput[]{colors});
	}

	@Override
	public void updateUniforms(PostFXShader shader) {}
}
