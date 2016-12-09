package nullEngine.object.component.graphics.camera;

import math.Matrix4f;
import math.Quaternion;
import math.Vector4f;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.input.Input;
import nullEngine.input.MouseEvent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * A first person camera that can fly around
 */
public class FlyCam extends Camera {

	private static final float WALK_SPEED = 1.5f;
	private static final float RUN_MULTIPLIER = 2;

	private boolean canMove = true;
	private boolean canRotate = true;
	private float speed = WALK_SPEED;

	/**
	 * Setup this object
	 *
	 * @param parent This objects parent
	 */
	@Override
	public void init(GameObject parent) {
		super.init(parent);
		updateMatrix();
	}

	private Quaternion temp = new Quaternion();

	/**
	 * Update our matrix
	 */
	public void updateMatrix() {
		getObject().getTransform().getWorldRot().toRotationMatrix(viewMatrix);
		Matrix4f.mul(viewMatrix, Matrix4f.setTranslation(getObject().getTransform().getWorldPos().mul(-1, null), null), viewMatrix);
	}

	/**
	 * Render this component
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {

	}

	/**
	 * If camera rotation is enabled, rotate the camera
	 *
	 * @param event The event
	 * @return Wether the camera rotated
	 */
	@Override
	public boolean mouseMoved(MouseEvent event) {
		if (canRotate) {
			Quaternion rotation = getObject().getTransform().getRot();
			getObject().getTransform().increaseRot(new Quaternion((float) Math.toRadians(event.x), Vector4f.UP));
			Quaternion.mul(rotation, new Quaternion((float) Math.toRadians(-event.y), rotation.getRight(null)), temp);

			float pitch = (float) Math.toDegrees(Math.atan2(2 * temp.x * temp.w - 2 * temp.y * temp.z, 1 - 2 * temp.x * temp.x - 2 * temp.z * temp.z));

			if (pitch > -85 && pitch < 85) {
				rotation.x = temp.x;
				rotation.y = temp.y;
				rotation.z = temp.z;
				rotation.w = temp.w;
				getObject().getTransform().setRot(rotation);
			}

			updateMatrix();
			return true;
		}
		return false;
	}

	private static final Vector4f Y_ONLY = new Vector4f(0, 1, 0, 0);
	private static final Vector4f NO_Y = new Vector4f(1, 0, 1, 0);

	/**
	 * Update the camera and process the movement
	 * @param physics
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(PhysicsEngine physics, GameObject object, double delta) {
		if (canMove) {
			Vector4f motion = new Vector4f();
			Quaternion rotation = getObject().getTransform().getRot();

			float speed = Input.keyPressed(Input.KEY_TAB) ? this.speed * RUN_MULTIPLIER : this.speed;

			if (Input.keyPressed(Input.KEY_W)) {
				Vector4f.add(motion, rotation.getForward(null).mul(NO_Y).normalize().mul(-speed * (float) delta), motion);
			}
			if (Input.keyPressed(Input.KEY_S)) {
				Vector4f.add(motion, rotation.getForward(null).mul(NO_Y).normalize().mul(speed * (float) delta), motion);
			}

			if (Input.keyPressed(Input.KEY_D)) {
				Vector4f.add(motion, rotation.getRight(null).mul(NO_Y).normalize().mul(speed * (float) delta), motion);
			}
			if (Input.keyPressed(Input.KEY_A)) {
				Vector4f.add(motion, rotation.getRight(null).mul(NO_Y).normalize().mul(-speed * (float) delta), motion);
			}

			if (Input.keyPressed(Input.KEY_SPACE)) {
				Vector4f.add(motion, rotation.getUp(null).mul(Y_ONLY).normalize().mul(speed * (float) delta), motion);
			}
			if (Input.keyPressed(Input.KEY_LEFT_SHIFT)) {
				Vector4f.add(motion, rotation.getUp(null).mul(Y_ONLY).normalize().mul(-speed * (float) delta), motion);
			}

			if (!motion.isZero()) {
				object.getTransform().increasePos(motion);
				updateMatrix();
			}
		}
	}

	/**
	 * Wether this camera can move
	 *
	 * @return Wether this camera can move
	 */
	public boolean getCanMove() {
		return canMove;
	}

	/**
	 * Set wether this camera can move
	 *
	 * @param canMove Wether this camera can move
	 */
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	/**
	 * Wether this camera can rotate
	 *
	 * @return Wether this camera can rotate
	 */
	public boolean getCanRotate() {
		return canRotate;
	}

	/**
	 * Set ether this camera can rotate
	 *
	 * @param canRotate Wether this camera can rotate
	 */
	public void setCanRotate(boolean canRotate) {
		this.canRotate = canRotate;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
