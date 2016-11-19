package nullEngine.managing;

/**
 * A resource that is reference counted
 */
public abstract class ManagedResource {

	/**
	 * Single zero length array to be used
	 */
	public static final ManagedResource[] NO_DEPENDENCIES = new ManagedResource[0];
	protected int references = 0;
	private boolean isNotDeleted = true;
	/**
	 * The resources this resource depends on
	 */
	public final ManagedResource[] dependencies;
	/**
	 * The full name of this resource in the form type:name e.g. texture:res/textures/example.png
	 */
	public final String fullName;

	/**
	 * Add a reference to this resource
	 *
	 * @return This ManagedResource
	 */
	public ManagedResource addReference() {
		references++;

		return this;
	}

	/**
	 * Utility method for joining arrays
	 *
	 * @param value The value to add
	 * @param arr   The array to add
	 * @return The combination of the value and the array
	 */
	public static ManagedResource[] join(ManagedResource value, ManagedResource[] arr) {
		ManagedResource[] newArr = new ManagedResource[arr.length + 1];
		newArr[0] = value;
		System.arraycopy(arr, 0, newArr, 1, arr.length);
		return newArr;
	}

	/**
	 * Utility method for joining arrays
	 *
	 * @param arr1 The first array
	 * @param arr2 The second array
	 * @return The combination of the value and the array
	 */
	public static ManagedResource[] join(ManagedResource[] arr1, ManagedResource[] arr2) {
		ManagedResource[] newArr = new ManagedResource[arr1.length + arr2.length];
		System.arraycopy(arr1, 0, newArr, 0, arr1.length);
		System.arraycopy(arr2, 0, newArr, arr1.length, arr2.length);
		return newArr;
	}

	/**
	 * Utility method for joining arrays
	 *
	 * @param arr   The array to add
	 * @param value The value to add
	 * @return The combination of the array and the value
	 */
	public static ManagedResource[] join(ManagedResource[] arr, ManagedResource value) {
		ManagedResource[] newArr = new ManagedResource[arr.length + 1];
		newArr[arr.length] = value;
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		return newArr;
	}

	/**
	 * Create a new managed resource
	 *
	 * @param name         The name of this resource
	 * @param type         The type of this resource
	 * @param dependencies The resorurces this requires
	 */
	public ManagedResource(String name, String type, ManagedResource... dependencies) {
		this.fullName = type + ":" + name;
		this.dependencies = dependencies;
		for (ManagedResource resource : dependencies)
			resource.addReference();
		ResourceManager.add(this);
	}

	/**
	 * Remove a reference from this resource, if it has no more references than delete it
	 */
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

	/**
	 * Delete this resource
	 */
	@Override
	protected void finalize() {
		references = 0;
		dispose();
	}

	/**
	 * Convert this resource to a string
	 *
	 * @return The full name of this resource
	 */
	@Override
	public String toString() {
		return fullName;
	}

	/**
	 * Delete this resource, DO NOT CALL DIRECTLY
	 *
	 * @return <code>true</code> if it should be logged to the debug log when this resource is deleted
	 */
	public abstract boolean delete();

	/**
	 * Get the hash code of this resource
	 *
	 * @return The hash code of this resources full name
	 */
	@Override
	public int hashCode() {
		return fullName.hashCode();
	}
}
