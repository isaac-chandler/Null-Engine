package nullEngine.object.component;

import nullEngine.gl.model.Quad;
import nullEngine.gl.shader.NormalGenShader;
import nullEngine.gl.texture.Texture2D;
import nullEngine.loading.FileFormatException;
import nullEngine.loading.Loader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class HeightMap {

	private BufferedImage map;
	private Texture2D heightMap;
	private Texture2D normalMap;
	private float maxHeight;

	public HeightMap(Loader loader, BufferedImage map, float maxHeight) throws FileFormatException {
		this.map = map;
		if (map.getHeight() != map.getWidth() || (map.getWidth() & (map.getWidth() - 1)) != 0) {
			throw new FileFormatException("Height map must be a power of 2 square");
		}
		this.maxHeight = maxHeight;
		heightMap = new Texture2D(genHeightMap(loader));
		normalMap = new Texture2D(genNormalMap(loader));
	}

	private int genHeightMap(Loader loader) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

		FloatBuffer buf = BufferUtils.createFloatBuffer(map.getWidth() * map.getHeight() * 3);

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				buf.put(getHeight(x, y));
				buf.put(0);
				buf.put(0);
			}
		}
		buf.flip();

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGB16F, map.getWidth(), map.getHeight(), 0, GL11.GL_RGB, GL11.GL_FLOAT, buf);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		loader.addTexture(texture);
		return texture;
	}

	private int genNormalMap(Loader loader) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, map.getWidth(), map.getHeight(), 0, GL11.GL_RGB, GL11.GL_FLOAT, (ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		int framebuffer = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, texture, 0);

		NormalGenShader.INSTANCE.bind();
		NormalGenShader.INSTANCE.updateUniforms(maxHeight, map.getWidth());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, heightMap.getID());
		Quad.get().render();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL30.glDeleteFramebuffers(framebuffer);

		loader.addTexture(texture);
		return texture;
	}

	private static final float MAX = 256 * 256 * 256;

	public float getHeight(int x, int y) {
		x %= map.getWidth();
		y %= map.getHeight();

		while(x < 0) {
			x += map.getWidth();
		}

		while(y < 0) {
			y += map.getHeight();
		}

		float height = map.getRGB(x, y);
		height += MAX / 2f;
		height /= MAX / 2f;
		return height * maxHeight;
	}

	public Texture2D getHeightMap() {
		return heightMap;
	}

	public Texture2D getNormalMap() {
		return normalMap;
	}

	public float getMaxHeight() {
		return maxHeight;
	}

	public int getResolution() {
		return map.getWidth();
	}
}
