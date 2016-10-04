package nullEngine.loading.filesys;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class ClasspathLocation implements ResourceLocation {
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		InputStream is = ResourceLoader.class.getClassLoader().getResourceAsStream(name);

		if (is == null)
			return null;

		if (requireMark && !is.markSupported()) {
			return new BufferedInputStream(is);
		} else {
			return is;
		}
	}

	@Override
	public void close() {

	}
}
