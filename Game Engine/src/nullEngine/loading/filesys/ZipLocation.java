package nullEngine.loading.filesys;

import nullEngine.util.logs.Logs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipLocation implements ResourceLocation {

	private ZipFile file;

	public ZipLocation(File file) throws IOException {
		this.file = new ZipFile(file);
	}

	/**
	 * Attempt to get a resouce in this location
	 *
	 * @param name        The name of the resource
	 * @param requireMark Wether the InputStream requires markSupported()
	 * @return The input stream that is loaded or <code>null</code> if the resouce wasn't found
	 * @see InputStream#markSupported()
	 */
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		ZipEntry entry = file.getEntry(name);
		if (entry != null)
			try {
				InputStream input = file.getInputStream(entry);
				if (!input.markSupported() && requireMark)
					return new BufferedInputStream(input);
				else
					return input;
			} catch (IOException e) {
				return null;
			}

		return null;
	}

	/**
	 * Clean up after this resource location
	 */
	@Override
	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			Logs.e(e);
		}
	}
}
