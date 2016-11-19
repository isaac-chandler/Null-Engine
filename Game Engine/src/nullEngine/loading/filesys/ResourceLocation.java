package nullEngine.loading.filesys;

import java.io.InputStream;

/**
 * A location to load resources from
 */
public interface ResourceLocation {

	/**
	 * Attempt to get a resouce in this location
	 *
	 * @param name        The name of the resource
	 * @param requireMark Wether the InputStream requires markSupported()
	 * @return The input stream that is loaded or <code>null</code> if the resouce wasn't found
	 * @see InputStream#markSupported()
	 */
	InputStream getResource(String name, boolean requireMark);

	/**
	 * Clean up after this resource location
	 */
	void close();
}
