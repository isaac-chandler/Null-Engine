package nullEngine.graphics.renderer;

import math.Matrix4f;
import nullEngine.control.Application;
import nullEngine.graphics.framebuffer.Framebuffer2D;
import nullEngine.graphics.model.Quad;
import nullEngine.graphics.shader.BasicShader;
import nullEngine.graphics.shader.gui.GuiBasicShader;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.GameComponent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import util.BitFieldInt;

/**
 * Renders the GUI
 */
public class GuiRenderer extends Renderer {
	private Framebuffer2D framebuffer;

	/**
	 * Create a new GUI renderer
	 */
	public GuiRenderer() {
		framebuffer = new Framebuffer2D(Application.get().getWidth(), Application.get().getHeight());
	}

	/**
	 * Does nothing
	 * @param component The component
	 */
	@Override
	public void add(GameComponent component) {

	}

	/**
	 * Render the framebuffer to the window
	 * @param flags The render flags
	 */
	@Override
	public void postRender(BitFieldInt flags) {
		if (Application.get().getRenderTarget() != null) {
			Application.get().getRenderTarget().bind();
		} else {
			Framebuffer2D.unbind();
		}
		GL11.glDisable(GL11.GL_BLEND);
		BasicShader.INSTANCE.bind();
		BasicShader.INSTANCE.loadMVP(Matrix4f.IDENTITY);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer.getColorTextureID());
		GL11.glEnable(GL11.GL_BLEND);
		Quad.get().render();
		GL11.glDisable(GL11.GL_BLEND);
	}

	/**
	 * Bind the framebuffer
	 * @param flags The rneder flags
	 */
	@Override
	public void preRender(BitFieldInt flags) {
		GuiBasicShader.INSTANCE.bind();
		framebuffer.bind();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Delete the framebuffer
	 */
	@Override
	public void cleanup() {
		framebuffer.delete();
	}

	/**
	 * Recreate the framebuffer
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		framebuffer = new Framebuffer2D(event.width, event.height);
	}

	/**
	 * Delete the framebuffer
	 */
	@Override
	public void preResize() {
		framebuffer.delete();
	}
}
