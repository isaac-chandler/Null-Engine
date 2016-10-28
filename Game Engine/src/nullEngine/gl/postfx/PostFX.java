package nullEngine.gl.postfx;

import math.Matrix4f;
import nullEngine.control.Application;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.model.Quad;
import nullEngine.gl.shader.postfx.PostFXShader;
import nullEngine.input.PostResizeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public abstract class PostFX implements PostFXOutput {
	private int hScaleDown;
	private int vScaleDown;

	private PostFXShader shader;
	private PostFXOutput[] inputs;

	private boolean renderedThisFrame = false;

	protected Framebuffer2D buffer;

	public PostFX(PostFXShader shader, PostFXOutput[] inputs, int hScaleDown, int vScaleDown) {
		this.shader = shader;
		this.inputs = inputs;
		this.vScaleDown = vScaleDown;
		this.hScaleDown = hScaleDown;
		buffer = new Framebuffer2D(Application.get().getWidth() / hScaleDown, Application.get().getHeight() / vScaleDown);
	}

	public PostFX(PostFXShader shader, PostFXOutput[] inputs, int scaleDown) {
		this(shader, inputs, scaleDown, scaleDown);
	}

	public PostFX(PostFXShader shader, PostFXOutput[] inputs) {
		this(shader, inputs, 1, 1);
	}

	@Override
	public void preRender() {
		for (PostFXOutput input : inputs)
			input.preRender();
		renderedThisFrame = false;
	}

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

	@Override
	public void postResize(PostResizeEvent event) {
		for (PostFXOutput input : inputs)
			input.postResize(event);
		buffer = new Framebuffer2D(event.width / hScaleDown, event.height / vScaleDown);
	}

	@Override
	public void preResize() {
		for (PostFXOutput input : inputs)
			input.preResize();
		buffer.delete();
	}

	@Override
	public int getTextureID() {
		return buffer.getColorTextureID();
	}

	public abstract void updateUniforms(PostFXShader shader);
}
