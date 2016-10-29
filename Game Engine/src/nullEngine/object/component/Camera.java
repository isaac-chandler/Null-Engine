package nullEngine.object.component;

import math.Matrix4f;
import nullEngine.object.GameComponent;

public abstract class Camera extends GameComponent {

	protected Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f postUpdateMatrix = new Matrix4f();
	private Matrix4f preRenderMatrix = new Matrix4f();

	public Matrix4f getViewMatrix() {
		return preRenderMatrix;
	}


	@Override
	public void postUpdate() {
		postUpdateMatrix.set(viewMatrix);
	}

	@Override
	public void preRender() {
		preRenderMatrix.set(postUpdateMatrix);
	}
}
