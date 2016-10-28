package nullEngine.input;

public interface EventListener {
	boolean keyRepeated(KeyEvent event);

	boolean keyPressed(KeyEvent event);

	boolean keyReleased(KeyEvent event);

	boolean mousePressed(MouseEvent event);

	boolean mouseReleased(MouseEvent event);

	boolean mouseScrolled(MouseEvent event);

	boolean mouseMoved(MouseEvent event);

	boolean charTyped(CharEvent event);

	void postResize(PostResizeEvent event);

	void preResize();
}
