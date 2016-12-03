package nullEngine.object.component.gui;

public abstract class Anchor {
	private boolean valid = false;
	private float x;
	private float y;

	public void invalidate() {
		valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public float getX() {
		if (!isValid()) {
			valid = true;
			x = calcX();
			y = calcY();
		}

		return x;
	}

	public float getY() {
		if (!isValid()) {
			valid = true;
			x = calcX();
			y = calcY();
		}

		return y;
	}

	protected abstract float calcX();

	protected abstract float calcY();
}
