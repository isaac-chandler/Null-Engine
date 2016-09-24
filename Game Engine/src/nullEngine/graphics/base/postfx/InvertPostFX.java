package nullEngine.graphics.base.postfx;

import nullEngine.graphics.base.shader.postfx.InvertShader;
import nullEngine.graphics.base.shader.postfx.PostFXShader;

public class InvertPostFX extends PostFX {

	public InvertPostFX(PostFXOutput colors) {
		super(InvertShader.INSTANCE, new PostFXOutput[]{colors});
	}

	@Override
	public void updateUniforms(PostFXShader shader) {}
}
