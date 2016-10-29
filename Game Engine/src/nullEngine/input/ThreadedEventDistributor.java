package nullEngine.input;

import nullEngine.util.logs.Logs;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadedEventDistributor extends EventDistributor {
	private volatile LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>();

	public void passEvents() {
		Event event;
		while ((event = eventQueue.poll()) != null) {
			passEvent(event);
		}
	}

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

	@Override
	public boolean keyRepeated(KeyEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean mouseScrolled(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public boolean charTyped(CharEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
		return false;
	}

	@Override
	public void notified(NotificationEvent event) {
		try {
			eventQueue.put(event);
		} catch (InterruptedException e) {
			Logs.e(e);
		}
	}
}
