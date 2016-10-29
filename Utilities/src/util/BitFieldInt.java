package util;

/**
 * List of booleans stored as ints
 */
public class BitFieldInt {

	private int[] arr;
	private static final int SIZE_MINUS_ONE = Integer.SIZE - 1;
	private static final int SIZE = Integer.SIZE;
	private int capacity = 0;

	/**
	 * The Resizer used to resize the underlying int array
	 * @see Resizer
	 */
	public Resizer resizer = Resizer.DOUBLE_RESIZE;

	/**
	 * Creates a new bitfield
	 * @param capacity the minimum capacity of the bitfield
	 */
	public BitFieldInt(int capacity) {
		arr = new int[(capacity + SIZE_MINUS_ONE) / SIZE];
	}

	/**
	 * Creates a new bitfield with a minimum capacity of 1
	 * @see #BitFieldInt(int)
	 */
	public BitFieldInt() {
		this(1);
	}

	/**
	 * Get a value from the bitfield
	 * @param index the index to get it from
	 * @return The value at index or <code>false</code> if index is outside of the bitfield
	 */
	public boolean get(int index) {
		if (index / SIZE >= arr.length) {
			return false;
		}

		int field = arr[index / SIZE];
		int fieldIdx = index % SIZE;
		return (field & (1 << fieldIdx)) != 0;
	}

	/**
	 * Set a value in the bitfield
	 * @param index the index to set
	 * @param value the value to set it to
	 */
	public void set(int index, boolean value) {
		ensureCapacity(index + 1);
		int fieldIdx = index % SIZE;
		if (value) {
			arr[index / SIZE] |= 1 << fieldIdx;
		} else {
			arr[index / SIZE] &= ~(1 << fieldIdx);
		}
	}

	/**
	 * Flips the value at index
	 * @param index the index to flip
	 * @return the new value
	 * @throws IndexOutOfBoundsException If the index is greater than the capacity 
	 */
	public boolean invert(int index) {
		if (index >= capacity) {
			throw new ArrayIndexOutOfBoundsException(index);
		}

		int field = arr[index / SIZE];
		int fieldIdx = index % SIZE;
		arr[index / SIZE] = field ^= (1 << fieldIdx);
		return (field & (1 << fieldIdx)) != 0;
	}

	/**
	 * Add a vaule to the end of the bitfield
	 * @param value the value to add
	 * @return the index it was added at
	 */
	public int add(boolean value) {
		capacity++;
		set(capacity - 1, value);
		return capacity - 1;
	}

	/**
	 * Get the capacity of this bitfield
	 * @return The capacity of this bitfield
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Set the capacity of this bitfield
	 * @param capacity The new capacity
	 */
	public void setCapacity(int capacity) {
		if (capacity > this.capacity)
			ensureCapacity(capacity);
		else if (capacity < this.capacity) {
			trimTo(capacity);
		}
		this.capacity = capacity;
	}

	/**
	 * Trim any unused capacity out of the underlying array
	 */
	public void trim() {
		trimTo(capacity);
	}

	private void trimTo(int capacity) {
		this.capacity = capacity;
		int reqSize = (capacity + SIZE_MINUS_ONE) / SIZE;
		if (arr.length > reqSize) {
			int[] newArr = new int[reqSize];
			System.arraycopy(arr, 0, newArr, 0, newArr.length);
			arr = newArr;
		}
	}

	private void ensureCapacity(int capacity) {
		int reqSize = (capacity + SIZE_MINUS_ONE) / SIZE;
		if (arr.length < reqSize) {
			int newSize = resizer.resize(arr.length, reqSize);
			int[] newArr = new int[newSize];
			System.arraycopy(arr, 0, newArr, 0, arr.length);
			arr = newArr;
		}
		this.capacity = Math.max(capacity, this.capacity);
	}
}
