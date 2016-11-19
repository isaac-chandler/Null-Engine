package nullEngine.input;

/**
 * An EventListener where not all of the methods have to be implemented
 */
public class EventAdapter implements EventListener {
	/**
	 * Called when a key is repeated
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean keyRepeated(KeyEvent event) {
		return false;
	}

	/**
	 * Called when a key is repeated
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean keyPressed(KeyEvent event) {
		return false;
	}

	/**
	 * Called when a key is released
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean keyReleased(KeyEvent event) {
		return false;
	}

	/**
	 * Called when a mouse button is pressed
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mousePressed(MouseEvent event) {
		return false;
	}

	/**
	 * Called when a mouse button is released
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mouseReleased(MouseEvent event) {
		return false;
	}

	/**
	 * Called when the mouse wheel is scrolled
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mouseScrolled(MouseEvent event) {
		return false;
	}

	/**
	 * Called when the mouse is moved
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mouseMoved(MouseEvent event) {
		return false;
	}

	/**
	 * Called when a character is typed
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean charTyped(CharEvent event) {
		return false;
	}

	/**
	 * Called when this adapter is notified
	 *
	 * @param event The event
	 */
	@Override
	public void notified(NotificationEvent event) {

	}

	/**
	 * Called after the window is resized
	 *
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {

	}

	/**
	 * Called before the window is resized
	 */
	@Override
	public void preResize() {

	}
}
