package nullEngine.object;

import math.Matrix4f;
import math.Quaternion;
import math.Vector4f;

public class Transform {
	private Transform parent;

	private Vector4f pos = new Vector4f(0, 0, 0);
	private Quaternion rot = new Quaternion(0, 0, 0, 1);
	private Vector4f scale = new Vector4f(1, 1, 1);

	private Matrix4f matrix = new Matrix4f();

	public Transform() {
		matrix.setIdentity();
	}

	public void setParent(Transform parent) {
		this.parent = parent;
	}

	public Vector4f getPos() {
		return pos;
	}

	public void setPos(Vector4f pos) {
		this.pos = pos;
		updateMatrix();
	}

	public Quaternion getRot() {
		return rot;
	}

	public void setRot(Quaternion rot) {
		this.rot = rot;
		updateMatrix();
	}

	public Vector4f getScale() {
		return scale;
	}

	public void setScale(Vector4f scale) {
		this.scale = scale;
		updateMatrix();
	}

	public void increasePos(Vector4f amt) {
		Vector4f.add(pos, amt, pos);
		updateMatrix();
	}

	public void increaseRot(Quaternion amt) {
		Quaternion.mul(rot, amt, rot);
		updateMatrix();
	}

	public void increaseScale(Vector4f amt) {
		Vector4f.add(scale, amt, scale);
		updateMatrix();
	}

	public Matrix4f getMatrix() {
		if (parent != null)
			return Matrix4f.mul(parent.getMatrix(), matrix, null);
		return matrix;
	}

	public Vector4f getWorldPos() {
		if (parent != null)
			return Matrix4f.transform(pos, parent.getMatrix(), null);
		return pos;
	}

	public Quaternion getWorldRot() {
		if (parent != null)
			return Quaternion.mul(parent.getWorldRot(), rot, null);
		return rot;
	}

	public void updateMatrix() {
		Matrix4f.setTransformation(scale, rot, pos, matrix);
	}
}
