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
	 *
	 * @param texture The texture
	 * @param width   The width
	 * @param height  The height
	 */
	public GuiTexture(Texture2D texture, Anchor anchor, AnchorPos anchorPos, float width, float height) {
		super(anchor, anchorPos, width, height, Quad.get(), texture);
	}
}
