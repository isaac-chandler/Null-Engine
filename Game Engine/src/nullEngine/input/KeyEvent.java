package nullEngine.input;

public class KeyEvent implements Event {
	public int key, mods;
	private EventType type;

	public KeyEvent(EventType type) {
		this.type = type;
	}

	@Override
	public EventType getType() {
		return type;
	}


}
