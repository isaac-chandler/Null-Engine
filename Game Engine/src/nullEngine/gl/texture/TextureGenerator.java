package nullEngine.gl.texture;

import math.Vector4f;
import nullEngine.gl.Color;
import nullEngine.managing.ResourceManager;
import nullEngine.managing.TextureResouce;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class TextureGenerator {
	public static final Texture2D WHITE = genColored(255, 255, 255, 255);

	public static Texture2D genColored(Vector4f color, boolean forceUnique) {
		return genColored((int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255), (int) (color.w * 255), forceUnique);
	}

	public static Texture2D genColored(Vector4f color) {
		return genColored((int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255), (int) (color.w * 255), false);
	}

	public static Texture2D genColored(Color color, boolean forceUnique) {
		TextureResouce resource;
		if (forceUnique || (resource = (TextureResouce) ResourceManager.getResource("texture:" + ":rgba" + color.getValue())) == null) {
			ByteBuffer buf = BufferUtils.createByteBuffer(16);

			for (int i = 0; i < 4; i++) {
				buf.put(color.r);
				buf.put(color.g);
				buf.put(color.b);
				buf.put(color.a);
			}

			buf.flip();


			int id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 2, 2, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			resource = forceUnique ? new TextureResouce(id) : new TextureResouce(":rgba" + color.getValue(), id);
		}
		resource.addReference();

		return new Texture2D(resource);
	}

	public static Texture2D genColored(Color color) {
		return genColored(color, false);
	}

	public static Texture2D genColored(int r, int g, int b, int a, boolean forceUnique) {
		return genColored(new Color((byte) r, (byte) g, (byte) b, (byte) a), forceUnique);
	}

	public static Texture2D genColored(int r, int g, int b, int a) {
		return genColored(new Color((byte) r, (byte) g, (byte) b, (byte) a), false);
	}
}
