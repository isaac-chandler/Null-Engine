package nullEngine.loading.filesys;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * A location that loads files from the classpath
 */
public class ClasspathLocation implements ResourceLocation {

	private final ClassLoader loader = getClass().getClassLoader();

	/**
	 * Attempt to get a resouce in the classpath
	 *
	 * @param name        The name of the resource
	 * @param requireMark Wether the InputStream requires markSupported()
	 * @return The input stream that is loaded or <code>null</code> if the resouce wasn't found
	 * @see InputStream#markSupported()
	 */
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		InputStream is = loader.getResourceAsStream(name);

		if (is == null)
			return null;

		if (requireMark && !is.markSupported()) {
			return new BufferedInputStream(is);
		} else {
			return is;
		}
	}

	/**
	 * Does nothing
	 */
	@Override
	public void close() {

	}
}
