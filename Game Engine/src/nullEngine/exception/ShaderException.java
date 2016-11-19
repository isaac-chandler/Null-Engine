package nullEngine.exception;

import java.io.PrintStream;

/**
 * Exception thrown if a shader throws an error
 */
public class ShaderException extends RuntimeException {
	public ShaderException(String message) {
		super(message);
	}

	@Override
	public void printStackTrace(PrintStream s) {
		s.println(getMessage());
	}
}
