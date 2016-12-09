package nullEngine.object.component.graphics.gui;

import com.sun.istack.internal.NotNull;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.font.Font;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.graphics.shader.gui.GuiBasicShader;
import nullEngine.object.GameObject;
import util.BitFieldInt;

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
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		preRender();
		super.render(renderer, object, flags);
		render();
		GuiBasicShader.INSTANCE.bind();
	}

	/**
	 * Update this component
	 * @param physics
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(PhysicsEngine physics, GameObject object, double delta) {

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
