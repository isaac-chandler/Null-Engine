package nullEngine.graphics.base.renderer;

import math.Matrix4f;
import nullEngine.control.Application;
import nullEngine.graphics.base.framebuffer.Framebuffer2D;
import nullEngine.graphics.base.model.Quad;
import nullEngine.graphics.base.shader.BasicShader;
import nullEngine.graphics.base.shader.GuiBasicShader;
import nullEngine.input.ResizeEvent;
import nullEngine.object.GameComponent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GuiRenderer extends Renderer {
	private Framebuffer2D framebuffer;

	public GuiRenderer() {
		framebuffer = new Framebuffer2D(Application.get().getWidth(), Application.get().getHeight());
	}

	@Override
	public void add(GameComponent component) {

	}

	@Override
	public void postRender() {
		if (Application.get().getRenderTarget() != null) {
			Application.get().getRenderTarget().bind();
		} else {
			Framebuffer2D.unbind();
		}
		GL11.glDisable(GL11.GL_BLEND);
		BasicShader.INSTANCE.bind();
		BasicShader.INSTANCE.loadProjectionMatrix(Matrix4f.IDENTITY);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer.getColorTextureID());
		GL11.glEnable(GL11.GL_BLEND);
		Quad.get().render();
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void preRender() {
		GuiBasicShader.INSTANCE.bind();
		framebuffer.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void cleanup() {
		framebuffer.delete();
	}

	@Override
	public void postResize(ResizeEvent event) {
		framebuffer = new Framebuffer2D(event.width, event.height);
	}

	@Override
	public void preResize() {
		framebuffer.delete();
	}
}
