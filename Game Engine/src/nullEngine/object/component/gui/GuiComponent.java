package nullEngine.object.component.gui;

import math.Vector4f;
import nullEngine.gl.Color;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.shader.GuiShader;
import nullEngine.gl.shader.Shader;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class GuiComponent extends GameComponent {

	private Vector4f color = Color.WHITE;
	private float x, y, width, height;

	public GuiComponent(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if (Shader.bound() instanceof GuiShader) {
			GuiShader shader = (GuiShader) Shader.bound();
			shader.loadColor(color);
			shader.loadPosition(x, y);
			shader.loadSize(width, height);
		}
	}

	@Override
	public void update(float delta, GameObject object) {

	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
