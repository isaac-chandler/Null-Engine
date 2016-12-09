package nullEngine.graphics.texture;

import nullEngine.managing.TextureResouce;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * A 2D OpenGL texture
 */
public class Texture2D {
	private final TextureResouce resource;

	/**
	 * Create a new texture
	 * @param resource The texture resource
	 */
	public Texture2D(TextureResouce resource) {
		this.resource = resource;
	}

	/**
	 * Bind the texture to texture unit 0
	 */
	public void bind() {
		bind(0);
	}

	/**
	 * Bind this texture
	 * @param i The texture unit to bind to
	 */
	public void bind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, resource.getTextureID());
	}

	/**
	 * Unbind the texture from texture unit 0
	 */
	public static void unbind() {
		unbind(0);
	}

	/**
	 * Unbind the texture
	 * @param i The texture unit to unbind
	 */
	public static void unbind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Dispose the texture
	 */
	public void dispose() {
		resource.dispose();
	}

	/**
	 * Get the texture id
	 * @return The texture id
	 */
	public int getID() {
		return resource.getTextureID();
	}
}
