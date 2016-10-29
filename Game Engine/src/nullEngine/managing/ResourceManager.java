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
		String name = resource.fullName;
		resources.put(name, resource);
	}

	public static void delete(ManagedResource resource) {
		if (resource.delete())
			Logs.d("Deleted " + resource.fullName);
		resources.remove(resource.fullName);
	}

	public static void deleteAll() {
		for (Map.Entry<String, ManagedResource> resource : resources.entrySet()) {
			if (resource.getValue().delete())
				Logs.d("Cleanup: deleted " + resource.getKey());
		}
		resources.clear();
	}
}
