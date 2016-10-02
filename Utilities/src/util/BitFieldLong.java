package util;

public class BitFieldLong {

	private long[] arr;
	private static final int SIZE_MINUS_ONE = Long.SIZE - 1;
	private static final int SIZE = Long.SIZE;
	private int capacity = 0;
	public Resizer resizer = Resizer.DOUBLE_RESIZE;

	public BitFieldLong(int capacity) {
		arr = new long[(capacity + SIZE_MINUS_ONE) / SIZE];
	}

	public BitFieldLong() {
		this(1);
	}

	public boolean get(int index) {
		if (index >= capacity) {
			throw new ArrayIndexOutOfBoundsException(index);
		}

		long field = arr[index / SIZE];
		int fieldIdx = index % SIZE;
		return (field & (1 << fieldIdx)) != 0;
	}

	public void set(int index, boolean value) {
		ensureCapacity(index + 1);
		int fieldIdx = index % SIZE;
		if (value) {
			arr[index / SIZE] |= 1 << fieldIdx;
		} else {
			arr[index / SIZE] &= ~(1 << fieldIdx);
		}
	}

	public boolean invert(int index) {
		if (index >= capacity) {
			throw new ArrayIndexOutOfBoundsException(index);
		}

		long field = arr[index / SIZE];
		int fieldIdx = index % SIZE;
		arr[index / SIZE] = field ^= (1 << fieldIdx);
		return (field & (1 << fieldIdx)) != 0;
	}

	public int add(boolean value) {
		capacity++;
		set(capacity - 1, value);
		return capacity - 1;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		if (capacity > this.capacity)
			ensureCapacity(capacity);
		else if (capacity < this.capacity) {
			trimTo(capacity);
		}
		this.capacity = capacity;
	}

	public void trim() {
		trimTo(capacity);
	}

	private void trimTo(int capacity) {
		this.capacity = capacity;
		int reqSize = (capacity + SIZE_MINUS_ONE) / SIZE;
		if (arr.length > reqSize) {
			long[] newArr = new long[reqSize];
			System.arraycopy(arr, 0, newArr, 0, newArr.length);
			arr = newArr;
		}
	}

	private void ensureCapacity(int capacity) {
		int reqSize = (capacity + SIZE_MINUS_ONE) / SIZE;
		if (arr.length < reqSize) {
			int newSize = resizer.resize(arr.length, reqSize);
			long[] newArr = new long[newSize];
			System.arraycopy(arr, 0, newArr, 0, arr.length);
			arr = newArr;
		}
		this.capacity = Math.max(capacity, this.capacity);
	}
}
