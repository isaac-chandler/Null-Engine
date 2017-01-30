package nullEngine.object.component.physics;

import com.bulletphysics.collision.shapes.StaticPlaneShape;
import math.Vector4f;

import javax.vecmath.Vector3f;

public class PlaneCollider extends Collider {

	public PlaneCollider(Vector4f normal, float buffer) {
		setCollisionShape(new StaticPlaneShape(new Vector3f(normal.x, normal.y, normal.z), buffer));
	}


}
