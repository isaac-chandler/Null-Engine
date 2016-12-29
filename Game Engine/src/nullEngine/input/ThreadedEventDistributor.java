package nullEngine.input;

import nullEngine.util.logs.Logs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Distributes events to the assigned event listener, ensures the events are passed from the correct thread
 */
public class ThreadedEventDistributor extends EventDistributor {
	private volatile BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

	/**
	 * Pass the queued events to the listener
	 * Call from UPDATE thread
	 */
	public void passEvents() {
		Event event;
		while ((event = eventQueue.poll()) != null) {
			passEvent(event);
		}
	}

	/**
	 * Put an event in the event queue
	 *
	 * @param event The event
	 */
	public void queueEvent(Event event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
	}

	private void passEvent(Event event) {

		if (event.getType().isRenderThread())
			Logs.w("Received render thread event in update thread");

		switch (event.getType()) {
			case KEY_REPEATED:
				listener.keyRepeated((KeyEvent) event);
				break;
			case KEY_PRESSED:
				listener.keyPressed((KeyEvent) event);
				break;
			case KEY_RELEASED:
				listener.keyReleased((KeyEvent) event);
				break;
			case MOUSE_PRESSED:
				listener.mousePressed((MouseEvent) event);
				break;
			case MOUSE_RELEASED:
				listener.mouseReleased((MouseEvent) event);
				break;
			case MOUSE_SCROLLED:
				listener.mouseScrolled((MouseEvent) event);
				break;
			case MOUSE_MOVED:
				listener.mouseMoved((MouseEvent) event);
				break;
			case CHAR_TYPED:
				listener.charTyped((CharEvent) event);
				break;
			case POST_RESIZE:
				listener.postResize((PostResizeEvent) event);
				break;
			case PRE_RESIZE:
				listener.preResize();
				break;
			case NOTIFICATION:
				((NotificationEvent) event).getDestination().notified((NotificationEvent) event);
				break;
			default:
				Logs.w("Received event with unknown type");
				break;
		}
	}

	/**
	 * Adds a key repeated event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean keyRepeated(KeyEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a key pressed event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean keyPressed(KeyEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a key released event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean keyReleased(KeyEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a mouse pressed event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mousePressed(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a mouse released event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mouseReleased(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a mouse scrolled event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mouseScrolled(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a mouse moved event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean mouseMoved(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a character typed event to the queue
	 *
	 * @param event The event
	 * @return <code>false</code>
	 */
	@Override
	public boolean charTyped(CharEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	/**
	 * Adds a notified event to the queue
	 *
	 * @param event The event
	 */
	@Override
	public void notified(NotificationEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
	}


}
