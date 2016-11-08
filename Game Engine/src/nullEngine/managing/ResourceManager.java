package nullEngine.managing;

import nullEngine.util.logs.Logs;

import java.util.HashMap;
import java.util.Map;

/**
 * A manager for all of the ManagedResources
 * @see ManagedResource
 */
public class ResourceManager {
	private static HashMap<String, ManagedResource> resources = new HashMap<String, ManagedResource>();

	/**
	 * Get a loaded resource by name
	 * @param name The resource's name
	 * @return The resource that was found, <code>null</code> if it doesn't exist
	 */
	public static ManagedResource getResource(String name) {
		return resources.get(name);
	}

	/**
	 * Add a resouce to the manager
	 * @param resource The resource
	 */
	public static void add(ManagedResource resource) {
		String name = resource.fullName;
		resources.put(name, resource);
	}

	/**
	 * Delete a resource and possibly log it to the debug log
	 * @param resource The resouce to delete
	 */
	public static void delete(ManagedResource resource) {
		if (resource.delete())
			Logs.d("Deleted " + resource.fullName);
		resources.remove(resource.fullName);
	}

	/**
	 * Delete all of the resources
	 */
	public static void deleteAll() {
		for (Map.Entry<String, ManagedResource> resource : resources.entrySet()) {
			if (resource.getValue().delete())
				Logs.w("Cleanup: deleted " + resource.getKey());
		}
		resources.clear();
	}
}
