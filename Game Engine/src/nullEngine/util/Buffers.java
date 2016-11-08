package nullEngine.util;

import org.lwjgl.BufferUtils;

import java.nio.*;

public class Buffers {
	/**
	 * Create a ByteBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static ByteBuffer createBuffer(byte[] arr) {
		ByteBuffer buf = BufferUtils.createByteBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	/**
	 * Create a CharBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static CharBuffer createBuffer(char[] arr) {
		CharBuffer buf = BufferUtils.createCharBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	/**
	 * Create a ShortBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static ShortBuffer createBuffer(short[] arr) {
		ShortBuffer buf = BufferUtils.createShortBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	/**
	 * Create a IntBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static IntBuffer createBuffer(int[] arr) {
		IntBuffer buf = BufferUtils.createIntBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	/**
	 * Create a FloatBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static FloatBuffer createBuffer(float[] arr) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	/**
	 * Create a LongBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static LongBuffer createBuffer(long[] arr) {
		LongBuffer buf = BufferUtils.createLongBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}

	/**
	 * Create a DoubleBuffer from an array
	 * @param arr The array
	 * @return The buffer that was created
	 */
	public static DoubleBuffer createBuffer(double[] arr) {
		DoubleBuffer buf = BufferUtils.createDoubleBuffer(arr.length);
		buf.put(arr).flip();
		return buf;
	}
}
