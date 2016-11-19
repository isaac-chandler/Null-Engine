package nullEngine.loading.filesys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemLocation implements ResourceLocation {

	/**
	 * Attempt to get a resouce in the file system
	 *
	 * @param name        The name of the resource
	 * @param requireMark Wether the InputStream requires markSupported()
	 * @return The input stream that is loaded or <code>null</code> if the resouce wasn't found
	 * @see InputStream#markSupported()
	 */
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		try {
			if (requireMark) {
				return new FileChannelInputStream(name);
			} else {
				return new FileInputStream(name);
			}
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Does nothing
	 */
	@Override
	public void close() {

	}
}
