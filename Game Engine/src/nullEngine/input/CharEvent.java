package nullEngine.input;

/**
 * Event when a character has been typed
 */
public class CharEvent implements Event {
	/**
	 * The character
	 */
	public char character;
	/**
	 * The modifiers e.g. ALT, CTRL, SHIFT
	 */
	public int mods;

	/**
	 * Get the event type
	 *
	 * @return CHAR_TYPED
	 */
	@Override
	public EventType getType() {
		return EventType.CHAR_TYPED;
	}
}
