package nullEngine.object.component.physics;

import com.bulletphysics.collision.shapes.CollisionShape;

public class Collider {
	private CollisionShape collisionShape;

	public CollisionShape getCollisionShape() {
		return collisionShape;
	}

	protected void setCollisionShape(CollisionShape collisionShape) {
		this.collisionShape = collisionShape;
	}
}
