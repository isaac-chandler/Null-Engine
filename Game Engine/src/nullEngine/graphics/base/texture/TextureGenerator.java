package nullEngine.graphics.base.texture;

import math.Vector4f;
import nullEngine.graphics.base.Color;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class TextureGenerator {

	private static final HashMap<Integer, Integer> coloredTextures = new HashMap<Integer, Integer>();

	public static final Texture2D WHITE = genColored(255, 255, 255, 255);

	public static Texture2D genColored(Vector4f color) {
		return genColored((int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255), (int) (color.w * 255));
	}

	public static Texture2D genColored(Color color) {
		if (coloredTextures.get(color.getValue()) != null) {
			return new Texture2D(coloredTextures.get(color.getValue()));
		} else {

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

			coloredTextures.put(color.getValue(), id);

			return new Texture2D(id);
		}
	}

	public static Texture2D genColored(int r, int g, int b, int a) {
		return genColored(new Color((byte) r, (byte) g, (byte) b, (byte) a));
	}
}
