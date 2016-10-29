package math;

/**
 * A 4D (or 3D homogeneous) vector
 */
public class Vector4f {
	public static final Vector4f UP = new Vector4f(0, 1, 0);
	public static final Vector4f DOWN = new Vector4f(0, -1, 0);
	public static final Vector4f LEFT = new Vector4f(0, 0, -1);
	public static final Vector4f RIGHT = new Vector4f(0, 0, 1);
	public static final Vector4f FORWARD = new Vector4f(0, 0, 1);
	public static final Vector4f BACK = new Vector4f(0, 0, -1);
	public static final Vector4f ZERO = new Vector4f(0, 0, 0);
	public float x = 0, y = 0, z = 0, w = 1;

	/**
	 * Create a 3D homogeneous vector
	 * @param x The x value
	 * @param y The y value
	 * @param z The z value
	 */
	public Vector4f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Create a 4D vector
	 * @param x The x value
	 * @param y The y value
	 * @param z The z value
	 * @param w The w value
	 */
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Create a 3D homogeneous zero vector
	 */
	public Vector4f() {

	}

	/**
	 * Get the length of the vector squared
	 * @param src the vector
	 * @return The length of the vector squared
	 */
	public static float lengthSquared(Vector4f src) {
		return src.x * src.x + src.y * src.y + src.z * src.z + src.w * src.w;
	}

	/**
	 * @see #lengthSquared(Vector4f)
	 * @return
	 */
	public float lengthSquared() {
		return lengthSquared(this);
	}

	/**
	 * Get the length of the vector
	 * @param src the vector
	 * @return The length of the vector
	 */
	public static float length(Vector4f src) {
		return (float) Math.sqrt(lengthSquared(src));
	}

	/**
	 * @see #length(Vector4f)
	 * @return
	 */
	public float length() {
		return length(this);
	}

	/**
	 * Get the dot product of two vectors
	 * @param left The left vector
	 * @param right The right vector
	 * @return The dot product of the vectors
	 */
	public static float dot(Vector4f left, Vector4f right) {
		return left.x * right.x + left.y * right.y + left.z * right.z + left.w * right.w;
	}

	/**
	 * @see #dot(Vector4f, Vector4f)
	 * @param right
	 * @return
	 */
	public float dot(Vector4f right) {
		return dot(this, right);
	}

	/**
	 * Get the cross product of two vectors
	 * @param left The left vector
	 * @param right The right vector
	 * @param dest The destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
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

	/**
	 * @see #cross(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f cross(Vector4f right, Vector4f dest) {
		return cross(this, right, dest);
	}

	/**
	 * @see #cross(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f cross(Vector4f right) {
		return cross(this, right, this);
	}

	/**
	 * Linear interpolate between two vectors
	 * @param left The first vector
	 * @param right  The second vector
	 * @param lerpFactor The amount to interpolate by 0 = the first vector 1 = the second vector
	 * @param dest The destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f lerp(Vector4f left, Vector4f right, float lerpFactor, Vector4f dest) {
		return right.sub(left, null).mul(lerpFactor).add(left, dest);
	}

	/**
	 * @see #lerp(Vector4f, Vector4f, float, Vector4f)
	 * @param left
	 * @param right
	 * @param lerpFactor
	 * @return
	 */
	public static Vector4f lerp(Vector4f left, Vector4f right, float lerpFactor) {
		return lerp(left, right, lerpFactor, left);
	}

	/**
	 * @see #lerp(Vector4f, Vector4f, float, Vector4f)
	 * @param right
	 * @param lerpFactor
	 * @param dest
	 * @return
	 */
	public Vector4f lerp(Vector4f right, float lerpFactor, Vector4f dest) {
		return lerp(this, right, lerpFactor, dest);
	}

	/**
	 * @see  #lerp(Vector4f, Vector4f, float, Vector4f)
	 * @param right
	 * @param lerpFactor
	 * @return
	 */
	public Vector4f lerp(Vector4f right, float lerpFactor) {
		return lerp(this, right, lerpFactor, this);
	}

	/**
	 * Add two vectors
	 * @param left The left vector
	 * @param right The right vector
	 * @param dest The destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f add(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;
		dest.w = left.w + right.w;

		return dest;
	}

	/**
	 * @see #add(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f add(Vector4f right, Vector4f dest) {
		return add(this, right, dest);
	}

	/**
	 * @see #add(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f add(Vector4f right) {
		return add(this, right, this);
	}

	/**
	 * Add a constant to each componet of a vector
	 * @param left The vector
	 * @param right The constant
	 * @param dest the destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f add(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x + right;
		dest.y = left.y + right;
		dest.z = left.z + right;
		dest.w = left.w + right;

		return dest;
	}

	/**
	 * @see #add(Vector4f, float, Vector4f)
	 * @param left
	 * @param right
	 * @return
	 */
	public static Vector4f add(Vector4f left, float right) {
		return add(left, right, left);
	}

	/**
	 * @see #add(Vector4f, float, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f add(float right, Vector4f dest) {
		return add(this, right, dest);
	}

	/**
	 * @see #add(Vector4f, float, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f add(float right) {
		return add(this, right, this);
	}

	/**
	 * Subtract two vectors
	 * @param left The left vector
	 * @param right The right vector
	 * @param dest The destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f sub(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;
		dest.w = left.w - right.w;

		return dest;
	}

	/**
	 * @see #sub(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f sub(Vector4f right, Vector4f dest) {
		return sub(this, right, dest);
	}

	/**
	 * @see #sub(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f sub(Vector4f right) {
		return sub(this, right, this);
	}

	/**
	 * Subtract a constant from each componet of a vector
	 * @param left The vector
	 * @param right The constant
	 * @param dest the destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f sub(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x - right;
		dest.y = left.y - right;
		dest.z = left.z - right;
		dest.w = left.w - right;

		return dest;
	}

	/**
	 * @see #sub(Vector4f, float, Vector4f)
	 * @param left
	 * @param right
	 * @return
	 */
	public static Vector4f sub(Vector4f left, float right) {
		return sub(left, right, left);
	}

	/**
	 * @see #sub(Vector4f, float, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f sub(float right, Vector4f dest) {
		return sub(this, right, dest);
	}

	/**
	 * @see #sub(Vector4f, float, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f sub(float right) {
		return sub(this, right, this);
	}

	/**
	 * Multiply two vectors
	 * @param left The left vector
	 * @param right The right vector
	 * @param dest The destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f mul(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x * right.x;
		dest.y = left.y * right.y;
		dest.z = left.z * right.z;
		dest.w = left.w * right.w;

		return dest;
	}

	/**
	 * @see #mul(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f mul(Vector4f right, Vector4f dest) {
		return mul(this, right, dest);
	}

	/**
	 * @see #mul(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f mul(Vector4f right) {
		return mul(this, right, this);
	}

	/**
	 * Multiply each componet of a vector by a constant
	 * @param left The vector
	 * @param right The constant
	 * @param dest the destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f mul(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x * right;
		dest.y = left.y * right;
		dest.z = left.z * right;
		dest.w = left.w * right;

		return dest;
	}

	/**
	 * @see #mul(Vector4f, float, Vector4f)
	 * @param left
	 * @param right
	 * @return
	 */
	public static Vector4f mul(Vector4f left, float right) {
		return mul(left, right, left);
	}

	/**
	 * @see #mul(Vector4f, float, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f mul(float right, Vector4f dest) {
		return mul(this, right, dest);
	}

	/**
	 * @see #mul(Vector4f, float, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f mul(float right) {
		return mul(this, right, this);
	}

	/**
	 * Divide two vectors
	 * @param left The left vector
	 * @param right The right vector
	 * @param dest The destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f div(Vector4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x / right.x;
		dest.y = left.y / right.y;
		dest.z = left.z / right.z;
		dest.w = left.w / right.w;

		return dest;
	}

	/**
	 * @see #div(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f div(Vector4f right, Vector4f dest) {
		return div(this, right, dest);
	}

	/**
	 * @see #div(Vector4f, Vector4f, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f div(Vector4f right) {
		return div(this, right, this);
	}

	/**
	 * Divide each componet of a vector by a constant
	 * @param left The vector
	 * @param right The constant
	 * @param dest the destination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f div(Vector4f left, float right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		dest.x = left.x / right;
		dest.y = left.y / right;
		dest.z = left.z / right;
		dest.w = left.w / right;

		return dest;
	}

	/**
	 * @see #div(Vector4f, float, Vector4f)
	 * @param left
	 * @param right
	 * @return
	 */
	public static Vector4f div(Vector4f left, float right) {
		return div(left, right, left);
	}

	/**
	 * @see #div(Vector4f, float, Vector4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Vector4f div(float right, Vector4f dest) {
		return div(this, right, dest);
	}

	/**
	 * @see #div(Vector4f, float, Vector4f)
	 * @param right
	 * @return
	 */
	public Vector4f div(float right) {
		return div(this, right, this);
	}

	/**
	 * Normalize a vector
	 * @param src The vector to normalize
	 * @param dest The detination vector
	 * @return The destination vector, or if it was null a new vector
	 */
	public static Vector4f normalize(Vector4f src, Vector4f dest) {
		return div(src, length(src), dest);
	}

	/**
	 * @see #normalize(Vector4f, Vector4f)
	 * @param dest
	 * @return
	 */
	public Vector4f normalize(Vector4f dest) {
		return normalize(this, dest);
	}

	/**
	 * @see #normalize(Vector4f, Vector4f)
	 * @return
	 */
	public Vector4f normalize() {
		return normalize(this, this);
	}

	/**
	 * Checks wether this vector is zero
	 * @param src The vector to check
	 * @return <code>true</code> if the x, y and z components are zero, otherwise <code>false</code>
	 */
	public static boolean isZero(Vector4f src) {
		return src.x == 0 && src.y == 0 && src.z == 0;
	}

	/**
	 * @see #isZero(Vector4f)
	 * @return
	 */
	public boolean isZero() {
		return isZero(this);
	}

	/**
	 * Convert this vecotor to a string
	 * @return The string representation
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}
