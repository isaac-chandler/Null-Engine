package nullEngine.loading.filesys;

import java.io.IOException;

/**
 * The exception thrown if a file format is incorrect
 */
public class FileFormatException extends IOException {

	/**
	 * Create a new FileFormatException
	 */
	public FileFormatException() {
		super();
	}

	/**
	 * Create a new FileFormatException
	 *
	 * @param message The message
	 */
	public FileFormatException(String message) {
		super(message);
	}

	/**
	 * Create a new FileFormatException
	 *
	 * @param message The message
	 * @param cause   The cause
	 */
	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create a new FileFormatException
	 *
	 * @param cause The cause
	 */
	public FileFormatException(Throwable cause) {
		super(cause);
	}
}
