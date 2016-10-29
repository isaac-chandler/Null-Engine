package math;

public class MathUtil {

	/**
	 * Floating point PI
	 */
	public static final float PI = (float) Math.PI;
	/**
	 * Floating point E
	 */
	public static final float E = (float) Math.E;

	/**
	 * Perform barrycentric interpolation
	 * @param v1 The first vertex
	 * @param v2 The second vertex
	 * @param v3 The third vertex
	 * @param x The x value to use
	 * @param y the y value to use
	 * @return The y coordinate at (x, y) on the triangle
	 */
	public static float barryCentric(Vector4f v1, Vector4f v2, Vector4f v3, float x, float y) {
		float det = (v2.z - v3.z) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.z - v3.z);
		float l1 = ((v2.z - v3.z) * (x - v3.x) + (v3.x - v2.x) * (y - v3.z)) / det;
		float l2 = ((v3.z - v1.z) * (x - v3.x) + (v1.x - v3.x) * (y - v3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * v1.y + l2 * v2.y + l3 * v3.y;
	}


	/**
	 * Clamp a value between min and max
	 * @param val The value to clamp
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return val clamped between min and max
	 */
	public static int clamp(int val, int min, int max) {
		return val > min ? (val < max ? val : max) : min;
	}

	/**
	 * Clamp a value between min and max
	 * @param val The value to clamp
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return val clamped between min and max
	 */
	public static float clamp(float val, float min, float max) {
		return val > min ? (val < max ? val : max) : min;
	}
}
