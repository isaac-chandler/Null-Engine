package nullEngine.object.component.graphics.gui;

import nullEngine.graphics.model.Quad;
import nullEngine.graphics.texture.Texture2D;

/**
 * An image to be renderd to the GUI
 */
public class GuiTexture extends GuiModel {

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
