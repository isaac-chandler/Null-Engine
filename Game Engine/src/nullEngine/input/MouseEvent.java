package nullEngine.input;

public class MouseEvent implements Event {
	public int button;
	public int x;
	public int y;
	public int mods;
	private EventType type;

	public MouseEvent(EventType type) {
		this.type = type;
	}

	@Override
	public EventType getType() {
		return type;
	}
}
