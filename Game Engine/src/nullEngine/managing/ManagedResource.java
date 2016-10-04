package nullEngine.managing;

public abstract class ManagedResource {

	public static final ManagedResource[] NO_DEPENDENCIES = new ManagedResource[0];
	protected int references = 0;
	private boolean isNotDeleted = true;
	public final ManagedResource[] dependencies;
	public final String fullName;

	public ManagedResource addReference() {
		references++;

		return this;
	}

	public ManagedResource(String name, String type, ManagedResource[] dependencies) {
		fullName = type + ":" + name;
		this.dependencies = dependencies;
		for (ManagedResource resource : dependencies)
			resource.addReference();
		ResourceManager.add(this);
	}

	public ManagedResource(String name, String type) {
		this(name, type, NO_DEPENDENCIES);
	}

	public void dispose() {
		if (isNotDeleted) {
			references--;

			if (references <= 0) {
				ResourceManager.delete(this);
				isNotDeleted = false;
				for (ManagedResource resource : dependencies)
					resource.dispose();
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		references = 0;
		dispose();
	}

	public abstract void delete();
}
