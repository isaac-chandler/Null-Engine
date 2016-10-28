package nullEngine.object.component;

import math.Matrix4f;
import nullEngine.object.GameComponent;

import java.util.concurrent.locks.ReentrantLock;

public abstract class Camera extends GameComponent {

	protected Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f postUpdateMatrix = new Matrix4f();
	private Matrix4f preRenderMatrix = new Matrix4f();

	private ReentrantLock matrixLock = new ReentrantLock();

	public Matrix4f getViewMatrix() {
		return preRenderMatrix;
	}


	@Override
	public void postUpdate() {
		matrixLock.lock();
		postUpdateMatrix.set(viewMatrix);
		matrixLock.unlock();
	}

	@Override
	public void preRender() {
		matrixLock.lock();
		preRenderMatrix.set(postUpdateMatrix);
		matrixLock.unlock();
	}
}
