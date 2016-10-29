package math;

/**
 * A quaternion
 */
public class Quaternion {
	public float x = 0, y = 0, z = 0, w = 1;

	/**
	 * Create a new quaternion
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 * @param w the w value
	 */
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Create a new quaternion with default value (0, 0, 0, 1)
	 */
	public Quaternion() {
	}

	/**
	 * Create a new quaternion
	 * @param angle the amount to rotate by
	 * @param axis the axis to rotate around
	 */
	public Quaternion(float angle, Vector4f axis) {
		float s = (float) Math.sin(angle / 2);
		float c = (float) Math.cos(angle / 2);

		x = axis.x * s;
		y = axis.y * s;
		z = axis.z * s;
		w = c;
	}

	/**
	 * Take the conjugate of a quaternion
	 * @param src the quaternion to take the conjugate of
	 * @param dest the destination quaternion
	 * @return the destination quaternion or if it was null a new quaternion
	 */
	public static Quaternion conjugate(Quaternion src, Quaternion dest) {
		if (dest == null)
			dest = new Quaternion();

		dest.x = -src.x;
		dest.y = -src.y;
		dest.z = -src.z;
		dest.w = src.w;
		return dest;
	}

	/**
	 * @see #conjugate(Quaternion, Quaternion)
	 * @param src
	 * @return
	 */
	public static Quaternion conjuagete(Quaternion src) {
		return conjugate(src, src);
	}

	/**
	 * @see #conjugate(Quaternion, Quaternion)
	 * @param dest
	 * @return
	 */
	public Quaternion conjugate(Quaternion dest) {
		return conjugate(this, dest);
	}

	/**
	 * @see #conjugate(Quaternion, Quaternion)
	 * @return
	 */
	public Quaternion conjugate() {
		return conjugate(this, this);
	}

	/**
	 * Multiply to quaternions
	 * @param left The left quaternion
	 * @param right The right quaternion
	 * @param dest The destination quaternion
	 * @return The destination quaternion, or if it was null a new quaternion
	 */
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

	/**
	 * @see #mul(Quaternion, Quaternion, Quaternion)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Quaternion mul(Quaternion right, Quaternion dest) {
		return mul(this, right, dest);
	}

	/**
	 * @see #mul(Quaternion, Quaternion, Quaternion)
	 * @param right
	 * @return
	 */
	public Quaternion mul(Quaternion right) {
		return mul(this, right, this);
	}

	/**
	 * Multiply a quaternion by a vector
	 * @param left The quaternion to multiply
	 * @param right The vector to multiply by
	 * @param dest The destination quaternion
	 * @return The destination quaternion, or if it was null a new quaternion
	 */
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

	/**
	 * @see #mul(Quaternion, Vector4f, Quaternion)
	 * @param left
	 * @param right
	 * @return
	 */
	public static Quaternion mul(Quaternion left, Vector4f right) {
		return mul(left, right, left);
	}

	/**
	 * @see #mul(Quaternion, Vector4f, Quaternion)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Quaternion mul(Vector4f right, Quaternion dest) {
		return mul(this, right, dest);
	}

	/**
	 * @see #mul(Quaternion, Vector4f, Quaternion)
	 * @param right
	 * @return
	 */
	public Quaternion mul(Vector4f right) {
		return mul(this, right, this);
	}

	/**
	 * Get the right vector
	 * @param dest The destination vector
	 * @return The destination vector or if it was null a new vector
	 */
	public Vector4f getRight(Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = 1.0f - 2.0f * (y * y + z * z);
		dest.y = 2.0f * (x * y - w * z);
		dest.z = 2.0f * (x * z + w * y);

		return dest;
	}

	/**
	 * Get the up vector
	 * @param dest The destination vector
	 * @return The destination vector or if it was null a new vector
	 */
	public Vector4f getUp(Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = 2.0f * (x * y + w * z);
		dest.y = 1.0f - 2.0f * (x * x + z * z);
		dest.z = 2.0f * (y * z - w * x);

		return dest;
	}

	/**
	 * Get the forward vector
	 * @param dest The destination vector
	 * @return The destination vector or if it was null a new vector
	 */
	public Vector4f getForward(Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = 2.0f * (x * z - w * y);
		dest.y = 2.0f * (y * z + w * x);
		dest.z = 1.0f - 2.0f * (x * x + y * y);

		return dest;
	}

	/**
	 * Convert to a rotation matrix
	 * @param dest the destination matrix
	 * @return The destination matrix or if it was null a new matrix
	 */
	public Matrix4f toRotationMatrix(Matrix4f dest) {
		return Matrix4f.setRotation(getRight(null), getUp(null), getForward(null), dest);
	}

	/**
	 * Get a string representation of the quaternion
	 * @return The string
	 */
	@Override
	public String toString() {
		return "Quaternion(" + x + ", " + y + ", " + z + ", " + w  + ")";
	}
}
