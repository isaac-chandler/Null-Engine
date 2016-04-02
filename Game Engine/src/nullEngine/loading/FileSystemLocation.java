package nullEngine.loading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileSystemLocation implements ResourceLocation {
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		try {
			if (requireMark)
				return new RandomAccessFileInputStream(name);
			else
				return new FileInputStream(name);
		} catch (FileNotFoundException e) {}
		return null;
	}

	@Override
	public void close() {

	}
}
