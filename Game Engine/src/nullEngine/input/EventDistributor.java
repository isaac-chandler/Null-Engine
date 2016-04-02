package nullEngine.input;

public class EventDistributor implements EventListener {
	private EventListener listener;

	public EventListener getListener() {
		return listener;
	}

	public void setListener(EventListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean keyRepeated(KeyEvent event) {
		return listener.keyRepeated(event);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		return listener.keyPressed(event);
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		return listener.keyReleased(event);
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		return listener.mousePressed(event);
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		return listener.mouseReleased(event);
	}

	@Override
	public boolean mouseScrolled(MouseEvent event) {
		return listener.mouseScrolled(event);
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		return listener.mouseMoved(event);
	}

	@Override
	public boolean charTyped(CharEvent event) {
		return listener.charTyped(event);
	}
}
