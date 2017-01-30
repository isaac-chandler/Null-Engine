package nullEngine.object.component.physics;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

//TODO
public class RigidBodyComponent extends GameComponent {

	private RigidBody rigidBody;
	private MotionState motionState;
	private Transform transform = new Transform();
	private Matrix4f matrix = new Matrix4f();
	private Vector3f pos = new Vector3f();
	private Quat4f rot = new Quat4f();

	public RigidBodyComponent(Collider collider) {
		MotionState motionState = new DefaultMotionState();
		CollisionShape shape = collider.getCollisionShape();


	}

	/**
	 * Render this component
	 *
	 * @param object   The object this component is attached to
	 * @see Renderer
	 */
	@Override
	public void render(GameObject object) {

	}

	/**
	 * Update this component
	 *  @param object  The object this component is attached to
	 * @param delta   The time since update was last called
	 */
	@Override
	public void update(GameObject object, double delta) {

	}

	public RigidBody getRigidBody() {
		return rigidBody;
	}
}
