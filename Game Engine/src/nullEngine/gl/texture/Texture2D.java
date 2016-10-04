package nullEngine.gl.texture;

import nullEngine.managing.TextureResouce;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture2D {
	private final TextureResouce resource;

	public Texture2D(TextureResouce resource) {
		this.resource = resource;
	}

	public void bind() {
		bind(0);
	}

	public void bind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, resource.textureID);
	}

	public static void unbind() {
		unbind(0);
	}

	public static void unbind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void dispose() {
		resource.dispose();
	}

	public int getID() {
		return resource.textureID;
	}
}
