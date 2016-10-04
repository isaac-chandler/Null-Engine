package nullEngine.exception;

import java.io.IOException;

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
