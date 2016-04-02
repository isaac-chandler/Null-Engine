package nullEngine.loading;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceLoader {

	private static final ArrayList<ResourceLocation> locations = new ArrayList<ResourceLocation>();
	private static FileSystemLocation fsLocation = new FileSystemLocation();
	private volatile static InputStream multithread;
	private static final ReentrantLock lock = new ReentrantLock();

	public static InputStream getResource(final String name, final boolean requireMark) throws FileNotFoundException {
		InputStream is;
		for (ResourceLocation location : locations)
			if ((is = location.getResource(name, requireMark)) != null)
				return is;

		throw new FileNotFoundException(name);
	}

	public static InputStream getResource(String name) throws FileNotFoundException {
		return getResource(name, false);
	}

	public static InputStream getFileResource(String name, boolean requireMark) throws FileNotFoundException {
		InputStream is = fsLocation.getResource(name, requireMark);
		if (is == null)
			throw new FileNotFoundException(name);

		return is;
	}

	public static InputStream getFileResource(String name) throws FileNotFoundException {
		return getFileResource(name, false);
	}

	public static void addResourceLocation(ResourceLocation location) {
		locations.add(location);
	}

	public static void removeResourceLocation(ResourceLocation location) {
		location.close();
		locations.remove(location);
	}

	public static void init() {
		addResourceLocation(fsLocation = new FileSystemLocation());
		addResourceLocation(new ClasspathLocation());
	}


	public static void close() {
		for (ResourceLocation location : locations)
			location.close();
		locations.clear();
	}
}
