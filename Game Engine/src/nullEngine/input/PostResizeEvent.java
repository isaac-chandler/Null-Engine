package nullEngine.input;

public class PostResizeEvent implements Event {
	public int width;
	public int height;

	@Override
	public EventType getType() {
		return EventType.POST_RESIZE;
	}
}
