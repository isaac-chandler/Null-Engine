package nullEngine.object.component.gui;

import com.sun.istack.internal.NotNull;
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
public abstract class GuiComponent extends GameComponent {

	public enum AnchorPos {
		TOP_LEFT, TOP, TOP_RIGHT,
		LEFT, CENTER, RIGHT,
		BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
	}

	private Vector4f color = Color.WHITE;
	private AnchorPos anchorPos;
	private Anchor anchor;

	private float width, height;

	public final ComponentAnchor TOP_LEFT, TOP, TOP_RIGHT,
			LEFT, CENTER, RIGHT,
			BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT;

	public GuiComponent(@NotNull Anchor anchor, AnchorPos anchorPos) {
		this.anchorPos = anchorPos;
		this.anchor = anchor;
		TOP_LEFT = ComponentAnchor.topLeft(this);
		TOP = ComponentAnchor.top(this);
		TOP_RIGHT = ComponentAnchor.topRight(this);
		LEFT = ComponentAnchor.left(this);
		CENTER = ComponentAnchor.center(this);
		RIGHT = ComponentAnchor.right(this);
		BOTTOM_LEFT = ComponentAnchor.bottomLeft(this);
		BOTTOM = ComponentAnchor.bottom(this);
		BOTTOM_RIGHT = ComponentAnchor.bottomRight(this);
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
			shader.loadPosition(getX(), getY());
		}
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
		return getXImpl() + getWidth() * getWidthXMul();
	}

	private float getXImpl() {
		switch (anchorPos) {
			case TOP_LEFT:
			case LEFT:
			case BOTTOM_LEFT:
				return anchor.getX() + getWidth() / 2;
			case TOP:
			case CENTER:
			case BOTTOM:
				return anchor.getX();
			case TOP_RIGHT:
			case RIGHT:
			case BOTTOM_RIGHT:
				return anchor.getX() - getWidth() / 2;
			default:
				return 0;
		}
	}

	/**
	 * Get this component's y
	 *
	 * @return The y
	 */
	public float getY() {
		return getYImpl() + getHeight() * getHeightYMul();
	}

	private float getYImpl() {
		switch (anchorPos) {
			case TOP_LEFT:
			case TOP:
			case TOP_RIGHT:
				return anchor.getY() - getHeight() / 2;
			case LEFT:
			case CENTER:
			case RIGHT:
				return anchor.getY();
			case BOTTOM_LEFT:
			case BOTTOM:
			case BOTTOM_RIGHT:
				return anchor.getY() + getHeight() / 2;
			default:
				return anchor.getY();
		}
	}

	public void setWidth(float width) {
		this.width = width;

		invalidate();
	}

	public void setHeight(float height) {
		this.height = height;

		invalidate();
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;

		invalidate();
	}


	private void invalidate() {
		TOP_LEFT.invalidate();
		TOP.invalidate();
		TOP_RIGHT.invalidate();

		LEFT.invalidate();
		CENTER.invalidate();
		RIGHT.invalidate();

		BOTTOM_LEFT.invalidate();
		BOTTOM.invalidate();
		BOTTOM_RIGHT.invalidate();
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
	 * Get this component's height
	 *
	 * @return The height
	 */
	public float getHeight() {
		return height;
	}

	public Anchor getAnchor() {
		return anchor;
	}

	protected float getWidthXMul() {
		return 0;
	}

	protected float getHeightYMul() {
		return 0;
	}
}
