package math;

public class Vector4f {
	public static final Vector4f UP = new Vector4f(0, 1, 0);
	public static final Vector4f DOWN = new Vector4f(0, -1, 0);
	public static final Vector4f LEFT = new Vector4f(0, 0, -1);
	public static final Vector4f RIGHT = new Vector4f(0, 0, 1);
	public static final Vector4f FORWARD = new Vector4f(0, 0, 1);
	public static final Vector4f BACK = new Vector4f(0, 0, -1);
	public static final Vector4f ZERO = new Vector4f(0, 0, 0);
	public float x = 0, y = 0, z = 0, w = 1;

	public Vector4f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f() {

	}

	public static float lengthSquared(Vector4f src) {
		return src.x * src.x + src.y * src.y + src.z * src.z + src.w * src.w;
	}

	public float lengthSquared() {
		return lengthSquared(this);
	}

	public static float length(Vector4f src) {
		return (float) Math.sqrt(lengthSquared(src));
	}

	public float length() {
		return length(this);
	}

	public static float dot(Vector4f left, Vector4f right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}

	public float dot(Vector4f right) {
		return dot(this, right);
	}

	public static Vector4f cross(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		float x = left.y * right.z - left.z * right.y;
		float y = left.z * right.x - left.x * right.z;
		float z = left.x * right.y - left.y * right.x;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = 1;
		return dest;
	}

	public Vector4f cross(Vector4f right, Vector4f dest) {
		return cross(this, right, dest);
	}

	public Vector4f cross(Vector4f right) {
		return cross(this, right, this);
	}

	public static Vector4f lerp(Vector4f left, Vector4f right, float lerpFactor, Vector4f dest) {
		return right.sub(left, null).mul(lerpFactor).add(left, dest);
	}

	public static Vector4f lerp(Vector4f left, Vector4f right, float lerpFactor) {
		return lerp(left, right, lerpFactor, left);
	}

	public Vector4f lerp(Vector4f right, float lerpFactor, Vector4f dest) {
		return lerp(this, right, lerpFactor, dest);
	}

	public Vector4f lerp(Vector4f right, float lerpFactor) {
		return lerp(this, right, lerpFactor, this);
	}

	public static Vector4f add(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;
		dest.w = left.w + right.w;

		return dest;
	}

	public Vector4f add(Vector4f right, Vector4f dest) {
		return add(this, right, dest);
	}

	public Vector4f add(Vector4f right) {
		return add(this, right, this);
	}

	public static Vector4f add(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x + right;
		dest.y = left.y + right;
		dest.z = left.z + right;
		dest.w = left.w + right;

		return dest;
	}

	public static Vector4f add(Vector4f left, float right) {
		return add(left, right, left);
	}

	public Vector4f add(float right, Vector4f dest) {
		return add(this, right, dest);
	}

	public Vector4f add(float right) {
		return add(this, right, this);
	}

	public static Vector4f sub(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;
		dest.w = left.w - right.w;

		return dest;
	}

	public Vector4f sub(Vector4f right, Vector4f dest) {
		return sub(this, right, dest);
	}

	public Vector4f sub(Vector4f right) {
		return sub(this, right, this);
	}

	public static Vector4f sub(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x - right;
		dest.y = left.y - right;
		dest.z = left.z - right;
		dest.w = left.w - right;

		return dest;
	}

	public static Vector4f sub(Vector4f left, float right) {
		return sub(left, right, left);
	}

	public Vector4f sub(float right, Vector4f dest) {
		return sub(this, right, dest);
	}

	public Vector4f sub(float right) {
		return sub(this, right, this);
	}

	public static Vector4f mul(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;
		dest.z = left.z * right.z;
		dest.w = left.w * right.w;

		return dest;
	}

	public Vector4f mul(Vector4f right, Vector4f dest) {
		return mul(this, right, dest);
	}

	public Vector4f mul(Vector4f right) {
		return mul(this, right, this);
	}

	public static Vector4f mul(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x * right;
		dest.y = left.y * right;
		dest.z = left.z * right;
		dest.w = left.w * right;

		return dest;
	}

	public static Vector4f mul(Vector4f left, float right) {
		return mul(left, right, left);
	}

	public Vector4f mul(float right, Vector4f dest) {
		return mul(this, right, dest);
	}

	public Vector4f mul(float right) {
		return mul(this, right, this);
	}

	public static Vector4f div(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;
		dest.z = left.z / right.z;
		dest.w = left.w / right.w;

		return dest;
	}

	public Vector4f div(Vector4f right, Vector4f dest) {
		return div(this, right, dest);
	}

	public Vector4f div(Vector4f right) {
		return div(this, right, this);
	}

	public static Vector4f div(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x / right;
		dest.y = left.y / right;
		dest.z = left.z / right;
		dest.w = left.w / right;

		return dest;
	}

	public static Vector4f div(Vector4f left, float right) {
		return div(left, right, left);
	}

	public Vector4f div(float right, Vector4f dest) {
		return div(this, right, dest);
	}

	public Vector4f div(float right) {
		return div(this, right, this);
	}

	public static Vector4f normalize(Vector4f src, Vector4f dest) {
		return div(src, length(src), dest);
	}

	public Vector4f normalize(Vector4f dest) {
		return normalize(this, dest);
	}

	public Vector4f normalize() {
		return normalize(this, this);
	}

	public static boolean isZero(Vector4f src) {
		return src.x == 0 && src.y == 0 && src.z == 0;
	}

	public boolean isZero() {
		return isZero(this);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}
