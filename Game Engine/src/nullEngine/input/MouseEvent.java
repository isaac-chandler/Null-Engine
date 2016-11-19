package nullEngine.input;

/**
 * An event to do with the mouse
 */
public class MouseEvent implements Event {
	/**
	 * The mouse button that the event was caused by
	 */
	public int button;
	/**
	 * The x coordinate
	 */
	public int x;
	/**
	 * The y coordinate
	 */
	public int y;
	/**
	 * The modifiers e.g. ALT, CTRL, SHIFT
	 */
	public int mods;
	private EventType type;

	/**
	 * Create a mouse event
	 *
	 * @param type The event type
	 */
	public MouseEvent(EventType type) {
		this.type = type;
	}

	/**
	 * Get the event type
	 *
	 * @return The event type
	 */
	@Override
	public EventType getType() {
		return type;
	}
}
