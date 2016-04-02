package nullEngine.managing;

public class NullResourceDeleter implements ResourceDeleter {
	public static final NullResourceDeleter instance = new NullResourceDeleter();

	private NullResourceDeleter() {}

	@Override
	public void deleteResource(ManagedResource resource) {}
}
