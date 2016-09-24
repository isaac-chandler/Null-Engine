package nullEngine.graphics.base.postfx;

import nullEngine.graphics.base.shader.postfx.ContrastShader;
import nullEngine.graphics.base.shader.postfx.PostFXShader;

public class ContrastPostFX extends PostFX {
	private float contrast;

	public ContrastPostFX(PostFXOutput colors, float contrast) {
		super(ContrastShader.INSTANCE, new PostFXOutput[]{colors});
		this.contrast = contrast;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
	}

	@Override
	public void updateUniforms(PostFXShader shader) {
		ContrastShader.INSTANCE.updateUniforms(contrast);
	}
}
