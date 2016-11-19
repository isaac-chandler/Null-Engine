package nullEngine.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Passes events to all of its listeners
 */
public class EventHandler implements EventListener {

	private List<EventListener> listeners = new ArrayList<>();

	/**
	 * Add an event listener
	 *
	 * @param listener The listener
	 */
	public void addListener(EventListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove an event listener
	 *
	 * @param listener The listener
	 */
	public void removeListener(EventListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Call keyRepeated on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean keyRepeated(KeyEvent event) {
		for (EventListener listener : listeners)
			if (listener.keyRepeated(event))
				return true;
		return false;
	}

	/**
	 * Call keyPressed on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean keyPressed(KeyEvent event) {
		for (EventListener listener : listeners)
			if (listener.keyPressed(event))
				return true;
		return false;
	}

	/**
	 * Call keyReleased on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean keyReleased(KeyEvent event) {
		for (EventListener listener : listeners)
			if (listener.keyReleased(event))
				return true;
		return false;
	}

	/**
	 * Call mousePressed on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean mousePressed(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mousePressed(event))
				return true;
		return false;
	}

	/**
	 * Call mouseReleased on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean mouseReleased(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mouseReleased(event))
				return true;
		return false;
	}

	/**
	 * Call mouseScrolled on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean mouseScrolled(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mouseScrolled(event))
				return true;
		return false;
	}

	/**
	 * Call mouseMoved on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	public boolean mouseMoved(MouseEvent event) {
		for (EventListener listener : listeners)
			if (listener.mouseMoved(event))
				return true;
		return false;
	}

	/**
	 * Call charTyped on the listeners until one returns <code>true</code>
	 *
	 * @param event The event
	 * @return <code>true</code> if one of the listeners returned <code>true</code>, otherwise <code>false</code>
	 */
	@Override
	public boolean charTyped(CharEvent event) {
		for (EventListener listener : listeners)
			if (listener.charTyped(event))
				return true;
		return false;
	}

	/**
	 * Does nothing
	 *
	 * @param event The event
	 */
	@Override
	public void notified(NotificationEvent event) {

	}

	/**
	 * Call postResize on the listeners
	 *
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
		for (EventListener listener : listeners)
			listener.postResize(event);
	}

	/**
	 * Call preResize on the listeners
	 */
	@Override
	public void preResize() {
		for (EventListener listener : listeners)
			listener.preResize();
	}
}
