package nullEngine.object.component.graphics.gui;

public class ComponentAnchor extends Anchor {

	private GuiComponent component;
	private AnchorPos anchorPos;

	private ComponentAnchor(GuiComponent component, AnchorPos anchorPos) {
		this.component = component;
		this.anchorPos = anchorPos;
	}

	public static ComponentAnchor topLeft(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.TOP_LEFT);
	}

	public static ComponentAnchor top(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.TOP);
	}

	public static ComponentAnchor topRight(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.TOP_RIGHT);
	}

	public static ComponentAnchor left(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.LEFT);
	}

	public static ComponentAnchor center(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.CENTER);
	}

	public static ComponentAnchor right(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.RIGHT);
	}

	public static ComponentAnchor bottomLeft(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.BOTTOM_LEFT);
	}

	public static ComponentAnchor bottom(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.BOTTOM);
	}

	public static ComponentAnchor bottomRight(GuiComponent component) {
		return new ComponentAnchor(component, AnchorPos.BOTTOM_RIGHT);
	}

	@Override
	public boolean isValid() {
		return super.isValid() && component.getAnchor().isValid();
	}

	@Override
	public float calcX() {
		return calcXImpl() - component.getWidth() * component.getWidthXMul();
	}

	private float calcXImpl() {
		switch (anchorPos) {
			case TOP_LEFT:
			case LEFT:
			case BOTTOM_LEFT:
				return component.getX() - component.getWidth() / 2;
			case TOP:
			case CENTER:
			case BOTTOM:
				return component.getX();
			case TOP_RIGHT:
			case RIGHT:
			case BOTTOM_RIGHT:
				return component.getX() + component.getWidth() / 2;
			default:
				return component.getX();
		}
	}


	@Override
	public float calcY() {
		return calcYImpl() - component.getHeight() * component.getHeightYMul();
	}

	private float calcYImpl() {
		switch (anchorPos) {
			case TOP_LEFT:
			case TOP:
			case TOP_RIGHT:
				return component.getY() + component.getHeight() / 2;
			case LEFT:
			case CENTER:
			case RIGHT:
				return component.getY();
			case BOTTOM_LEFT:
			case BOTTOM:
			case BOTTOM_RIGHT:
				return component.getY() - component.getHeight() / 2;
			default:
				return component.getY();
		}
	}
}
