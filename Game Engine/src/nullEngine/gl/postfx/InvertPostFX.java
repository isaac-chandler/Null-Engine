package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.InvertShader;
import nullEngine.gl.shader.postfx.PostFXShader;

public class InvertPostFX extends PostFX {

	public InvertPostFX(PostFXOutput colors) {
		super(InvertShader.INSTANCE, new PostFXOutput[]{colors});
	}

	@Override
	public void updateUniforms(PostFXShader shader) {}
}
