package nullEngine.object.component.gui;

import nullEngine.gl.model.Quad;
import nullEngine.gl.texture.Texture2D;

/**
 * An image to be renderd to the GUI
 */
public class GuiTexture extends GuiModel {

	private Texture2D texture;

	/**
	 * Creare a new GUI image
	 * @param texture The texture
	 * @param x The x
	 * @param y The y
	 * @param width The width
	 * @param height The height
	 */
	public GuiTexture(Texture2D texture, float x, float y, float width, float height) {
		super(x, y, width, height, Quad.get(), texture);
	}
}
