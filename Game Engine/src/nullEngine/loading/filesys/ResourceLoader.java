package nullEngine.loading.filesys;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A class that manages resource loading
 */
public class ResourceLoader {

	private static final ArrayList<ResourceLocation> locations = new ArrayList<>();

	/**
	 * Load a resource from the registered resource locations
	 *
	 * @param name        The name of the resouce to load
	 * @param requireMark Does the resouce require mark/reset
	 * @return The reource that was loaded
	 * @throws FileNotFoundException If the resource wasn't found or mark/reset was requested but wasn't supported in any ResourceLocation
	 */
	public static InputStream getResource(final String name, final boolean requireMark) throws FileNotFoundException {
		InputStream is;
		for (ResourceLocation location : locations)
			if ((is = location.getResource(name, requireMark)) != null)
				return is;

		throw new FileNotFoundException(name);
	}

	/**
	 * Load a resource from the registered resource locations
	 *
	 * @param name The name of the resouce to load
	 * @return The reource that was loaded
	 * @throws FileNotFoundException If the resource wasn't found in any ResourceLocation
	 */
	public static InputStream getResource(String name) throws FileNotFoundException {
		return getResource(name, false);
	}

	/**
	 * Add a resource location
	 *
	 * @param location The location to add
	 */
	public static void addResourceLocation(ResourceLocation location) {
		locations.add(location);
	}

	/**
	 * Remove a resource location
	 *
	 * @param location The location to remove
	 * @return <code>true</code> if the location was in the location list
	 */
	public static boolean removeResourceLocation(ResourceLocation location) {
		if (locations.remove(location)) {
			location.close();
			return true;
		}
		return false;
	}

	/**
	 * Set up the default resource locations
	 */
	public static void init() {
		addResourceLocation(new FileSystemLocation());
		addResourceLocation(new ClasspathLocation());
	}

	/**
	 * Close and remove all of the resource locations
	 */
	public static void close() {
		locations.forEach(ResourceLocation::close);
		locations.clear();
	}
}
