package nullEngine.managing;

import nullEngine.util.logs.Logs;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	private static HashMap<String, ManagedResource> resources = new HashMap<String, ManagedResource>();

	public static ManagedResource getResource(String name) {
		return resources.get(name);
	}

	public static void add(ManagedResource resource) {
		String name = resource.getType() + ":" + resource.getName();
		resources.put(name, resource);
	}

	public static void delete(ManagedResource resource) {
		resource.getDeleter().deleteResource(resource);
		Logs.d("Deleting " + resource.getType() + ":" + resource.getName());
		resources.remove(resource.getType() + ":" + resource.getName());
	}

	public static void deleteAll() {
		for (Map.Entry<String, ManagedResource> resource : resources.entrySet()) {
			Logs.d("Cleanup: deleting " + resource.getKey());
			resource.getValue().getDeleter().deleteResource(resource.getValue());
		}
		resources.clear();
	}
}
