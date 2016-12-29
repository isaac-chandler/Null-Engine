package nullEngine.object;

import math.Matrix4f;
import math.Quaternion;
import math.Vector4f;

/**
 * Stores a transform hierarchy
 */
public class Transform {
	private Transform parent;

	private Vector4f pos = new Vector4f(0, 0, 0);
	private Quaternion rot = new Quaternion(0, 0, 0, 1);
	private Vector4f scale = new Vector4f(1, 1, 1);

	private Matrix4f matrix = new Matrix4f();
	private GameObject object;

	/**
	 * Create a new transform
	 */
	public Transform(GameObject object) {
		this.object = object;
	}

	/**
	 * Set the parent of this transform in the hierarchy
	 *
	 * @param parent The parent
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Get the position relative to the parent
	 *
	 * @return The position
	 */
	public Vector4f getPos() {
		return pos;
	}

	/**
	 * Set the position relative to the parent
	 *
	 * @param pos The new position
	 */
	public void setPos(Vector4f pos) {
		this.pos = pos;
		updateMatrix();
	}

	/**
	 * Get the rotation relative to the parent
	 *
	 * @return The rotation
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Set the rotation relative to the parent
	 *
	 * @param rot The new rotation
	 */
	public void setRot(Quaternion rot) {
		this.rot = rot;
		updateMatrix();
	}

	/**
	 * Get the scale relative to the parent
	 *
	 * @return The scale
	 */
	public Vector4f getScale() {
		return scale;
	}

	/**
	 * Set the scale relative to the parent
	 *
	 * @param scale The scale
	 */
	public void setScale(Vector4f scale) {
		this.scale = scale;
		updateMatrix();
	}

	/**
	 * Increase the position relative to the parent
	 *
	 * @param amt The amount to increase by
	 */
	public void increasePos(Vector4f amt) {
		Vector4f.add(pos, amt, pos);
		updateMatrix();
	}

	/**
	 * Increase the rotation relative to the parent
	 *
	 * @param amt The amount to increase by
	 */
	public void increaseRot(Quaternion amt) {
		Quaternion.mul(rot, amt, rot);
		updateMatrix();
	}

	/**
	 * Increase the scale relative to the parent
	 *
	 * @param amt The amount to increase by
	 */
	public void increaseScale(Vector4f amt) {
		Vector4f.add(scale, amt, scale);
		updateMatrix();
	}

	/**
	 * Get the transformation matrix
	 *
	 * @return The transformation matrix
	 */
	public Matrix4f getMatrix() {
		if (parent != null)
			return Matrix4f.mul(parent.getMatrix(), matrix, null);
		return matrix;
	}

	/**
	 * Get the position relative to the world
	 *
	 * @return The position
	 */
	public Vector4f getWorldPos() {
		if (parent != null)
			return Matrix4f.transform(pos, parent.getMatrix(), null);
		return pos;
	}

	/**
	 * Get the Rotation relative to the world
	 *
	 * @return The rotation
	 */
	public Quaternion getWorldRot() {
		if (parent != null)
			return Quaternion.mul(parent.getWorldRot(), rot, null);
		return rot;
	}

	private void updateMatrix() {
		Matrix4f.setTransformation(scale, rot, pos, matrix);
		object.matrixUpdated();
	}
}
