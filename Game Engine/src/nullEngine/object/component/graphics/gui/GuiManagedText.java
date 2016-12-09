package nullEngine.object.component.graphics.gui;

import com.sun.istack.internal.NotNull;
import math.Vector4f;
import nullEngine.graphics.Color;
import nullEngine.graphics.font.Font;
import nullEngine.graphics.model.Model;
import nullEngine.graphics.shader.gui.GuiTextShader;

/**
 * Text to be rendered to the GUI
 */
public abstract class GuiManagedText extends GuiComponent  {

	private String text;
	private Font font;
	private boolean dirty = true;

	private float width = 0.5f;
	private float edge = 0.1f;
	private float borderWidth = 0;
	private float borderEdge = 0.01f;

	private float textSize;
	private float textWidth;
	private float textHeight;

	private Vector4f borderColor = Color.WHITE;
	private Vector4f textColor = Color.WHITE;

	private float borderOffsetX = 0, borderOffsetY = 0;

	private Model textModel;

	/**
	 * Create new text on the GUI
	 *
	 * @param textSize The text textSize
	 * @param text The text to display
	 * @param font The font to use
	 */
	public GuiManagedText(Anchor anchor, AnchorPos anchorPos, float textSize, @NotNull String text, @NotNull Font font) {
		super(anchor, anchorPos);
		this.text = text;
		this.font = font;
		this.textSize = textSize;
		updateTextSize();
		updateSize();
		dirty = true;
	}

	public void preRender() {
		if (dirty) {
			if (textModel == null)
				textModel = font.createString(text);
			else
				font.updateString(textModel, text);
			dirty = false;
		}
		GuiTextShader.INSTANCE.bind();
		GuiTextShader.INSTANCE.loadThickness(width, edge);
		GuiTextShader.INSTANCE.loadBorderThickness(borderWidth, borderEdge);
		GuiTextShader.INSTANCE.loadColor(textColor);
		GuiTextShader.INSTANCE.loadBorderColor(borderColor);
		GuiTextShader.INSTANCE.loadBorderOffset(borderOffsetX, borderOffsetY);
		GuiTextShader.INSTANCE.loadTextSize(textSize / font.getLineHeight());
		font.getTexture().bind();
	}

	public void render() {
		textModel.render();
	}

	/**
	 * Get the text
	 *
	 * @return The text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text
	 *
	 * @param text The new text
	 */
	public void setText(String text) {
		this.text = text;
		dirty = true;
		updateTextSize();
		updateSize();
	}

	/**
	 * Get the font
	 *
	 * @return The fint
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Set the font
	 *
	 * @param font The new font
	 */
	public void setFont(Font font) {
		this.font = font;
		dirty = true;
		updateTextSize();
		updateSize();
	}

	/**
	 * Set the border color
	 *
	 * @param borderColor The new border color
	 */
	public void setBorderColor(Vector4f borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * Set the thickness of the text
	 *
	 * @param width The full brightness width
	 * @param edge  The fade width
	 */
	public void setThickness(float width, float edge) {
		this.width = width;
		this.edge = edge;
	}

	/**
	 * Set the thickness of the border
	 *
	 * @param width The full brightness width of the border
	 * @param edge  The fade width of the border
	 */
	public void setBorderThickness(float width, float edge) {
		borderWidth = width;
		borderEdge = edge;
	}

	/**
	 * Set the offset of the border (useful for drop shadows)
	 *
	 * @param x The x offset
	 * @param y The y offset
	 */
	public void setBorderOffset(float x, float y) {
		borderOffsetX = x;
		borderOffsetY = y;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
		updateSize();
	}

	public Vector4f getBorderColor() {
		return borderColor;
	}

	public Vector4f getTextColor() {
		return textColor;
	}

	public void setTextColor(Vector4f textColor) {
		this.textColor = textColor;
	}

	private void updateTextSize() {
		textWidth = font.getWidth(text);
		textHeight = font.getHeight(text);
	}

	private void updateSize() {
		setTextDimensions(getTextWidth(), getTextHeight());
	}

	protected abstract void setTextDimensions(float width, float height);

	public float getTextWidth() {
		return textWidth * textSize / font.getLineHeight();
	}

	public float getTextHeight() {
		return textHeight * textSize / font.getLineHeight();
	}
}
