package nullEngine.graphics.postfx;

import nullEngine.graphics.shader.postfx.InvertShader;
import nullEngine.graphics.shader.postfx.PostFXShader;

/**
 * Invert collors postfx
 */
public class InvertPostFX extends PostFX {

	/**
	 * Create a new invert postfx
	 * @param colors The colors input
	 */
	public InvertPostFX(PostFXOutput colors) {
		super(InvertShader.INSTANCE, colors);
	}

	/**
	 * Does nothing
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
	}
}
