package nullEngine.managing;

import org.lwjgl.opengl.GL11;

/**
 * A resource to be used so textures don't have to be loaded multiple times
 */
public class TextureResouce extends ManagedResource {

	private static final String TYPE = "texture";

	private int textureID;

	/**
	 * Create a new texture resource
	 * @param name The name of the texture that was loaded
	 * @param textureID The texture id to be used
	 */
	public TextureResouce(String name, int textureID) {
		super(name, TYPE);
		this.textureID = textureID;
	}

	/**
	 * Create a new texture resource
	 * @param textureID The texture id to be used
	 */
	public TextureResouce(int textureID) {
		super(":" + textureID, TYPE);
		this.textureID = textureID;
	}

	/**
	 * Delete this texture resource
	 * @return <code>true</code>
	 */
	@Override
	public boolean delete() {
		GL11.glDeleteTextures(textureID);
		return true;
	}

	/**
	 * Get the OpenGL texture id of this resoucre
	 * @return The texture id
	 */
	public int getTextureID() {
		return textureID;
	}
}
