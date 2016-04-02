package nullEngine.input;

import java.util.ArrayList;

public class EventHandler implements EventListener {

	ArrayList<EventListener> listeners = new ArrayList<EventListener>();

	public void addListener(EventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(EventListener listener) {
		listeners.remove(listener);
	}

	public boolean keyRepeated(KeyEvent event) {
		for (EventListener listener : listeners)
			if (listener.keyRepeated(event))
				return true;
		return false;
	}

	public boolean keyPressed(KeyEvent event) {
		for (EventListener listener : listeners)
			if (listener.keyPressed(event))
				return true;
		return false;
	}

	public boolean keyReleased(KeyEvent event) {
		for (EventListener listener : listeners)
			if (listener.keyReleased(event))
				return true;
		return false;
	}

	public boolean mousePressed(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mousePressed(event))
				return true;
		return false;
	}

	public boolean mouseReleased(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mouseReleased(event))
				return true;
		return false;
	}

	public boolean mouseScrolled(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mouseScrolled(event))
				return true;
		return false;
	}

	public boolean mouseMoved(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mouseMoved(event))
				return true;
		return false;
	}

	@Override
	public boolean charTyped(CharEvent event) {
		for (EventListener listener : listeners)
			if (listener.charTyped(event))
				return true;
		return false;
	}
}
