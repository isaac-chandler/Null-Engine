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

	public static ManagedResource[] join(ManagedResource value, ManagedResource[] arr) {
		ManagedResource[] newArr = new ManagedResource[arr.length + 1];
		newArr[0] = value;
		System.arraycopy(arr, 0, newArr, 1, arr.length);
		return newArr;
	}

	public static ManagedResource[] join(ManagedResource[] arr1, ManagedResource[] arr2) {
		ManagedResource[] newArr = new ManagedResource[arr1.length + arr2.length];
		System.arraycopy(arr1, 0, newArr, 0, arr1.length);
		System.arraycopy(arr2, 0, newArr, arr1.length, arr2.length);
		return newArr;
	}

	public static ManagedResource[] join(ManagedResource[] arr, ManagedResource value) {
		ManagedResource[] newArr = new ManagedResource[arr.length + 1];
		newArr[arr.length] = value;
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		return newArr;
	}

	public ManagedResource(String name, String type, ManagedResource... dependencies) {
		this.fullName = type + ":" + name;
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

	@Override
	public String toString() {
		return fullName;
	}

	public abstract boolean delete();
}
