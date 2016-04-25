package nullEngine.loading;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ResourceLoader {

	private static final ArrayList<ResourceLocation> locations = new ArrayList<ResourceLocation>();

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

	public static void addResourceLocation(ResourceLocation location) {
		locations.add(location);
	}

	public static void removeResourceLocation(ResourceLocation location) {
		location.close();
		locations.remove(location);
	}

	public static void init() {
		addResourceLocation(new FileSystemLocation());
		addResourceLocation(new ClasspathLocation());
	}


	public static void close() {
		for (ResourceLocation location : locations)
			location.close();
		locations.clear();
	}
}
