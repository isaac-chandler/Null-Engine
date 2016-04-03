package nullEngine.object.component;

import math.Matrix4f;
import math.Quaternion;
import math.Vector4f;
import nullEngine.gl.Renderer;
import nullEngine.input.Input;
import nullEngine.input.KeyEvent;
import nullEngine.input.MouseEvent;
import nullEngine.object.GameObject;
import nullEngine.util.logs.Logs;

public class FirstPersonCamera extends Camera {

	private static final float MOVEMENT_SPEED = 10;
	private Matrix4f viewMatrix = new Matrix4f();
	private Quaternion rotation = new Quaternion(0, 0, 0, 1);

	@Override
	public void init(GameObject parent) {
		super.init(parent);
		updateMatrix();
	}

	private Quaternion temp = new Quaternion();

	public Matrix4f updateMatrix() {
		getParent().getTransform().getWorldRot().mul(rotation, (Quaternion) null).toRotationMatrix(viewMatrix);
		viewMatrix.mul(Matrix4f.setTranslation(getParent().getTransform().getWorldPos(), null));
		return viewMatrix;
	}

	@Override
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public void render(Renderer renderer, GameObject object) {

	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		Quaternion.mul(rotation, new Quaternion((float) Math.toRadians(event.x / -1f), Vector4f.UP), rotation);
		Quaternion.mul(rotation, new Quaternion((float) Math.toRadians(event.y / 1f), rotation.getRight(null)), temp);

		float pitch = (float) Math.toDegrees(Math.atan2(2 * temp.x * temp.w - 2 * temp.y * temp.z, 1 - 2 * temp.x * temp.x - 2 * temp.z * temp.z));

		if ((pitch < -95 && pitch > -180) || (pitch < 180 && pitch > 95)) {
			rotation.x = temp.x;
			rotation.y = temp.y;
			rotation.z = temp.z;
			rotation.w = temp.w;
		}

		updateMatrix();
		return true;
	}

	private static final Vector4f Y_ONLY = new Vector4f(0, 1, 0);
	private static final Vector4f NO_Y = new Vector4f(1, 0, 1);

	@Override
	public void update(float delta, GameObject object) {
		Vector4f motion = new Vector4f();

		if (Input.keyPressed(Input.KEY_W)) {
			Vector4f.add(motion, rotation.getForward(null).mul(NO_Y).normalize().mul(MOVEMENT_SPEED * delta), motion);
		}
		if (Input.keyPressed(Input.KEY_S)) {
			Vector4f.add(motion, rotation.getForward(null).mul(NO_Y).normalize().mul(-MOVEMENT_SPEED * delta), motion);
		}

		if (Input.keyPressed(Input.KEY_D)) {
			Vector4f.add(motion, rotation.getRight(null).mul(NO_Y).normalize().mul(-MOVEMENT_SPEED * delta), motion);
		}
		if (Input.keyPressed(Input.KEY_A)) {
			Vector4f.add(motion, rotation.getRight(null).mul(NO_Y).normalize().mul(MOVEMENT_SPEED * delta), motion);
		}

		if (Input.keyPressed(Input.KEY_SPACE)) {
			Vector4f.add(motion, rotation.getUp(null).mul(Y_ONLY).normalize().mul(MOVEMENT_SPEED * delta), motion);
		}
		if (Input.keyPressed(Input.KEY_LEFT_SHIFT)) {
			Vector4f.add(motion, rotation.getUp(null).mul(Y_ONLY).normalize().mul(-MOVEMENT_SPEED * delta), motion);
		}

		if (!motion.isZero()) {
			object.getTransform().increasePos(motion);
			updateMatrix();
		}
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (event.key == Input.KEY_P) {
			double pitch = Math.toDegrees(Math.atan2(2 * rotation.x * rotation.w - 2 * rotation.y * rotation.z, 1 - 2 * rotation.x * rotation.x - 2 * rotation.z * rotation.z));
			Logs.d(pitch);
		}
		return false;
	}
}
