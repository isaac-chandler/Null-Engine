package nullEngine.input;

public class EventAdapter implements EventListener {
	@Override
	public boolean keyRepeated(KeyEvent event) {
		return false;
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		return false;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		return false;
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		return false;
	}

	@Override
	public boolean mouseScrolled(MouseEvent event) {
		return false;
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		return false;
	}

	@Override
	public boolean charTyped(CharEvent event) {
		return false;
	}

	@Override
	public void postResize(ResizeEvent event) {

	}

	@Override
	public void preResize() {

	}
}
