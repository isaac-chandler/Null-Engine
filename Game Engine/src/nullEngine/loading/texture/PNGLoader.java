package nullEngine.loading.texture;

import de.matthiasmann.twl.utils.PNGDecoder;
import nullEngine.gl.texture.Texture2D;
import nullEngine.loading.filesys.ResourceLoader;
import nullEngine.managing.ResourceManager;
import nullEngine.managing.TextureResouce;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.ByteBuffer;

public class PNGLoader {
	public static Texture2D loadTexture(String file, float lodBias, boolean anisotropyEnabled, float anisotropyAmount, boolean forceUnique) throws IOException {
		TextureResouce resource;
		if (forceUnique || (resource = (TextureResouce) ResourceManager.getResource("texture:" + file)) == null) {
			PNGDecoder decoder = new PNGDecoder(ResourceLoader.getResource(file));

			int texture = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lodBias);

			if (anisotropyEnabled) {
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, anisotropyAmount);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lodBias);
			}

			if (decoder.hasAlpha()) {
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
				ByteBuffer buf = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
				decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
				buf.flip();
				GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, decoder.getWidth(), decoder.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
			} else {
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
				ByteBuffer buf = BufferUtils.createByteBuffer(3 * decoder.getWidth() * decoder.getHeight());
				decoder.decode(buf, decoder.getWidth() * 3, PNGDecoder.Format.RGB);
				buf.flip();
				GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, decoder.getWidth(), decoder.getHeight(), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buf);
			}

			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			resource = forceUnique ? new TextureResouce(texture) : new TextureResouce(file, texture);
		}
		resource.addReference();

		return new Texture2D(resource);
	}
}
