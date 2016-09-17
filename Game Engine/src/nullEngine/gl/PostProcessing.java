package nullEngine.gl;

import math.Matrix4f;
import nullEngine.control.Application;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.model.Quad;
import nullEngine.gl.shader.postfx.PostProcessingShader;
import nullEngine.input.ResizeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public abstract class PostProcessing {

	private PostProcessingShader shader;
	private boolean enabled = true;

	private Framebuffer2D buffer;

	public PostProcessing(PostProcessingShader shader) {
		this.shader = shader;
		buffer = new Framebuffer2D(Application.get().getWidth(), Application.get().getHeight());
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int render(int colors, int positions, int normals, int specular, int depth, Matrix4f viewMatrix) {
		if (enabled) {
			shader.bind();
			shader.updateViewMatrix(viewMatrix);
			updateUniforms(shader);
			buffer.bind();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, colors);
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, positions);
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, normals);
			GL13.glActiveTexture(GL13.GL_TEXTURE3);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, specular);
			GL13.glActiveTexture(GL13.GL_TEXTURE4);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, depth);
			Quad.get().lazyRender(0);
			return buffer.getColorTextureID();
		} else
			return colors;
	}

	public void postResize(ResizeEvent event) {
		buffer = new Framebuffer2D(event.width, event.height);
	}

	public void preResize() {
		buffer.delete();
	}

	public abstract void updateUniforms(PostProcessingShader shader);
}
