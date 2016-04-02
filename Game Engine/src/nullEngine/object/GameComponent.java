package nullEngine.object;

import nullEngine.gl.Renderer;
import nullEngine.input.EventAdapter;

public abstract class GameComponent extends EventAdapter {

	private GameObject parent;

	public void init(GameObject parent) {
		this.parent = parent;
	}

	public GameObject getParent() {
		return parent;
	}

	public abstract void render(Renderer renderer, GameObject object);

	public abstract void update(float delta, GameObject object);
}
