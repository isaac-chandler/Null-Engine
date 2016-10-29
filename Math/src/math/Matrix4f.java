package math;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.nio.FloatBuffer;

/**
 * 4x4 Matrix
 */
public class Matrix4f implements Serializable {

	/**
	 * The identity matrix
	 */
	public static final Matrix4f IDENTITY = new Matrix4f();

	private static final Matrix4f internal = new Matrix4f();

	public float
			m00 = 1, m01 = 0, m02 = 0, m03 = 0,
			m10 = 0, m11 = 1, m12 = 0, m13 = 0,
			m20 = 0, m21 = 0, m22 = 1, m23 = 0,
			m30 = 0, m31 = 0, m32 = 0, m33 = 1;

	/**
	 * Create a copy of a matrix
	 * @param mat The matrix to copy
	 */
	public Matrix4f(Matrix4f mat) {
		this.m00 = mat.m00;
		this.m01 = mat.m01;
		this.m02 = mat.m02;
		this.m03 = mat.m03;

		this.m10 = mat.m10;
		this.m11 = mat.m11;
		this.m12 = mat.m12;
		this.m13 = mat.m13;

		this.m20 = mat.m20;
		this.m21 = mat.m21;
		this.m22 = mat.m22;
		this.m23 = mat.m23;

		this.m30 = mat.m30;
		this.m31 = mat.m31;
		this.m32 = mat.m32;
		this.m33 = mat.m33;
	}

	/**
	 * Create a new matrix, defaults to the identity matrix
	 */
	public Matrix4f() {}

	/**
	 * Multiply two matrices
	 * @param left The left matrix
	 * @param right The right matrix
	 * @param dest The destination matrix
	 * @return The destination matrix or a new matrix if it is <code>null</code>
	 */
	public static Matrix4f mul(Matrix4f left, Matrix4f right, @Nullable Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		float m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02 + left.m30 * right.m03;
		float m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12 + left.m30 * right.m13;
		float m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22 + left.m30 * right.m23;
		float m30 = left.m00 * right.m30 + left.m10 * right.m31 + left.m20 * right.m32 + left.m30 * right.m33;

		float m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02 + left.m31 * right.m03;
		float m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12 + left.m31 * right.m13;
		float m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22 + left.m31 * right.m23;
		float m31 = left.m01 * right.m30 + left.m11 * right.m31 + left.m21 * right.m32 + left.m31 * right.m33;

		float m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02 + left.m32 * right.m03;
		float m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12 + left.m32 * right.m13;
		float m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 + left.m32 * right.m23;
		float m32 = left.m02 * right.m30 + left.m12 * right.m31 + left.m22 * right.m32 + left.m32 * right.m33;

		float m03 = left.m03 * right.m00 + left.m13 * right.m01 + left.m23 * right.m02 + left.m33 * right.m03;
		float m13 = left.m03 * right.m10 + left.m13 * right.m11 + left.m23 * right.m12 + left.m33 * right.m13;
		float m23 = left.m03 * right.m20 + left.m13 * right.m21 + left.m23 * right.m22 + left.m33 * right.m23;
		float m33 = left.m03 * right.m30 + left.m13 * right.m31 + left.m23 * right.m32 + left.m33 * right.m33;

		dest.m00 = m00;
		dest.m10 = m10;
		dest.m20 = m20;
		dest.m30 = m30;

		dest.m01 = m01;
		dest.m11 = m11;
		dest.m21 = m21;
		dest.m31 = m31;

		dest.m02 = m02;
		dest.m12 = m12;
		dest.m22 = m22;
		dest.m32 = m32;

		dest.m03 = m03;
		dest.m13 = m13;
		dest.m23 = m23;
		dest.m33 = m33;

		return dest;
	}

	/**
	 * @see #mul(Matrix4f, Matrix4f, Matrix4f)
	 * @param right
	 * @param dest
	 * @return
	 */
	public Matrix4f mul(Matrix4f right, @Nullable Matrix4f dest) {
		return mul(this, right, dest);
	}

	/**
	 * @see #mul(Matrix4f, Matrix4f, Matrix4f)
	 * @param right
	 * @return
	 */
	public Matrix4f mul(Matrix4f right) {
		return mul(this, right, this);
	}

	/**
	 * Set a matrix to be the identity
	 * @param dest The matrix to set
	 * @return dest or if dest is <code>null</code> than a new matrix
	 */
	public static Matrix4f setIdentity(@Nullable Matrix4f dest) {
		if (dest == null)
			return new Matrix4f();

		dest.m00 = 1;
		dest.m01 = 0;
		dest.m02 = 0;
		dest.m03 = 0;
		dest.m10 = 0;
		dest.m11 = 1;
		dest.m12 = 0;
		dest.m13 = 0;
		dest.m20 = 0;
		dest.m21 = 0;
		dest.m22 = 1;
		dest.m23 = 0;
		dest.m30 = 0;
		dest.m31 = 0;
		dest.m32 = 0;
		dest.m33 = 1;
		return dest;
	}

	/**
	 * @see #setIdentity(Matrix4f)
	 * @return
	 */
	public Matrix4f setIdentity() {
		return setIdentity(this);
	}

	/**
	 * Set a matrix to be a translation matrix
	 * @param pos The amount of translation
	 * @param dest The matrix to set
	 * @return The destination matrix set to be a translation matrix, if it was null it creates a new matrix
	 */
	public static Matrix4f setTranslation(Vector4f pos, @Nullable Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		dest.m00 = 1;
		dest.m01 = 0;
		dest.m02 = 0;
		dest.m03 = 0;
		dest.m10 = 0;
		dest.m11 = 1;
		dest.m12 = 0;
		dest.m13 = 0;
		dest.m20 = 0;
		dest.m21 = 0;
		dest.m22 = 1;
		dest.m23 = 0;
		dest.m30 = pos.x;
		dest.m31 = pos.y;
		dest.m32 = pos.z;
		dest.m33 = 1;
		return dest;
	}

	/**
	 * @see #setTranslation(Vector4f, Matrix4f)
	 * @param pos
	 * @return
	 */
	public Matrix4f setTranslation(Vector4f pos) {
		return setTranslation(pos, this);
	}

	/**
	 * Set a matrix to be a scale matrix
	 * @param scale The amount of scaling
	 * @param dest The matrix to set
	 * @return The destination matrix set to be a scale matrix, if it was null it creates a new matrix
	 */
	public static Matrix4f setScale(Vector4f scale, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		dest.m00 = scale.x;
		dest.m01 = 0;
		dest.m02 = 0;
		dest.m03 = 0;
		dest.m10 = 0;
		dest.m11 = scale.y;
		dest.m12 = 0;
		dest.m13 = 0;
		dest.m20 = 0;
		dest.m21 = 0;
		dest.m22 = scale.z;
		dest.m23 = 0;
		dest.m30 = 0;
		dest.m31 = 0;
		dest.m32 = 0;
		dest.m33 = 1;
		return dest;
	}

	/**
	 * @see #setScale(Vector4f, Matrix4f)
	 * @param scale
	 * @return
	 */
	public Matrix4f setScale(Vector4f scale) {
		return setScale(scale, this);
	}

	/**
	 * Sets a rotation matrix
	 * @param right the right vector
	 * @param up the up vector
	 * @param forward the forward vector
	 * @param dest the destination matrix
	 * @return The destination matrix set to be a rotation matrix, if it was null it creates a new matrix
	 */
	public static Matrix4f setRotation(Vector4f right, Vector4f up, Vector4f forward, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		dest.m00 = right.x;
		dest.m01 = up.x;
		dest.m02 = forward.x;
		dest.m03 = 0;
		dest.m10 = right.y;
		dest.m11 = up.y;
		dest.m12 = forward.y;
		dest.m13 = 0;
		dest.m20 = right.z;
		dest.m21 = up.z;
		dest.m22 = forward.z;
		dest.m23 = 0;
		dest.m30 = 0;
		dest.m31 = 0;
		dest.m32 = 0;
		dest.m33 = 1;
		return dest;
	}

	/**
	 * @see #setRotation(Vector4f, Vector4f, Vector4f, Matrix4f)
	 * @param right
	 * @param up
	 * @param forward
	 * @return
	 */
	public Matrix4f setRotation(Vector4f right, Vector4f up, Vector4f forward) {
		return setRotation(right, up, forward, this);
	}

	/**
	 * Sets a rotation matrix
	 * @param rot The angles to rotate by
	 * @param dest the destination matrix
	 * @return The destination matrix set to be a rotation matrix, if it was null it creates a new matrix
	 */
	public synchronized static Matrix4f setRotation(Vector4f rot, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		else
			dest.setIdentity();

		internal.rotX(rot.x);
		dest.mul(internal);
		internal.rotY(rot.y);
		dest.mul(internal);
		internal.rotZ(rot.z);
		dest.mul(internal);
		return dest;
	}

	/**
	 * @see #setRotation(Vector4f, Matrix4f)
	 * @param rot
	 * @return
	 */
	public Matrix4f setRotation(Vector4f rot) {
		return setRotation(rot, this);
	}

	/**
	 * Create an orthographic projection matrix
	 * @param left the left plane
	 * @param right the right plane
	 * @param bottom the bottom plane
	 * @param top the top plane
	 * @param near the near plane
	 * @param far the far plane
	 * @param dest The matrix to set
	 * @return The destination matrix set to be an orthographic projection matrix, if it was null it creates a new matrix
	 */
	public static Matrix4f setOrthographic(float left, float right, float bottom, float top, float near, float far, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		float width = right - left;
		float height = top - bottom;
		float length = far - near;

		dest.m00 = 2 / width;
		dest.m01 = 0;
		dest.m02 = 0;
		dest.m03 = 0;
		dest.m10 = 0;
		dest.m11 = 2 / height;
		dest.m12 = 0;
		dest.m13 = 0;
		dest.m20 = 0;
		dest.m21 = 0;
		dest.m22 = -2 / length;
		dest.m23 = 0;
		dest.m30 = (right + left) / -width;
		dest.m31 = (top + bottom) / -height;
		dest.m32 = (far + near) / -length;
		dest.m33 = 1;
		return dest;
	}

	/**
	 * @see #setOrthographic(float, float, float, float, float, float, Matrix4f)
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 * @return
	 */
	public Matrix4f setOrthographic(float left, float right, float bottom, float top, float near, float far) {
		return setOrthographic(left, right, bottom, top, near, far, this);
	}

	/**
	 * Create an perspective projection matrix
	 * @param fov the vertical field of view
	 * @param aspect the aspect ration
	 * @param near the near plane
	 * @param far the far plane
	 * @param dest The matrix to set
	 * @return The destination matrix set to be an perspective projection matrix, if it was null it creates a new matrix
	 */
	public static Matrix4f setPerspective(float fov, float aspect, float near, float far, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();

		float tanHalfFOV = (float) Math.tan(fov / 2);

		float length = far - near;

		dest.m00 = 1.0f / (tanHalfFOV * aspect);
		dest.m10 = 0;
		dest.m20 = 0;
		dest.m30 = 0;
		dest.m01 = 0;
		dest.m11 = 1.0f / tanHalfFOV;
		dest.m21 = 0;
		dest.m31 = 0;
		dest.m02 = 0;
		dest.m12 = 0;
		dest.m22 = -((near + far) / length);
		dest.m32 = -((2 * far * near) / length);
		dest.m03 = 0;
		dest.m13 = 0;
		dest.m23 = -1;
		dest.m33 = 0;

		return dest;
	}

	/**
	 * @see #setPerspective(float, float, float, float, Matrix4f)
	 * @param fov
	 * @param aspect
	 * @param near
	 * @param far
	 * @return
	 */
	public Matrix4f setPerspective(float fov, float aspect, float near, float far) {
		return setPerspective(fov, aspect, near, far, this);
	}

	/**
	 * /**
	 * Sets a transformation matrix
	 * @param scale The amount to scale by
	 * @param rot The quaternion to rotate by
	 * @param dest the destination matrix
	 * @param pos The amount to translate by
	 * @return The destination matrix set to be a transformation matrix, if it was null it creates a new matrix
	 */
	public static synchronized Matrix4f setTransformation(Vector4f scale, Quaternion rot, Vector4f pos, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		else
			dest.setIdentity();

		dest.setTranslation(pos);
		dest.mul(rot.toRotationMatrix(internal));
		dest.mul(internal.setScale(scale));
		return dest;
	}

	/**
	 * @see #setTransformation(Vector4f, Quaternion, Vector4f, Matrix4f)
	 * @param scale
	 * @param rot
	 * @param pos
	 * @return
	 */
	public synchronized Matrix4f setTransformation(Vector4f scale, Quaternion rot, Vector4f pos) {
		return setTransformation(scale, rot, pos, this);
	}

	/**
	 * Transform a vector
	 * @param src The vector to transform
	 * @param operand The matrix to transform by
	 * @param dest The destination vector
	 * @return The destination vector or if it is null, a new vector
	 */
	public static Vector4f transform(Vector4f src, Matrix4f operand, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		float x = src.x * operand.m00 + src.y * operand.m10 + src.z * operand.m20 + src.w * operand.m30;
		float y = src.x * operand.m01 + src.y * operand.m11 + src.z * operand.m21 + src.w * operand.m31;
		float z = src.x * operand.m02 + src.y * operand.m12 + src.z * operand.m22 + src.w * operand.m32;
		float w = src.x * operand.m03 + src.y * operand.m13 + src.z * operand.m23 + src.w * operand.m33;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;
		return dest;
	}

	/**
	 * @see #transform(Vector4f, Matrix4f, Vector4f)
	 * @param src
	 * @param operand
	 * @return
	 */
	public static Vector4f transform(Vector4f src, Matrix4f operand) {
		return transform(src, operand, src);
	}

	/**
	 * @see #transform(Vector4f, Matrix4f, Vector4f)
	 * @param src
	 * @param dest
	 * @return
	 */
	public Vector4f transform(Vector4f src, Vector4f dest) {
		return transform(src, this, dest);
	}

	/**
	 * @see #transform(Vector4f, Matrix4f, Vector4f)
	 * @param src
	 * @return
	 */
	public Vector4f transform(Vector4f src) {
		return transform(src, this, src);
	}

	private void rotX(float angle) {
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);

		m00 = 1;
		m01 = 0;
		m02 = 0;
		m03 = 0;
		m10 = 0;
		m11 = c;
		m12 = s;
		m13 = 0;
		m20 = 0;
		m21 = -s;
		m22 = c;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 1;
	}

	private void rotY(float angle) {
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);

		m00 = c;
		m01 = 0;
		m02 = -s;
		m03 = 0;
		m10 = 0;
		m11 = 1;
		m12 = 0;
		m13 = 0;
		m20 = s;
		m21 = 0;
		m22 = c;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 1;
	}

	private void rotZ(float angle) {
		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);

		m00 = c;
		m01 = s;
		m02 = 0;
		m03 = 0;
		m10 = -s;
		m11 = c;
		m12 = 0;
		m13 = 0;
		m20 = 0;
		m21 = 0;
		m22 = 1;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 1;
	}

	/**
	 * Rigth the matrix to a FloatBuffer
	 * @param buf the buffer to write to
	 * @return the buffer
	 * @see java.nio.FloatBuffer
	 */
	public FloatBuffer toFloatBuffer(FloatBuffer buf) {
		buf.put(m00);
		buf.put(m10);
		buf.put(m20);
		buf.put(m30);

		buf.put(m01);
		buf.put(m11);
		buf.put(m21);
		buf.put(m31);

		buf.put(m02);
		buf.put(m12);
		buf.put(m22);
		buf.put(m32);

		buf.put(m03);
		buf.put(m13);
		buf.put(m23);
		buf.put(m33);

		buf.flip();
		return buf;
	}

	/**
	 * Convert the matrix to a string
	 * @return The matrix in string form
	 */
	@Override
	public String toString() {
		return String.format("%n%+04f %+04f %+04f %+04f%n%+04f %+04f %+04f %+04f%n%+04f %+04f %+04f %+04f%n%+04f %+04f %+04f %+04f%n",
				m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33);
	}

	/**
	 * Set the matix to be a copy of another matrix
	 * @param matrix the matrix to copy
	 */
	public void set(Matrix4f matrix) {
		this.m00 = matrix.m00;
		this.m01 = matrix.m01;
		this.m02 = matrix.m02;
		this.m03 = matrix.m03;
		this.m10 = matrix.m10;
		this.m11 = matrix.m11;
		this.m12 = matrix.m12;
		this.m13 = matrix.m13;
		this.m20 = matrix.m20;
		this.m21 = matrix.m21;
		this.m22 = matrix.m22;
		this.m23 = matrix.m23;
		this.m30 = matrix.m30;
		this.m31 = matrix.m31;
		this.m32 = matrix.m32;
		this.m33 = matrix.m33;
	}

	/**
	 * Get the amount of translation the matrix applies
	 * @param dest The destination vector
	 * @return The destination vector or a new vector if it was null
	 */
	public Vector4f getPos(Vector4f dest) {
		return transform(Vector4f.ZERO, dest);
	}
}
