package nullEngine.graphics.postfx;

import math.Matrix4f;
import nullEngine.input.PostResizeEvent;

/**
 * Uses a plain texture as  the output
 */
public class TextureOutput implements PostFXOutput {
	private int textureID;

	/**
	 * Create a new texture output
	 * @param textureID The texture id
	 */
	public TextureOutput(int textureID) {
		this.textureID = textureID;
	}

	/**
	 * Set the texture id
	 * @param textureID The new texture id
	 */
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	/**
	 * Dose nothing
	 */
	@Override
	public void preRender() {
	}

	/**
	 * Does nothing
	 * @param viewMatrix The view matrix
	 */
	@Override
	public void render(Matrix4f viewMatrix) {
	}

	/**
	 * Get the texture id
	 * @return The texture id
	 */
	@Override
	public int getTextureID() {
		return textureID;
	}

	/**
	 * Does nothing
	 */
	@Override
	public void preResize() {
	}

	/**
	 * Does nothing
	 * @param event The event
	 */
	@Override
	public void postResize(PostResizeEvent event) {
	}
}
