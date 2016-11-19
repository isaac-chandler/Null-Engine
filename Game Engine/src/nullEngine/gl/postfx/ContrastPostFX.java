package nullEngine.gl.postfx;

import nullEngine.gl.shader.postfx.ContrastShader;
import nullEngine.gl.shader.postfx.PostFXShader;

/**
 * Contrast postfx
 */
public class ContrastPostFX extends PostFX {
	private float contrast;

	/**
	 * Create a new contrast postfx
	 * @param colors The colors input
	 * @param contrast The contrast
	 */
	public ContrastPostFX(PostFXOutput colors, float contrast) {
		super(ContrastShader.INSTANCE, colors);
		this.contrast = contrast;
	}

	/**
	 * Set the contrast
	 * @param contrast The contrast
	 */
	public void setContrast(float contrast) {
		this.contrast = contrast;
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	@Override
	public void updateUniforms(PostFXShader shader) {
		ContrastShader.INSTANCE.loadContrast(contrast);
	}
}
