package nullEngine.graphics.postfx;

import math.Matrix4f;
import nullEngine.input.PostResizeEvent;

/**
 * The output of a postfx
 */
public interface PostFXOutput {

	/**
	 * Called before a render
	 */
	void preRender();

	/**
	 * Render this output
	 * @param viewMatrix The view matrix
	 */
	void render(Matrix4f viewMatrix);

	/**
	 * Get the texture id of this output
	 * @return The texture id
	 */
	int getTextureID();

	/**
	 * Called before the window resizes
	 */
	void preResize();

	/**
	 * Called after the window resizes
	 * @param event The event
	 */
	void postResize(PostResizeEvent event);
}
