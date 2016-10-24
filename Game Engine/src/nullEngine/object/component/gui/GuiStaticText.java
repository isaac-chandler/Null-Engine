package nullEngine.object.component.gui;

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

public class GuiStaticText extends GuiComponent {

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

	public GuiStaticText(float x, float y, float size, String text, Font font) {
		super(x, y, size, size);
		this.text = text;
		this.font = font;
		dirty = true;
	}

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		dirty = true;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		dirty = true;
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
