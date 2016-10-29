package util;

/**
 * Specifies how a dynamically resizing object should be resized
 */
public interface Resizer {
	/**
	 * Calculate the new size of a dynamically sized object
	 * @param currentSize the current size of the object
	 * @param requiredSize the minimum size requirement
	 * @return
	 */
	int resize(int currentSize, int requiredSize);

	/**
	 * Resizer that doubles the current size until it is greater than the required size
	 */
	public static final Resizer DOUBLE_RESIZE = new Resizer() {
		@Override
		public int resize(int currentSize, int requiredSize) {
			while ((currentSize *= 2) < requiredSize);
			return currentSize;
		}
	};

	/**
	 * Resizer that returns the required size
	 */
	public static final Resizer LINEAR_RESIZE = new Resizer() {
		@Override
		public int resize(int currentSize, int requiredSize) {
			return requiredSize;
		}
	};
}
