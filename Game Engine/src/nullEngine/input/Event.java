package nullEngine.input;

public interface Event {

	enum EventType {
		KEY_REPEATED, KEY_PRESSED, KEY_RELEASED, MOUSE_PRESSED, MOUSE_RELEASED, MOUSE_SCROLLED, MOUSE_MOVED, CHAR_TYPED, POST_RESIZE(true), PRE_RESIZE(true), MISC_RENDER(true), MISC_UPDATE;
		private boolean renderThread;

		EventType(boolean renderThread) {
			this.renderThread = renderThread;
		}

		EventType() {
			this(false);
		}

		public boolean isRenderThread() {
			return renderThread;
		}
	}

	EventType getType();
}
