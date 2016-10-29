package nullEngine.input;

public class NotificationEvent implements Event {

	private static volatile int nextNotificationType = 0;

	public static synchronized int newNotificationType() {
		return nextNotificationType++;
	}

	public static final int NOTIFICATION_MOUSE_PICK_COMPLETE = newNotificationType();

	private EventListener destination;

	private int notificationType;
	private Object data;

	public NotificationEvent(EventListener destination, int notificationType, Object data) {
		this.destination = destination;
		this.notificationType = notificationType;
		this.data = data;
	}

	public EventListener getDestination() {
		return destination;
	}

	public int getNotificationType() {
		return notificationType;
	}

	public Object getData() {
		return data;
	}

	@Override
	public EventType getType() {
		return EventType.NOTIFICATION;
	}
}
