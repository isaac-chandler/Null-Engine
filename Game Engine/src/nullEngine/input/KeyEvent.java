package nullEngine.input;

/**
 * An event to do with the keyboard
 */
public class KeyEvent implements Event {
	/**
	 * The key that the event was caused by
	 */
	public int key;
	/**
	 * The modifiers e.g. ALT, CTRL, SHIFT
	 */
	public int mods;

	private EventType type;

	/**
	 * Create a key event
	 *
	 * @param type The event type
	 */
	public KeyEvent(EventType type) {
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
