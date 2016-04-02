package nullEngine.input;

public class InputData {

	private static final int MOUSE_BUTTON_COUNT = 8;
	private static final int KEY_COUNT = 512;

	private final boolean[] buttons = new boolean[MOUSE_BUTTON_COUNT];
	private final boolean[] keys = new boolean[KEY_COUNT];
	private int mods = 0;

	private int cursorX = 0;
	private int cursorY = 0;

	public void keyPressed(int key) {
		if (key < keys.length) {
			keys[key] = true;
		}
	}

	public void keyRepeated(int key) {
		if (key < keys.length) {
			keys[key] = true;
		}
	}

	public void keyReleased(int key) {
		if (key < keys.length) {
			keys[key] = false;
		}
	}

	public void mods(int mods) {
		this.mods = mods;
	}

	public int mods() {
		return mods;
	}

	public void setCursorX(int cursorX) {
		this.cursorX = cursorX;
	}

	public void setCursorY(int cursorY) {
		this.cursorY = cursorY;
	}

	public int cursorX(int cursorX) {
		int delta = cursorX - this.cursorX;
		this.cursorX = cursorX;
		return delta;
	}

	public int cursorY(int cursorY) {
		int delta = this.cursorY - cursorY;
		this.cursorY = cursorY;
		return delta;
	}

	public boolean[] getButtons() {
		return buttons;
	}

	public boolean[] getKeys() {
		return keys;
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
