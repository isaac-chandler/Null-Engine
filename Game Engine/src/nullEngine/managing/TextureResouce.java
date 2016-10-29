package nullEngine.managing;

import org.lwjgl.opengl.GL11;

public class TextureResouce extends ManagedResource {

	private static final String TYPE = "texture";

	public int textureID;

	public TextureResouce(String name, int textureID) {
		super(name, TYPE);
		this.textureID = textureID;
	}

	public TextureResouce(int textureID) {
		super(":" + textureID, TYPE);
		this.textureID = textureID;
	}

	@Override
	public boolean delete() {
		GL11.glDeleteTextures(textureID);
		return true;
	}
}
