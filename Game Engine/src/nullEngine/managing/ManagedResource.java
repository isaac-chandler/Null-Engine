package nullEngine.managing;

public abstract class ManagedResource {

	public static final ManagedResource[] NO_DEPENDENCIES = new ManagedResource[0];
	protected int references = 0;
	private boolean isNotDisposed = true;

	public abstract String getName();

	public abstract String getType();

	public ManagedResource[] getDependencies() {
		return NO_DEPENDENCIES;
	}

	public ManagedResource addReference() {
		references++;
		for (ManagedResource resource : getDependencies())
			resource.addReference();
		return this;
	}

	public void init() {
		ResourceManager.add(this);
		addReference();
	}

	public void dispose() {
		if (isNotDisposed) {
			references--;

			if (references <= 0) {
				ResourceManager.delete(this);
				isNotDisposed = false;
			}

			for (ManagedResource resource : getDependencies())
				resource.dispose();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		dispose();
	}

	public abstract ResourceDeleter getDeleter();
}
