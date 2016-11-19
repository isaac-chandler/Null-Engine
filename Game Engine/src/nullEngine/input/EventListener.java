package nullEngine.input;

/**
 * Listener for events
 */
public interface EventListener {
	/**
	 * Called when a key is repeated
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean keyRepeated(KeyEvent event);

	/**
	 * Called when a key is pressed
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean keyPressed(KeyEvent event);

	/**
	 * Called when a key is released
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean keyReleased(KeyEvent event);

	/**
	 * Called when a mouse button is pressed
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean mousePressed(MouseEvent event);

	/**
	 * Called when a mouse button is released
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean mouseReleased(MouseEvent event);

	/**
	 * Called when the mouse wheel is scrolled
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean mouseScrolled(MouseEvent event);

	/**
	 * Called when the mouse is moved
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean mouseMoved(MouseEvent event);

	/**
	 * Called when a character is typed
	 *
	 * @param event The event
	 * @return <code>true</code> if the event should not be passed to any further listeners
	 */
	boolean charTyped(CharEvent event);

	/**
	 * Called when this listener is notified
	 *
	 * @param event The event
	 */
	void notified(NotificationEvent event);

	/**
	 * Called after the window resizes
	 *
	 * @param event The event
	 */
	void postResize(PostResizeEvent event);

	/**
	 * Called before the window resizes
	 */
	void preResize();
}
