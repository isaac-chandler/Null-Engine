package nullEngine.input;

public class CharEvent implements Event {
	public char character;
	public int mods;

	@Override
	public EventType getType() {
		return EventType.CHAR_TYPED;
	}
}
