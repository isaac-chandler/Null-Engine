package nullEngine.graphics.postfx;

import nullEngine.graphics.shader.postfx.GrayscaleShader;
import nullEngine.graphics.shader.postfx.PostFXShader;

/**
 * Grayscale postfx
 */
public class GrayscalePostFX extends PostFX {

	/**
	 * Create a new grayscale postfx
	 * @param colors The colors input
	 */
	public GrayscalePostFX(PostFXOutput colors) {
		super(GrayscaleShader.INSTANCE, colors);
	}

	/**
	 * Does nothing
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
	}
}
