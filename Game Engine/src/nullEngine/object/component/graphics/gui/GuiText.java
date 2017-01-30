package nullEngine.object.component.graphics.gui;

import com.sun.istack.internal.NotNull;
import nullEngine.graphics.font.Font;
import nullEngine.graphics.shader.gui.GuiBasicShader;
import nullEngine.object.GameObject;

/**
 * Text to be rendered to the GUI
 */
public class GuiText extends GuiManagedText {

	/**
	 * Create new text on the GUI
	 *
	 * @param textSize The text textSize
	 * @param text The text to display
	 * @param font The font to use
	 */
	public GuiText(Anchor anchor, AnchorPos anchorPos, float textSize, @NotNull String text, @NotNull Font font) {
		super(anchor, anchorPos, textSize, text, font);
	}

	/**
	 * Render this text
	 * @param object   The object this component is attached to
	 */
	@Override
	public void render(GameObject object) {
		preRender();
		super.render(object);
		render();
		GuiBasicShader.INSTANCE.bind();
	}

	@Override
	protected void setTextDimensions(float width, float height) {
		setSize(width, height);
	}

	@Override
	protected float getWidthXMul() {
		return -0.5f;
	}

	@Override
	protected float getHeightYMul() {
		return 0.5f;
	}
}
