package nullEngine.object.component;

import math.Matrix4f;
import math.Quaternion;
import math.Vector4f;
import nullEngine.gl.renderer.Renderer;
import nullEngine.input.Input;
import nullEngine.input.MouseEvent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class FlyCam extends Camera {

	private static final float WALK_SPEED = 1.5f;
	private static final float RUN_SPEED = 4;

	private boolean canMove = true;
	private boolean canRotate = true;

	private Matrix4f viewMatrix = new Matrix4f();

	@Override
	public void init(GameObject parent) {
		super.init(parent);
		updateMatrix();
	}

	private Quaternion temp = new Quaternion();

	public Matrix4f updateMatrix() {
		getParent().getTransform().getWorldRot().toRotationMatrix(viewMatrix);
		Matrix4f.mul(viewMatrix, Matrix4f.setTranslation(getParent().getTransform().getWorldPos().mul(-1, null), null), viewMatrix);
		return viewMatrix;
	}

	@Override
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {

	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		if (canRotate) {
			Quaternion rotation = getParent().getTransform().getRot();
			getParent().getTransform().increaseRot(new Quaternion((float) Math.toRadians(event.x / 1f), Vector4f.UP));
			Quaternion.mul(rotation, new Quaternion((float) Math.toRadians(event.y / -1f), rotation.getRight(null)), temp);

			float pitch = (float) Math.toDegrees(Math.atan2(2 * temp.x * temp.w - 2 * temp.y * temp.z, 1 - 2 * temp.x * temp.x - 2 * temp.z * temp.z));

			if (pitch > -85 && pitch < 85) {
				rotation.x = temp.x;
				rotation.y = temp.y;
				rotation.z = temp.z;
				rotation.w = temp.w;
				getParent().getTransform().setRot(rotation);
			}

			updateMatrix();
			return true;
		}
		return false;
	}

	private static final Vector4f Y_ONLY = new Vector4f(0, 1, 0, 0);
	private static final Vector4f NO_Y = new Vector4f(1, 0, 1, 0);

	@Override
	public void update(float delta, GameObject object) {
		if (canMove) {
			Vector4f motion = new Vector4f();
			Quaternion rotation = getParent().getTransform().getRot();

			float speed = Input.keyPressed(Input.KEY_TAB) ? RUN_SPEED : WALK_SPEED;

			if (Input.keyPressed(Input.KEY_W)) {
				Vector4f.add(motion, rotation.getForward(null).mul(NO_Y).normalize().mul(-speed * delta), motion);
			}
			if (Input.keyPressed(Input.KEY_S)) {
				Vector4f.add(motion, rotation.getForward(null).mul(NO_Y).normalize().mul(speed * delta), motion);
			}

			if (Input.keyPressed(Input.KEY_D)) {
				Vector4f.add(motion, rotation.getRight(null).mul(NO_Y).normalize().mul(speed * delta), motion);
			}
			if (Input.keyPressed(Input.KEY_A)) {
				Vector4f.add(motion, rotation.getRight(null).mul(NO_Y).normalize().mul(-speed * delta), motion);
			}

			if (Input.keyPressed(Input.KEY_SPACE)) {
				Vector4f.add(motion, rotation.getUp(null).mul(Y_ONLY).normalize().mul(speed * delta), motion);
			}
			if (Input.keyPressed(Input.KEY_LEFT_SHIFT)) {
				Vector4f.add(motion, rotation.getUp(null).mul(Y_ONLY).normalize().mul(-speed * delta), motion);
			}

			if (Input.keyPressed(Input.KEY_LEFT_CONTROL))
				motion.mul(10);

			if (!motion.isZero()) {
				object.getTransform().increasePos(motion);
				updateMatrix();
			}
		}
	}

	public boolean getCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public boolean getCanRotate() {
		return canRotate;
	}

	public void setCanRotate(boolean canRotate) {
		this.canRotate = canRotate;
	}
}
