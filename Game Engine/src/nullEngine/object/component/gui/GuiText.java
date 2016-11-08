package nullEngine.object.component.gui;

import com.sun.istack.internal.NotNull;
import math.Vector4f;
import nullEngine.control.Application;
import nullEngine.gl.Color;
import nullEngine.gl.font.Font;
import nullEngine.gl.model.Model;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.shader.GuiBasicShader;
import nullEngine.gl.shader.GuiTextShader;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * Text to be rendered to the GUI
 */
public class GuiText extends GuiComponent {

	private String text;
	private Font font;
	private boolean dirty = true;

	private float width = 0.5f;
	private float edge = 0.1f;
	private float borderWidth = 0;
	private float borderEdge = 0.01f;

	private Vector4f borderColor = Color.BLACK;

	private float borderOffsetX = 0, borderOffsetY = 0;

	private Model textModel;

	/**
	 * Create new text on the GUI
	 * @param x The x
	 * @param y The y
	 * @param size The text size
	 * @param text The text to display
	 * @param font The font to use
	 */
	public GuiText(float x, float y, float size, @NotNull String text, @NotNull Font font) {
		super(x, y, size, size);
		this.text = text;
		this.font = font;
		dirty = true;
	}

	/**
	 * Render this text
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if (dirty) {
			if (textModel == null)
				textModel = font.createString(text);
			else
				font.updateString(textModel, text);
			dirty = false;
		}
		GuiTextShader.INSTANCE.bind();
		GuiTextShader.INSTANCE.loadMVP(renderer.getMVP());
		GuiTextShader.INSTANCE.loadThickness(width, edge);
		GuiTextShader.INSTANCE.loadBorderThickness(borderWidth, borderEdge);
		GuiTextShader.INSTANCE.loadBorderColor(borderColor);
		GuiTextShader.INSTANCE.loadBorderOffset(borderOffsetX, borderOffsetY);
		GuiTextShader.INSTANCE.loadAspectRatio((float) Application.get().getHeight() / (float) Application.get().getWidth(), 1);
		font.getTexture().bind();
		super.render(renderer, object, flags);
		textModel.render();
		GuiBasicShader.INSTANCE.bind();
	}

	/**
	 * Get the text
	 * @return The text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text
	 * @param text The new text
	 */
	public void setText(String text) {
		this.text = text;
		dirty = true;
	}

	/**
	 * Get the font
	 * @return The fint
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Set the font
	 * @param font The new font
	 */
	public void setFont(Font font) {
		this.font = font;
		dirty = true;
	}

	/**
	 * Set the border color
	 * @param borderColor The new border color
	 */
	public void setBorderColor(Vector4f borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * Set the thickness of the text
	 * @param width The full brightness width
	 * @param edge The fade width
	 */
	public void setThickness(float width, float edge) {
		this.width = width;
		this.edge = edge;
	}

	/**
	 * Set the thickness of the border
	 * @param width The full brightness width of the border
	 * @param edge The fade width of the border
	 */
	public void setBorderThickness(float width, float edge) {
		borderWidth = width;
		borderEdge = edge;
	}

	/**
	 * Set the offset of the border (useful for drop shadows)
	 * @param x The x offset
	 * @param y The y offset
	 */
	public void setBorderOffset(float x, float y) {
		borderOffsetX = x;
		borderOffsetY = y;
	}
}
