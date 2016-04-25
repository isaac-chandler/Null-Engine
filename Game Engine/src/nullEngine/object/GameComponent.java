package nullEngine.object;

import nullEngine.gl.renderer.Renderer;
import nullEngine.input.*;

public abstract class GameComponent implements EventListener {

	private GameObject parent;

	public void init(GameObject parent) {
		this.parent = parent;
	}

	public GameObject getParent() {
		return parent;
	}

	public abstract void render(Renderer renderer, GameObject object);

	public abstract void update(float delta, GameObject object);

	@Override
	public boolean keyRepeated(KeyEvent event) {
		return false;
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		return false;
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		return false;
	}

	@Override
	public boolean mousePressed(MouseEvent event) {
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent event) {
		return false;
	}

	@Override
	public boolean mouseScrolled(MouseEvent event) {
		return false;
	}

	@Override
	public boolean mouseMoved(MouseEvent event) {
		return false;
	}

	@Override
	public boolean charTyped(CharEvent event) {
		return false;
	}

	@Override
	public void postResize(ResizeEvent event) {

	}

	@Override
	public void preResize() {

	}
}
