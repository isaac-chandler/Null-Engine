package nullEngine.input;

/**
 * An event
 */
public interface Event {

	/**
	 * The type of an event
	 */
	enum EventType {
		/**
		 * Key repeated event, called on update thread
		 */
		KEY_REPEATED,
		/**
		 * Key pressed event, called on update thread
		 */
		KEY_PRESSED,
		/**
		 * Key released event, called on
		 */
		KEY_RELEASED,
		/**
		 * Mouse pressed event, called on update thread
		 */
		MOUSE_PRESSED,
		/**
		 * Mouse released event, called on update thread
		 */
		MOUSE_RELEASED,
		/**
		 * Mouse scrolled event, called on update thread
		 */
		MOUSE_SCROLLED,
		/**
		 * Mouse moved evwent, called on update thread
		 */
		MOUSE_MOVED,
		/**
		 * Character typed event, called on update thread
		 */
		CHAR_TYPED,
		/**
		 * Notification event, called on update thread
		 */
		NOTIFICATION,
		/**
		 * Event after window resize, called on render thread
		 */
		POST_RESIZE(true),
		/**
		 * Event before window resize, called on render thread
		 */
		PRE_RESIZE(true),
		/**
		 * Miscellaneous event, called on render thread
		 */
		MISC_RENDER(true),
		/**
		 * Miscellsneous event, called on update thread
		 */
		MISC_UPDATE;

		private boolean renderThread;

		/**
		 * Create a new EventType
		 *
		 * @param renderThread Wether these events should be called on the RENDER thread
		 */
		EventType(boolean renderThread) {
			this.renderThread = renderThread;
		}

		/**
		 * Create a new EventType on the UPDATE thread
		 */
		EventType() {
			this(false);
		}

		/**
		 * Wether this EventType should be passed on the Render thread
		 *
		 * @return Wether this EventType should be passed on the Render thread
		 */
		public boolean isRenderThread() {
			return renderThread;
		}
	}

	/**
	 * Get the event type
	 *
	 * @return The event type
	 */
	EventType getType();
}
