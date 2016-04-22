package nullEngine.gl.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture2D {
	private int id;

	public Texture2D(int id) {
		this.id = id;
	}

	public void bind() {
		bind(0);
	}

	public void bind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public static void unbind() {
		unbind(0);
	}

	public static void unbind(int i) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void delete() {
		GL11.glDeleteTextures(id);
	}

	public Object getID() {
		return id;
	}
}
