package nullEngine.input;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A class that stores the data for mouse and keyboard input
 */
public class InputData {

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private static final int MOUSE_BUTTON_COUNT = 8;
	private static final int KEY_COUNT = 512;

	private final boolean[] buttons = new boolean[MOUSE_BUTTON_COUNT];
	private final boolean[] keys = new boolean[KEY_COUNT];
	private int mods = 0;

	private int cursorX = 0;
	private int cursorY = 0;

	/**
	 * Set the key state to pressed
	 *
	 * @param key The key that was pressed
	 */
	public void keyPressed(int key) {
		lock.writeLock().lock();
		if (key < keys.length && key > 0)
			keys[key] = true;
		lock.writeLock().unlock();
	}

	/**
	 * Set the key state to repeated
	 *
	 * @param key The key that was repeated
	 */
	public void keyRepeated(int key) {
		lock.writeLock().lock();
		if (key < keys.length && key > 0)
			keys[key] = true;
		lock.writeLock().unlock();
	}

	/**
	 * Set the key state to released
	 *
	 * @param key The key that was released
	 */
	public void keyReleased(int key) {
		lock.writeLock().lock();
		if (key < keys.length && key > 0)
			keys[key] = false;
		lock.writeLock().unlock();
	}

	/**
	 * Set the modifiers
	 *
	 * @param mods The modifiers e.g. ALT, CTRL, SHIFT
	 */
	public void setMods(int mods) {
		lock.writeLock().lock();
		this.mods = mods;
		lock.writeLock().unlock();
	}

	/**
	 * Get the modifiers
	 *
	 * @return The modifiers e.g. ALT, CTRL, SHIFT
	 */
	public int getMods() {
		lock.readLock().lock();
		int ret = mods;
		lock.readLock().unlock();
		return ret;
	}

	/**
	 * Set the cursor x
	 *
	 * @param cursorX The cursor x
	 */
	public void setCursorX(int cursorX) {
		lock.writeLock().lock();
		this.cursorX = cursorX;
		lock.writeLock().unlock();
	}

	/**
	 * Set the cursor y
	 *
	 * @param cursorY The cursor y
	 */
	public void setCursorY(int cursorY) {
		lock.writeLock().lock();
		this.cursorY = cursorY;
		lock.writeLock().unlock();
	}

	/**
	 * Set the cursor x
	 *
	 * @param cursorX the cursor x
	 * @return The movement since the last position
	 */
	public int cursorX(int cursorX) {
		lock.readLock().lock();
		int delta = cursorX - this.cursorX;
		lock.readLock().unlock();
		lock.writeLock().lock();
		this.cursorX = cursorX;
		lock.writeLock().unlock();
		return delta;
	}

	/**
	 * Set the cursor y
	 *
	 * @param cursorY the cursor y
	 * @return The movement since the last position
	 */
	public int cursorY(int cursorY) {
		lock.readLock().lock();
		int delta = this.cursorY - cursorY;
		lock.readLock().unlock();
		lock.writeLock().lock();
		this.cursorY = cursorY;
		lock.writeLock().unlock();
		return delta;
	}

	/**
	 * Get wether a mouse button is pressed
	 *
	 * @param button The button
	 * @return Wether the button is pressed
	 */
	public boolean getButton(int button) {
		boolean ret = false;
		lock.readLock().lock();
		if (button > 0 && button < buttons.length)
			ret = buttons[button];
		lock.readLock().unlock();
		return ret;
	}

	/**
	 * Get wether a key is pressed
	 *
	 * @param key The key
	 * @return Wether the key is pressed
	 */
	public boolean getKey(int key) {
		boolean ret = false;
		lock.readLock().lock();
		if (key > 0 && key < keys.length)
			ret = keys[key];
		lock.readLock().unlock();
		return ret;
	}

	/**
	 * Get the cursor x
	 *
	 * @return The cursor x
	 */
	public int getCursorX() {
		return cursorX;
	}

	/**
	 * Get the cursor y
	 *
	 * @return The cursor y
	 */
	public int getCursorY() {
		return cursorY;
	}

	/**
	 * Set the mouse button state to pressed
	 *
	 * @param button The button that was pressed
	 */
	public void mousePressed(int button) {
		lock.writeLock().lock();
		if (button < buttons.length && button > 0)
			buttons[button] = true;
		lock.writeLock().unlock();
	}

	/**
	 * Set the mouse button state to released
	 *
	 * @param button The button that was released
	 */
	public void mouseReleased(int button) {
		lock.writeLock().lock();
		if (button < buttons.length && button > 0)
			buttons[button] = false;
		lock.writeLock().unlock();
	}
}
