package nullEngine.object.component.physics;

import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.sun.istack.internal.Nullable;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class RigidBodyComponent extends GameComponent {

	private RigidBody rigidBody;
	private MotionState motionState;
	private Transform transform = new Transform();
	private Matrix4f matrix = new Matrix4f();
	private Vector3f pos = new Vector3f();
	private Quat4f rot = new Quat4f();
	private boolean added;

	public RigidBodyComponent(Collider collider) {
		MotionState motionState = new DefaultMotionState();
		CompoundShape shape = new CompoundShape();

	}

	/**
	 * Render this component
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 * @see Renderer
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {

	}

	/**
	 * Update this component
	 *
	 * @param physics
	 * @param object  The object this component is attached to
	 * @param delta   The time since update was last called
	 */
	@Override
	public void update(@Nullable PhysicsEngine physics, GameObject object, double delta) {
		if (!added && isEnabled()) {
			physics.add(this);
			physics.add(this);
		}
	}

	public RigidBody getRigidBody() {
		return rigidBody;
	}
}
