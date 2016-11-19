package nullEngine.input;

/**
 * Distributes events to the assigned distributor
 */
public class EventDistributor implements EventListener {
	/**
	 * The event listener
	 */
	public EventListener listener;

	/**
	 * Calls keyRepeated on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean keyRepeated(KeyEvent event) {
		return listener.keyRepeated(event);
	}

	/**
	 * Calls keyPressed on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean keyPressed(KeyEvent event) {
		return listener.keyPressed(event);
	}

	/**
	 * Calls keyReleased on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean keyReleased(KeyEvent event) {
		return listener.keyReleased(event);
	}

	/**
	 * Calls mousePressed on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean mousePressed(MouseEvent event) {
		return listener.mousePressed(event);
	}

	/**
	 * Calls mouseReleased on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean mouseReleased(MouseEvent event) {
		return listener.mouseReleased(event);
	}

	/**
	 * Calls mouseScrolled on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean mouseScrolled(MouseEvent event) {
		return listener.mouseScrolled(event);
	}

	/**
	 * Calls mouseMoved on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean mouseMoved(MouseEvent event) {
		return listener.mouseMoved(event);
	}

	/**
	 * Calls charTyped on the listener
	 *
	 * @param event The event
	 * @return What the listener returns
	 */
	@Override
	public boolean charTyped(CharEvent event) {
		return listener.charTyped(event);
	}

	/**
	 * Calls notified on the events destination
	 *
	 * @param event The event
	 */
	@Override
	public void notified(NotificationEvent event) {
		event.getDestination().notified(event);
	}

	/**
	 * Calls postResize on the listener
	 *
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		listener.postResize(event);
	}

	/**
	 * Calls preResize on the listener
	 */
	@Override
	public void preResize() {
		listener.preResize();
	}
}
