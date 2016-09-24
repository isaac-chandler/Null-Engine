package nullEngine.object.component;

import nullEngine.graphics.base.model.Quad;
import nullEngine.graphics.base.texture.Texture2D;

public class GuiTexture extends GuiModel {

	private Texture2D texture;

	public GuiTexture(Texture2D texture, float x, float y, float width, float height) {
		super(x, y, width, height, Quad.get(), texture);
	}
}
