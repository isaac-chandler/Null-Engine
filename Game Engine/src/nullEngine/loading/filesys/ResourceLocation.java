package nullEngine.loading.filesys;

import java.io.InputStream;

public interface ResourceLocation {
	InputStream getResource(String name, boolean requireMark);

	void close();
}
