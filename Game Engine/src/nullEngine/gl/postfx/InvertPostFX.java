package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.InvertShader;
import nullEngine.gl.shader.postfx.PostFXShader;

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
