package nullEngine.object.component.graphics.gui;

import com.sun.istack.internal.NotNull;
import math.MathUtil;
import nullEngine.control.Application;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.control.layer.LayerGUI;
import nullEngine.graphics.Color;
import nullEngine.graphics.font.Font;
import nullEngine.graphics.model.Quad;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.graphics.shader.gui.GuiBasicShader;
import nullEngine.graphics.shader.gui.GuiQuadShader;
import nullEngine.graphics.shader.gui.GuiTextShader;
import nullEngine.input.Input;
import nullEngine.input.MouseEvent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class GuiDebugSlider extends GuiManagedText {

	private ButtonState state = ButtonState.UP;

	private static final String DEFAULT_VALUE_FORMAT = "%.1f";

	private float lineHeight = 20;
	private float lineWidth = 0.3f;
	private float boxSize = 3;
	private float value, minVal, maxVal;
	private String valueFormat = DEFAULT_VALUE_FORMAT;
	private GuiDebug.DebugSetter<Float> setter;

	public GuiDebugSlider(@NotNull Anchor anchor, AnchorPos anchorPos, GuiDebug.DebugSetter<Float> setter, float minVal, float maxVal, float defaultVal, Font font) {
		super(anchor, anchorPos, 3, " ", font);
		this.setter = setter;
		this.minVal = minVal;
		this.maxVal = maxVal;
		setValue(MathUtil.clamp(defaultVal, minVal, maxVal));
		updateSize();
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();

		GuiQuadShader.INSTANCE.bind();
		GuiQuadShader.INSTANCE.loadMVP(renderer.getMVP());
		GuiQuadShader.INSTANCE.loadPosition(x, y + height / 2 - lineHeight / 2 - boxSize / 2);
		GuiQuadShader.INSTANCE.loadSize(lineWidth / 2, lineHeight / 2);
		GuiQuadShader.INSTANCE.loadColor(Color.WHITE);
		Quad.get().render();
		GuiQuadShader.INSTANCE.loadPosition(x, (y + height / 2 - lineHeight - boxSize / 2) + (value - minVal) / (maxVal - minVal) * lineHeight);
		GuiQuadShader.INSTANCE.loadSize(boxSize / 2, boxSize / 2);

		switch (state) {
			case DOWN:
				GuiQuadShader.INSTANCE.loadColor(Color.BLACK);
				break;
			case HOVER:
				GuiQuadShader.INSTANCE.loadColor(Color.BLUE);
				break;
			case UP:
			default:
				GuiQuadShader.INSTANCE.loadColor(Color.WHITE);
				break;
		}

		Quad.get().render();
		preRender();
		GuiTextShader.INSTANCE.loadPosition(x - width / 2, y - height / 2 + getTextHeight());
		render();
		GuiBasicShader.INSTANCE.bind();
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		if (Application.get().getCursorEnabled()) {
			if (state == ButtonState.UP && mouseInBox(Input.getMouseX(), Input.getMouseY())) {
				state = ButtonState.HOVER;
			} else if (state == ButtonState.HOVER && !mouseInBox(Input.getMouseX(), Input.getMouseY())) {
				state = ButtonState.UP;
			} else if (state == ButtonState.DOWN) {
				setValue(value + ((maxVal - minVal) / lineHeight * LayerGUI.getMouseY(event.y)));
			}

		}
		return false;
	}

	private boolean mouseInBox(float x, float y) {
		return LayerGUI.isInComponentRect(x, y, this, -boxSize / 2,
				getHeight() / 2 - lineHeight - boxSize + (value - minVal) / (maxVal - minVal) * lineHeight, boxSize, boxSize);
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		if (Application.get().getCursorEnabled()) {
			if (state == ButtonState.HOVER) {
				state = ButtonState.DOWN;
				return true;
			} else if (state == ButtonState.UP && LayerGUI.isInComponentRect(event.x, event.y, this, -boxSize / 2, getHeight() / 2 - lineHeight - boxSize / 2, boxSize, lineHeight)) {
				float my = LayerGUI.getRelativeMouseY(event.y, this);
				setValue(minVal + maxVal * (my - (getHeight() / 2 - lineHeight - boxSize / 2)) / lineHeight);
				state = ButtonState.DOWN;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		if (Application.get().getCursorEnabled() && state == ButtonState.DOWN) {
			if (mouseInBox(event.x, event.y)) {
				state = ButtonState.HOVER;
			} else {
				state = ButtonState.UP;
			}
			return true;
		}
		return false;
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

	public void setLineHeight(float lineHeight) {
		this.lineHeight = lineHeight;
		updateSize();
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
		updateSize();
	}

	public void setBoxSize(float boxSize) {
		this.boxSize = boxSize;
		updateSize();
	}

	public void setValue(float value) {
		value = MathUtil.clamp(value, minVal, maxVal);
		this.value = value;
		setText(String.format(valueFormat, value));
		if (setter != null)
			setter.set(value);
	}

	public void setValueFormat(String valueFormat) {
		this.valueFormat = valueFormat;
		setText(String.format(valueFormat, value));
	}

	@Override
	public void setTextDimensions(float width, float height) {
		setSize(Math.max(boxSize, width), height + 1 + boxSize + lineHeight);
	}

	private void updateSize() {
		setSize(Math.max(boxSize, getTextWidth()), getTextHeight() + 1 + boxSize + lineHeight);
	}
}
