package math;

public class Quaternion {
	public float x = 0, y = 0, z = 0, w = 1;

	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion() {
	}

	public Quaternion(float angle, Vector4f axis) {
		float s = (float) Math.sin(angle / 2);
		float c = (float) Math.cos(angle / 2);

		x = axis.x * s;
		y = axis.y * s;
		z = axis.z * s;
		w = c;
	}

	public static Quaternion conjugate(Quaternion src, Quaternion dest) {
		if (dest == null)
			dest = new Quaternion();

		dest.x = -src.x;
		dest.y = -src.y;
		dest.z = -src.z;
		dest.w = src.w;
		return dest;
	}

	public static Quaternion conjuagete(Quaternion src) {
		return conjugate(src, src);
	}

	public Quaternion conjugate(Quaternion dest) {
		return conjugate(this, dest);
	}

	public Quaternion conjugate() {
		return conjugate(this, this);
	}

	public static Quaternion mul(Quaternion left, Quaternion right, Quaternion dest) {
		if (dest == null)
			dest = new Quaternion();

		float w = left.w * right.w - left.x * right.x - left.y * right.y - left.z * right.z;
		float x = left.w * right.x + left.x * right.w + left.y * right.z - left.z * right.y;
		float y = left.w * right.y + left.y * right.w + left.z * right.x - left.x * right.z;
		float z = left.w * right.z + left.z * right.w + left.x * right.y - left.y * right.x;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;
		return dest;
	}

	public Quaternion mul(Quaternion right, Quaternion dest) {
		return mul(this, right, dest);
	}

	public Quaternion mul(Quaternion right) {
		return mul(this, right, this);
	}

	public static Quaternion mul(Quaternion left, Vector4f right, Quaternion dest) {
		if (dest == null)
			dest = new Quaternion();

		float w = -left.x * right.x - left.y * right.y - left.z * right.z;
		float x = left.w * right.y + left.y * right.z - left.z * right.y;
		float y = left.w * right.z + left.z * right.x - left.x * right.z;
		float z = left.w * right.z + left.x * right.y - left.y * right.x;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;
		return dest;
	}

	public static Quaternion mul(Quaternion left, Vector4f right) {
		return mul(left, right, left);
	}

	public Quaternion mul(Vector4f right, Quaternion dest) {
		return mul(this, right, dest);
	}

	public Quaternion mul(Vector4f right) {
		return mul(this, right, this);
	}

	public Vector4f getRight(Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = 1.0f - 2.0f * (y * y + z * z);
		dest.y = 2.0f * (x * y - w * z);
		dest.z = 2.0f * (x * z + w * y);

		return dest;
	}

	public Vector4f getUp(Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = 2.0f * (x * y + w * z);
		dest.y = 1.0f - 2.0f * (x * x + z * z);
		dest.z = 2.0f * (y * z - w * x);

		return dest;
	}

	public Vector4f getForward(Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = 2.0f * (x * z - w * y);
		dest.y = 2.0f * (y * z + w * x);
		dest.z = 1.0f - 2.0f * (x * x + y * y);

		return dest;
	}

	public Matrix4f toRotationMatrix(Matrix4f dest) {
		return Matrix4f.setRotation(getRight(null), getUp(null), getForward(null), dest);
	}

	@Override
	public String toString() {
		return "Quaternion(" + x + ", " + y + ", " + z + ", " + w  + ")";
	}
}
