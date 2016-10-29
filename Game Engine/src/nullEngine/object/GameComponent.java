package nullEngine.object;

import nullEngine.gl.renderer.Renderer;
import nullEngine.input.*;
import util.BitFieldInt;

public abstract class GameComponent extends EventAdapter {

	private GameObject parent;
	public boolean enabled = true;

	public void init(GameObject parent) {
		this.parent = parent;
	}

	public GameObject getParent() {
		return parent;
	}

	public abstract void render(Renderer renderer, GameObject object, BitFieldInt flags);

	public abstract void update(double delta, GameObject object);

	public void preRender() {

	}

	public void postUpdate() {

	}
}
