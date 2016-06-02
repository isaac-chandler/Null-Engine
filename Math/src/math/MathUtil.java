package math;

public class MathUtil {

	public static final float PI = (float) Math.PI;
	public static final float E = (float) Math.E;

	public static float barryCentric(Vector4f v1, Vector4f v2, Vector4f v3, float x, float y) {
		float det = (v2.z - v3.z) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.z - v3.z);
		float l1 = ((v2.z - v3.z) * (x - v3.x) + (v3.x - v2.x) * (y - v3.z)) / det;
		float l2 = ((v3.z - v1.z) * (x - v3.x) + (v1.x - v3.x) * (y - v3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * v1.y + l2 * v2.y + l3 * v3.y;
	}


	public static int clamp(int val, int min, int max) {
		return val > min ? (val < max ? val : max) : min;
	}

	public static float clamp(float val, float min, float max) {
		return val > min ? (val < max ? val : max) : min;
	}
}
