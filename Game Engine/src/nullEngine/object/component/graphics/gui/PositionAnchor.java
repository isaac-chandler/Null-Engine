package nullEngine.object.component.graphics.gui;

public class PositionAnchor extends Anchor {

	private float x, y;

	public PositionAnchor(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void invalidate() {}

	@Override
	protected float calcX() {
		return x;
	}

	@Override
	protected float calcY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
		super.invalidate();
	}

	public void setY(float y) {
		this.y = y;
		super.invalidate();
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
		super.invalidate();
	}
}
