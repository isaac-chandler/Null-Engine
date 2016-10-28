package nullEngine.object;

import nullEngine.gl.renderer.Renderer;
import nullEngine.input.*;
import util.BitFieldInt;

public abstract class GameComponent implements EventListener {

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
	public void postResize(PostResizeEvent event) {

	}

	@Override
	public void preResize() {

	}
}
