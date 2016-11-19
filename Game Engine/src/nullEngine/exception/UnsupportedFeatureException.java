package nullEngine.exception;

import java.io.IOException;

/**
 * Excpetion thrown if an unsupported feature is used
 */
public class UnsupportedFeatureException extends IOException {
	public UnsupportedFeatureException() {
		super();
	}

	public UnsupportedFeatureException(String message) {
		super(message);
	}

	public UnsupportedFeatureException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedFeatureException(Throwable cause) {
		super(cause);
	}
}
