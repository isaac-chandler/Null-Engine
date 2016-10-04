package nullEngine.loading.filesys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemLocation implements ResourceLocation {
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		try {
			if (requireMark) {
				return new FileChannelInputStream(name);
			} else {
				return new FileInputStream(name);
			}
		} catch (IOException e) {}
		return null;
	}

	@Override
	public void close() {

	}
}
