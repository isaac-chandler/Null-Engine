package nullEngine.loading;

import nullEngine.util.logs.Logs;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathLocation implements ResourceLocation {
	@Override
	public InputStream getResource(String name, boolean requireMark) {
		InputStream is = ResourceLoader.class.getClassLoader().getResourceAsStream(name);

		if (is == null)
			return null;

		if (!is.markSupported() && requireMark) {
			try {
				is.close();
			} catch (IOException e) {
				Logs.e(e);
			}
			return null;
		}
		return is;
	}

	@Override
	public void close() {

	}
}
