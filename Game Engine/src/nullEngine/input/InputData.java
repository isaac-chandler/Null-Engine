package nullEngine.input;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InputData {

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private static final int MOUSE_BUTTON_COUNT = 8;
	private static final int KEY_COUNT = 512;

	private final boolean[] buttons = new boolean[MOUSE_BUTTON_COUNT];
	private final boolean[] keys = new boolean[KEY_COUNT];
	private int mods = 0;

	private int cursorX = 0;
	private int cursorY = 0;

	public void keyPressed(int key) {
		lock.writeLock().lock();
		if (key < keys.length) {
			keys[key] = true;
		}
		lock.writeLock().unlock();
	}

	public void keyRepeated(int key) {
		lock.writeLock().lock();
		if (key < keys.length) {
			keys[key] = true;
		}
		lock.writeLock().unlock();
	}

	public void keyReleased(int key) {
		lock.writeLock().lock();
		if (key < keys.length) {
			keys[key] = false;
		}
		lock.writeLock().unlock();
	}

	public void mods(int mods) {
		lock.writeLock().lock();
		this.mods = mods;
		lock.writeLock().unlock();
	}

	public int mods() {
		lock.readLock().lock();
		int ret = mods;
		lock.readLock().unlock();
		return ret;
	}

	public void setCursorX(int cursorX) {
		lock.writeLock().lock();
		this.cursorX = cursorX;
		lock.writeLock().unlock();
	}

	public void setCursorY(int cursorY) {
		lock.writeLock().lock();
		this.cursorY = cursorY;
		lock.writeLock().unlock();
	}

	public int cursorX(int cursorX) {
		lock.readLock().lock();
		int delta = cursorX - this.cursorX;
		lock.readLock().unlock();
		lock.writeLock().lock();
		this.cursorX = cursorX;
		lock.writeLock().unlock();
		return delta;
	}

	public int cursorY(int cursorY) {
		lock.readLock().lock();
		int delta = this.cursorY - cursorY;
		lock.readLock().unlock();
		lock.writeLock().lock();
		this.cursorY = cursorY;
		lock.writeLock().unlock();
		return delta;
	}

	public boolean getButton(int button) {
		boolean ret = false;
		lock.readLock().lock();
		if (button > 0 && button < buttons.length) {
			ret = buttons[button];
		}
		lock.readLock().unlock();
		return ret;
	}

	public boolean getKey(int key) {
		boolean ret = false;
		lock.readLock().lock();
		if (key > 0 && key < keys.length) {
			ret = keys[key];
		}
		lock.readLock().unlock();
		return ret;
	}

	public int getCursorX() {
		return cursorX;
	}

	public int getCursorY() {
		return cursorY;
	}

	public void mousePressed(int button) {
		if (button < buttons.length)
			buttons[button] = true;
	}

	public void mouseReleased(int button) {
		if (button < buttons.length)
			buttons[button] = false;
	}
}
