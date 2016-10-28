package math;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.concurrent.locks.ReentrantLock;

public class Matrix4f implements Serializable {
	public static final Matrix4f IDENTITY = new Matrix4f();
	private static final Matrix4f internal = new Matrix4f();
	private static final ReentrantLock internalLock = new ReentrantLock();

	public float
			m00 = 1, m01 = 0, m02 = 0, m03 = 0,
			m10 = 0, m11 = 1, m12 = 0, m13 = 0,
			m20 = 0, m21 = 0, m22 = 1, m23 = 0,
			m30 = 0, m31 = 0, m32 = 0, m33 = 1;

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

	public Matrix4f() {}

	public static Matrix4f mul(Matrix4f left, Matrix4f right, Matrix4f dest) {
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

	public Matrix4f mul(Matrix4f right, Matrix4f dest) {
		return mul(this, right, dest);
	}

	public Matrix4f mul(Matrix4f right) {
		return mul(this, right, this);
	}

	public static Matrix4f setIdentity(Matrix4f dest) {
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

	public Matrix4f setIdentity() {
		return setIdentity(this);
	}

	public static Matrix4f setTranslation(Vector4f pos, Matrix4f dest) {
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

	public Matrix4f setTranslation(Vector4f pos) {
		return setTranslation(pos, this);
	}

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

	public Matrix4f setScale(Vector4f scale) {
		return setScale(scale, this);
	}

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

	public Matrix4f setRotation(Vector4f right, Vector4f up, Vector4f forward) {
		return setRotation(right, up, forward, this);
	}

	public synchronized static Matrix4f setRotation(Vector4f rot, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		else
			dest.setIdentity();

		internalLock.lock();
		internal.rotX(rot.x);
		dest.mul(internal);
		internal.rotY(rot.y);
		dest.mul(internal);
		internal.rotZ(rot.z);
		dest.mul(internal);
		internalLock.unlock();
		return dest;
	}

	public Matrix4f setRotation(Vector4f rot) {
		return setRotation(rot, this);
	}

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

	public Matrix4f setOrthographic(float left, float right, float bottom, float top, float near, float far) {
		return setOrthographic(left, right, bottom, top, near, far, this);
	}

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

	public Matrix4f setPerspective(float fov, float aspect, float near, float far) {
		return setPerspective(fov, aspect, near, far, this);
	}

	public static synchronized Matrix4f setTransformation(Vector4f scale, Quaternion rot, Vector4f pos, Matrix4f dest) {
		if (dest == null)
			dest = new Matrix4f();
		else
			dest.setIdentity();

		dest.setTranslation(pos);
		internalLock.lock();
		dest.mul(rot.toRotationMatrix(internal));
		dest.mul(internal.setScale(scale));
		internalLock.unlock();
		return dest;
	}

	public synchronized Matrix4f setTransformation(Vector4f scale, Quaternion rot, Vector4f pos) {
		return setTransformation(scale, rot, pos, this);
	}

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

	public static Vector4f transform(Vector4f src, Matrix4f operand) {
		return transform(src, operand, src);
	}

	public Vector4f transform(Vector4f src, Vector4f dest) {
		return transform(src, this, dest);
	}

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

	@Override
	public String toString() {
		return String.format("%n%+04f %+04f %+04f %+04f%n%+04f %+04f %+04f %+04f%n%+04f %+04f %+04f %+04f%n%+04f %+04f %+04f %+04f%n",
				m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33);
	}

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
}
