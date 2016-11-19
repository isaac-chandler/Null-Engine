package nullEngine.object.component.gui;

import math.Vector4f;
import nullEngine.gl.Color;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.shader.GuiShader;
import nullEngine.gl.shader.Shader;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * A component of the GUI
 */
public class GuiComponent extends GameComponent {

	private Vector4f color = Color.WHITE;
	private float x, y, width, height;

	/**
	 * Create a new GUI component
	 *
	 * @param x      The x position
	 * @param y      The y position
	 * @param width  The width
	 * @param height The height
	 */
	public GuiComponent(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Render this GUI component
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if (Shader.bound() instanceof GuiShader) {
			GuiShader shader = (GuiShader) Shader.bound();
			shader.loadColor(color);
			shader.loadPosition(x, y);
			shader.loadSize(width, height);
		}
	}

	/**
	 * Do nothing
	 *
	 * @param delta  The time since update was last called
	 * @param object The object this component is attached to
	 */
	@Override
	public void update(double delta, GameObject object) {

	}

	/**
	 * Get this component's color (Default white)
	 *
	 * @return The color
	 */
	public Vector4f getColor() {
		return color;
	}

	/**
	 * Set this component's color
	 *
	 * @param color The new color
	 */
	public void setColor(Vector4f color) {
		this.color = color;
	}

	/**
	 * Get this component's x
	 *
	 * @return The x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Set this component's x
	 *
	 * @param x The new x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get this component's y
	 *
	 * @return The y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Set this component's y
	 *
	 * @param y The new y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Get this component's width
	 *
	 * @return The width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Set this component's width
	 *
	 * @param width The new width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Get this component's height
	 *
	 * @return The height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Set this component's height
	 *
	 * @param height The new height
	 */
	public void setHeight(float height) {
		this.height = height;
	}
}
