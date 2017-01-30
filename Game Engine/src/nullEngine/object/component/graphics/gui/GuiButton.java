package nullEngine.object.component.graphics.gui;

import com.sun.istack.internal.NotNull;
import nullEngine.control.Application;
import nullEngine.control.layer.LayerGUI;
import nullEngine.graphics.Color;
import nullEngine.graphics.model.Quad;
import nullEngine.graphics.shader.gui.GuiBasicShader;
import nullEngine.graphics.shader.gui.GuiQuadShader;
import nullEngine.input.Input;
import nullEngine.input.MouseEvent;
import nullEngine.object.GameObject;

public class GuiButton extends GuiComponent {

	private final GuiResizeListener resizeListener = this::updateSize;

	private GuiComponent content;
	private ButtonState state = ButtonState.UP;
	private GuiAction action;

	private float verticalMargin = 1;
	private float horizontalMargin = 1;

	public GuiButton(@NotNull Anchor anchor, AnchorPos anchorPos, GuiAction action, GuiComponent content) {
		super(anchor, anchorPos);
		this.action = action;
		setContent(content);
	}

	@Override
	public void render(GameObject object) {
		render(object, state);
	}

	protected void render(GameObject object, ButtonState state) {
		GuiQuadShader.INSTANCE.bind();
		GuiQuadShader.INSTANCE.loadMVP(getRenderer().getMVP());
		GuiQuadShader.INSTANCE.loadPosition(getX(), getY());
		GuiQuadShader.INSTANCE.loadSize(getWidth() / 2, getHeight() / 2);

		switch (state) {
			case HOVER:
				GuiQuadShader.INSTANCE.loadColor(Color.BLACK);
				break;
			case DOWN:
				GuiQuadShader.INSTANCE.loadColor(Color.BLUE);
				break;
			case UP:
			default:
				GuiQuadShader.INSTANCE.loadColor(Color.WHITE);
		}

		Quad.get().render();

		GuiBasicShader.INSTANCE.bind();
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		if (Application.get().getCursorEnabled()) {
			if (LayerGUI.isInComponent(Input.getMouseX(), Input.getMouseY(), this))
				state = ButtonState.HOVER;
			else
				state = ButtonState.UP;
		}
		return false;
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		if (Application.get().getCursorEnabled() && state == ButtonState.HOVER) {
			state = ButtonState.DOWN;
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		if (Application.get().getCursorEnabled() && state == ButtonState.DOWN) {
			state = ButtonState.HOVER;
			if (action != null)
				action.action(this);
			return true;
		}
		return false;
	}

	/**
	 * Update this component
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(GameObject object, double delta) {

	}

	public GuiComponent getContent() {
		return content;
	}

	public void setContent(GuiComponent content) {
		if (this.content != null) {
			this.content.removeResizeListener(resizeListener);
		}
		this.content = content;
		if (content != null) {
			content.addResizeListener(resizeListener);
			content.setAnchorPos(AnchorPos.CENTER);
			content.setAnchor(CENTER);
			updateSize();
		}
	}

	public void setAction(GuiAction action) {
		this.action = action;
	}

	public void setVerticalMargin(float verticalMargin) {
		this.verticalMargin = verticalMargin;
		updateSize();
	}

	public void setHorizontalMargin(float horizontalMargin) {
		this.horizontalMargin = horizontalMargin;
		updateSize();
	}

	private void updateSize(float width, float height) {
		setSize(width + horizontalMargin * 2, height + verticalMargin * 2);
	}

	private void updateSize() {
		if (content != null) {
			setSize(content.getWidth() + horizontalMargin * 2, content.getHeight() + verticalMargin * 2);
		}
	}
}
