package nullEngine.control.physics;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import nullEngine.object.GameComponent;
import nullEngine.object.component.physics.RigidBodyComponent;

import javax.vecmath.Vector3f;

public class PhysicsEngine {

	private BroadphaseInterface broadphase;
	private CollisionConfiguration collisionConfiguration;
	private CollisionDispatcher dispatcher;
	private ConstraintSolver solver;
	private DiscreteDynamicsWorld dynamicsWorld;

	public PhysicsEngine() {
		broadphase = new DbvtBroadphase();
		collisionConfiguration = new DefaultCollisionConfiguration();
		dispatcher = new CollisionDispatcher(collisionConfiguration);
		solver = new SequentialImpulseConstraintSolver();
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		dynamicsWorld.setGravity(new Vector3f(0, -9.81f, 0));
	}

	public void update(double delta) {

	}

	public void add(GameComponent component) {
		if (component instanceof RigidBodyComponent) {
			dynamicsWorld.addRigidBody(((RigidBodyComponent) component).getRigidBody());
		}
	}
}
