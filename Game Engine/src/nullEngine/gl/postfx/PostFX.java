package nullEngine.gl.postfx;

import math.Matrix4f;
import nullEngine.control.Application;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.model.Quad;
import nullEngine.gl.shader.postfx.PostFXShader;
import nullEngine.input.PostResizeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Post processing effect
 */
public abstract class PostFX implements PostFXOutput {
	private int hScaleDown;
	private int vScaleDown;

	private PostFXShader shader;
	private PostFXOutput[] inputs;

	private boolean renderedThisFrame = false;

	/**
	 * The framebuffer
	 */
	protected Framebuffer2D buffer;

	/**
	 * Create a new post processing effect
	 * @param shader The shader
	 * @param hScaleDown The horizontal downscale
	 * @param vScaleDown The vertical downscale
	 * @param inputs The input textures
	 */
	public PostFX(PostFXShader shader, int hScaleDown, int vScaleDown, PostFXOutput... inputs) {
		this.shader = shader;
		this.inputs = inputs;
		this.vScaleDown = vScaleDown;
		this.hScaleDown = hScaleDown;
		buffer = new Framebuffer2D(Application.get().getWidth() / hScaleDown, Application.get().getHeight() / vScaleDown);
	}

	/**
	 * Create a new post processing effect
	 * @param shader The shader
	 * @param scaleDown The downscale
	 * @param inputs The input textures
	 */
	public PostFX(PostFXShader shader, int scaleDown, PostFXOutput... inputs) {
		this(shader, scaleDown, scaleDown, inputs);
	}

	/**
	 * Create a new post processing effect
	 * @param shader The shader
	 * @param inputs The input textures
	 */
	public PostFX(PostFXShader shader, PostFXOutput... inputs) {
		this(shader, 1, 1, inputs);
	}

	/**
	 * Set renderedThisFrame to <code>false</code>
	 */
	@Override
	public void preRender() {
		for (PostFXOutput input : inputs)
			input.preRender();
		renderedThisFrame = false;
	}

	/**
	 * Render this post processing effect
	 * @param viewMatrix The view matrix
	 */
	@Override
	public void render(Matrix4f viewMatrix) {
		if (!renderedThisFrame) {
			renderedThisFrame = true;
			for (PostFXOutput input : inputs) {
				input.render(viewMatrix);
			}
			for (int i = 0; i < inputs.length; i++) {
				GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, inputs[i].getTextureID());
			}
			shader.bind();
			shader.updateViewMatrix(viewMatrix);
			updateUniforms(shader);
			buffer.bind();
			Quad.get().lazyRender(0);
		}
	}

	/**
	 * Resize the framebuffer
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		for (PostFXOutput input : inputs)
			input.postResize(event);
		buffer = new Framebuffer2D(event.width / hScaleDown, event.height / vScaleDown);
	}

	/**
	 * Delete the framebuffer
	 */
	@Override
	public void preResize() {
		for (PostFXOutput input : inputs)
			input.preResize();
		buffer.delete();
	}

	/**
	 * Get the framebuffer colors
	 * @return The framebuffer colors
	 */
	@Override
	public int getTextureID() {
		return buffer.getColorTextureID();
	}

	/**
	 * Update the uniforms
	 * @param shader The shader
	 */
	public abstract void updateUniforms(PostFXShader shader);
}
