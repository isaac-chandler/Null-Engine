package nullEngine.exception;

/**
 * Exception thrown if there was a failure during initialization
 */
public class InitializationException extends Exception {
	public InitializationException() {
		super();
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

	protected InitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
