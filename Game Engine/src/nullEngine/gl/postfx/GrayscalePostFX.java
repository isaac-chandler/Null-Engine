package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.GrayscaleShader;
import nullEngine.gl.shader.postfx.PostFXShader;

public class GrayscalePostFX extends PostFX {

	public GrayscalePostFX(PostFXOutput colors) {
		super(GrayscaleShader.INSTANCE, new PostFXOutput[]{colors});
	}

	@Override
	public void updateUniforms(PostFXShader shader) {}
}
