package nullEngine.input;

/**
 * Event that is sent directly to a certain listener
 */
public class NotificationEvent implements Event {

	private static volatile int nextNotificationType = 0;

	/**
	 * Get a new notification type id
	 *
	 * @return The new type id
	 */
	public static synchronized int newNotificationType() {
		return ++nextNotificationType;
	}

	/**
	 * The notification type id for when a mouse picking request is complete
	 */
	public static final int NOTIFICATION_MOUSE_PICK_COMPLETE = newNotificationType();

	private EventListener destination;

	private int notificationType;
	private Object data;

	/**
	 * Create a new notification event
	 *
	 * @param destination      The listener that should receive the event
	 * @param notificationType The notification type id
	 * @param data             The notification data
	 */
	public NotificationEvent(EventListener destination, int notificationType, Object data) {
		this.destination = destination;
		this.notificationType = notificationType;
		this.data = data;
	}

	/**
	 * Get the listener that should receive the event
	 *
	 * @return The listener
	 */
	public EventListener getDestination() {
		return destination;
	}

	/**
	 * Get the notification type id
	 *
	 * @return The notification type id
	 */
	public int getNotificationType() {
		return notificationType;
	}

	/**
	 * Get the notification data
	 *
	 * @return The data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Get the event type
	 *
	 * @return NOTIFICATION
	 */
	@Override
	public EventType getType() {
		return EventType.NOTIFICATION;
	}
}
