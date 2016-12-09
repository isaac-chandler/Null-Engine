package nullEngine.control.layer;

import math.Matrix4f;
import nullEngine.control.Application;
import nullEngine.graphics.renderer.GuiRenderer;
import nullEngine.input.PostResizeEvent;
import nullEngine.object.component.graphics.gui.GuiComponent;
import nullEngine.object.component.graphics.gui.PositionAnchor;

public class LayerGUI extends Layer {

	private float width, height;

	public final PositionAnchor TOP_LEFT = new PositionAnchor(0, 0);
	public final PositionAnchor TOP = new PositionAnchor(0, 0);
	public final PositionAnchor TOP_RIGHT = new PositionAnchor(0, 0);
	public final PositionAnchor LEFT = new PositionAnchor(0, 0);
	public final PositionAnchor CENTER = new PositionAnchor(0, 0);
	public final PositionAnchor RIGHT = new PositionAnchor(0, 0);
	public final PositionAnchor BOTTOM_LEFT = new PositionAnchor(0, 0);
	public final PositionAnchor BOTTOM = new PositionAnchor(0, 0);
	public final PositionAnchor BOTTOM_RIGHT = new PositionAnchor(0, 0);

	/**
	 * Create a new GUI layer
	 */
	public LayerGUI() {
		super(null);
		this.width = 100f * Application.get().getWidth() / Application.get().getHeight();
		this.height = 100f;
		setSize();
		renderer = new GuiRenderer();
	}

	@Override
	public void postResize(PostResizeEvent event) {
		this.width = 100f * event.width / event.height;
		this.height = 100f;
		setSize();
	}

	private void setSize() {
		TOP_LEFT.set(0, height);
		TOP.set(width / 2, height);
		TOP_RIGHT.set(width, height);
		LEFT.set(0, height / 2);
		CENTER.set(width / 2, height / 2);
		RIGHT.set(width, height / 2);
		BOTTOM_LEFT.set(0, 0);
		BOTTOM.set(width / 2, 0);
		BOTTOM_RIGHT.set(width, 0);
		projectionMatrix = Matrix4f.setOrthographic(0, width, 0, height, -5, 5, projectionMatrix);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public static float getMouseX(float x) {
		return x * 100f / Application.get().getHeight();
	}

	public static float getMouseY(float y) {
		return y * 100f / Application.get().getHeight();
	}

	public static float getRelativeMouseX(float x, GuiComponent component) {
		return getMouseX(x) - component.getX();
	}

	public static float getRelativeMouseY(float y, GuiComponent component) {
		return getMouseY(y) - component.getY();
	}

	public static boolean isInComponent(float x, float y, GuiComponent component) {
		x = getMouseX(x);
		y = getMouseY(y);

		return component.contains(x, y);
	}

	public static boolean isInRect(float x, float y, float rx, float ry, float rwidth, float rheight) {
		x = getMouseX(x);
		y = getMouseY(y);

		return x >= rx && y >= ry && x <= rx + rwidth && y <= ry + rheight;
	}

	public static boolean isInComponentRect(float x, float y, GuiComponent component, float rx, float ry, float rwidth, float rheight) {
		x = getRelativeMouseX(x, component);
		y = getRelativeMouseY(y, component);

		return x >= rx && y >= ry && x <= rx + rwidth && y <= ry + rheight;
	}
}
