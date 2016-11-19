package nullEngine.loading.filesys;

import nullEngine.util.logs.Logs;
import util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Loads resources from a bundle
 */
public class ResourceBundleLocation implements ResourceLocation {

	private InputStream is;
	private HashMap<String, Integer> fileOffsets;

	/**
	 * Create a new ReourceBundleLocation
	 *
	 * @param name The name of the resouce bundle without the <em>.res</em> extension in the <em>res/bundles</em> folder
	 */
	public ResourceBundleLocation(String name) {
		try {
			is = ResourceLoader.getResource("res/bundles/" + name + ".res", true);

			is.mark(is.available());

			int version = is.read();
			if (version != 1)
				throw new IOException("Unsupported bundle file version");

			int fileCount = StreamUtils.readInt(is);

			fileOffsets = new HashMap<>(fileCount);

			for (int i = 0; i < fileCount; i++) {
				String file = StreamUtils.readString(is);
				fileOffsets.put(file, StreamUtils.readInt(is));
			}
		} catch (IOException e) {
			Logs.f(e);
		}
	}

	/**
	 * Attempt to get a resouce in the resource bundle
	 *
	 * @param name        The name of the resource
	 * @param requireMark Wether the InputStream requires markSupported()
	 * @return The input stream that is loaded or <code>null</code> if the resouce wasn't found
	 * @see InputStream#markSupported()
	 */
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		if (fileOffsets.containsKey(name)) {
			try {
				is.reset();
				is.skip(fileOffsets.get(name));
				int len = StreamUtils.readInt(is);
				byte[] buf = new byte[len];

				is.read(buf);

				return new ByteArrayInputStream(buf);
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * Clean up after this resource location
	 */
	@Override
	public void close() {
		try {
			is.close();
		} catch (IOException e) {
			Logs.e(e);
		}
	}
}
