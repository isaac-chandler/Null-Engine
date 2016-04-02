package nullEngine.loading;

import nullEngine.util.StreamUtils;
import nullEngine.util.logs.Logs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/*

*/

public class ResourceBundleLocation implements ResourceLocation {

	private InputStream is;
	private HashMap<String, Integer> fileOffsets;

	public ResourceBundleLocation(String name) {
		try {
			is = ResourceLoader.getFileResource("res/bundles/" + name + ".res", true);

			int version = is.read();
			if (version != 1)
				throw new IOException("Invalid bundle file");

			int fileCount = StreamUtils.readInt(is);

			fileOffsets = new HashMap<String, Integer>(fileCount);

			for (int i = 0; i < fileCount; i++) {
				String file = StreamUtils.readString(is);
				fileOffsets.put(file, StreamUtils.readInt(is));
			}
		} catch (IOException e) {
			Logs.f(e);
		}
	}

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
			} catch (IOException e) {}
		}
		return null;
	}

	@Override
	public void close() {
		try {
			is.close();
		} catch (IOException e) {
			Logs.e(e);
		}
	}
}
