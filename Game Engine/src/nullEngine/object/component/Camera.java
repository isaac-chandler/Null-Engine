package nullEngine.object.component;

import math.Matrix4f;
import nullEngine.object.GameComponent;

public abstract class Camera extends GameComponent {
	public abstract Matrix4f getViewMatrix();
}
