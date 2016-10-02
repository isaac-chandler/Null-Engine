package util;

public interface Resizer {
	int resize(int currentSize, int requiredSize);

	public static final Resizer DOUBLE_RESIZE = new Resizer() {
		@Override
		public int resize(int currentSize, int requiredSize) {
			while ((currentSize *= 2) < requiredSize);
			return currentSize;
		}
	};

	public static final Resizer LINEAR_RESIZE = new Resizer() {
		@Override
		public int resize(int currentSize, int requiredSize) {
			return requiredSize;
		}
	};
}
