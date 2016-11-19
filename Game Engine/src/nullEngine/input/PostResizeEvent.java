package nullEngine.input;

/**
 * Event after the window has been resized
 */
public class PostResizeEvent implements Event {
	/**
	 * THe new width
	 */
	public int width;
	/**
	 * The new height
	 */
	public int height;

	/**
	 * Get the event type
	 *
	 * @return POST_RESIZE
	 */
	@Override
	public EventType getType() {
		return EventType.POST_RESIZE;
	}
}
