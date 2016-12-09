package nullEngine.control.physics;

public abstract class PhysicsEngine {
	public enum Type {
		BASIC_JBULLET
	}

	public abstract void update(double delta);
}
