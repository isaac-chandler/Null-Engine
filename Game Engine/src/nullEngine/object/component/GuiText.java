package nullEngine.object.component;

import math.Vector4f;
import nullEngine.gl.Color;
import nullEngine.gl.font.Font;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.shader.GuiBasicShader;
import nullEngine.gl.shader.GuiTextShader;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class GuiText extends GuiComponent {

	private String text;
	private Font font;

	private float width = 0.5f;
	private float edge = 0.1f;
	private float borderWidth = 0;
	private float borderEdge = 0.01f;

	private Vector4f borderColor = Color.BLACK;

	private float borderOffsetX = 0, borderOffsetY = 0;

	public GuiText(float x, float y, float size, String text, Font font) {
		super(x, y, size, size);
		this.text = text;
		this.font = font;
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		GuiTextShader.INSTANCE.bind();
		GuiTextShader.INSTANCE.loadMVP(renderer.getMVP());
		GuiTextShader.INSTANCE.loadThickness(width, edge);
		GuiTextShader.INSTANCE.loadBorderThickness(borderWidth, borderEdge);
		GuiTextShader.INSTANCE.loadBorderColor(borderColor);
		GuiTextShader.INSTANCE.loadBorderOffset(borderOffsetX, borderOffsetY);
		super.render(renderer, object, flags);
		font.drawString(text);
		GuiBasicShader.INSTANCE.bind();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setBorderColor(Vector4f borderColor) {
		this.borderColor = borderColor;
	}

	public void setThickness(float width, float edge) {
		this.width = width;
		this.edge = edge;
	}

	public void setBorderThickness(float width, float edge) {
		borderWidth = width;
		borderEdge = edge;
	}

	public void setBorderOffset(float x, float y) {
		borderOffsetX = x;
		borderOffsetY = y;
	}
}
