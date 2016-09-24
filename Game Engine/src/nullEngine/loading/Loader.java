package nullEngine.loading;

import de.matthiasmann.twl.utils.PNGDecoder;
import nullEngine.control.Application;
import nullEngine.graphics.base.font.Font;
import nullEngine.graphics.base.font.Glyph;
import nullEngine.graphics.base.framebuffer.Framebuffer3D;
import nullEngine.graphics.base.model.Model;
import nullEngine.graphics.base.texture.Texture2D;
import nullEngine.loading.model.NLMLoader;
import nullEngine.loading.model.OBJLoader;
import nullEngine.object.component.HeightMap;
import nullEngine.util.Buffers;
import nullEngine.util.logs.Logs;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {

	private Application application;
	private GLCapabilities capabilities;

	private float anisotropyAmount = 0;
	private boolean anisotropyEnabled = false;
	private float lodBias = 0;

	private ArrayList<Integer> vaos = new ArrayList<Integer>();
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	private ArrayList<Integer> textures = new ArrayList<Integer>();

	public Loader(Application application) {
		this.application = application;
		capabilities = application.getWindow().getGLCapabilities();
	}

	public void setAnisotropyEnabled(boolean anisotropyEnabled) {
		this.anisotropyEnabled = anisotropyEnabled;
	}

	public void setAnisotropyAmount(float anisotropyAmount) {
		if (capabilities.GL_EXT_texture_filter_anisotropic) {
			this.anisotropyAmount = Math.min(anisotropyAmount, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
		}
	}

	public void setLodBias(float lodBias) {
		this.lodBias = Math.min(GL11.glGetFloat(GL14.GL_MAX_TEXTURE_LOD_BIAS), lodBias);
	}

	private int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}

	private int dataToAttrib(int attrib, float[] data, int size) {
		FloatBuffer buf = Buffers.createBuffer(data);
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, size, GL11.GL_FLOAT, false, 0, 0);
		return vbo;
	}

	public int createVBO(float[] data) {
		FloatBuffer buf = Buffers.createBuffer(data);
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		return vbo;
	}

	private int indexBuffer(int[] indices) {
		IntBuffer buf = Buffers.createBuffer(indices);
		int ibo = GL15.glGenBuffers();
		vbos.add(ibo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		return ibo;
	}

	public Model loadModel(float[] vertices, float[] texCoords, float[] normals, int[] indices, int[] vertexCounts) {
		int vao = createVAO();
		int ibo = indexBuffer(indices);
		vaos.add(vao);
		int vertexVBO = dataToAttrib(0, vertices, 3);
		int texCoordVBO = dataToAttrib(1, texCoords, 2);
		int normalVBO = dataToAttrib(2, normals, 3);
		GL30.glBindVertexArray(0);

		int[] vertexOffsets = new int[vertexCounts.length];
		vertexOffsets[0] = 0;

		for (int i = 1; i < vertexCounts.length; i++) {
			vertexOffsets[i] = vertexCounts[i - 1] + vertexOffsets[i - 1];
		}

		float biggestRadius = 0;
		for (int i = 0; i < vertices.length / 3; i++) {
			float x = vertices[i * 3];
			float y = vertices[i * 3 + 1];
			float z = vertices[i * 3 + 2];
			float radius = x * x + y * y + z * z;
			if (radius > biggestRadius)
				biggestRadius = radius;
		}
		return new Model(vao, vertexCounts, vertexOffsets, ibo, vertexVBO, texCoordVBO, normalVBO, (float) Math.sqrt(biggestRadius));
	}

	public Model loadModel(int vertexVBO, float radius, int texCoordVBO, int normalVBO, int[] indices) {
		int vao = createVAO();
		int ibo = indexBuffer(indices);
		vaos.add(vao);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVBO);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordVBO);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalVBO);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
		GL30.glBindVertexArray(0);

		return new Model(vao, new int[]{indices.length}, new int[]{0}, ibo, vertexVBO, texCoordVBO, normalVBO, radius);
	}

	public Model loadModel(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
		return loadModel(vertices, texCoords, normals, indices, new int[]{indices.length});
	}

	public Model loadModel(String name) {
		name = name.lastIndexOf('/') < name.lastIndexOf('.') ? name : name + ".nlm";
		if (name.endsWith(".obj")) {
			return OBJLoader.loadModel(this, name);
		} else if (name.endsWith(".nlm")) {
			return NLMLoader.loadModel(this, name);
		}
		Logs.f(new FileFormatException("Unknown model format"));
		return null;
	}

	public float[] toFloatArray(ArrayList<Float> list) {
		float[] ret = new float[list.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public int[] toIntArray(ArrayList<Integer> list) {
		int[] ret = new int[list.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public int loadTexture(String file) throws IOException {
		return loadTextureCustomPath("res/textures/" + file);
	}

	private int loadTextureCustomPath(String file) throws IOException {
		PNGDecoder decoder = new PNGDecoder(ResourceLoader.getResource(file + ".png"));

		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lodBias);

		if (capabilities.GL_EXT_texture_filter_anisotropic && anisotropyEnabled) {
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, anisotropyAmount);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lodBias);
		}

		if (decoder.hasAlpha()) {
			ByteBuffer buf = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buf.flip();
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		} else {
			ByteBuffer buf = BufferUtils.createByteBuffer(3 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 3, PNGDecoder.Format.RGB);
			buf.flip();
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buf);
		}

		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		textures.add(texture);
		return texture;
	}

	public Font loadFont(String name, int desiredPadding) throws IOException {
		Scanner scanner = new Scanner(ResourceLoader.getResource("res/fonts/" + name + ".fnt"));

		String line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		String[] tokens = line.split(" ");
		if (!line.startsWith("info ") && tokens[10].startsWith("padding="))
			throw new FileFormatException("Not a valid font file");

		String[] padStr = tokens[10].substring(8).replaceAll("\\s", "").split(",");
		int[] padding = new int[padStr.length];
		for (int i = 0; i < padStr.length; i++) {
			padding[i] = Integer.parseInt(padStr[i]);
		}


		line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		if (!line.startsWith("common "))
			throw new FileFormatException("Not a valid font file");

		tokens = line.split(" ");
		if (!tokens[1].startsWith("lineHeight=") || !tokens[3].startsWith("scaleW=") || !tokens[4].startsWith("scaleH=") || !tokens[5].startsWith("pages="))
			throw new FileFormatException("Not a valid font file");

		float lineHeight = Integer.parseInt(tokens[1].substring(11)) - padding[0] - padding[1];
		float width = Integer.parseInt(tokens[3].substring(7));
		float height = Integer.parseInt(tokens[4].substring(7));
		int pages = Integer.parseInt(tokens[5].substring(6));

		if (pages != 1)
			throw new UnsupportedFeatureException("Multi page fonts not supported");

		line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		if (!line.startsWith("page "))
			throw new FileFormatException("Not a valid font file");

		tokens = line.split(" ");

		if (!tokens[2].startsWith("file=\"") || !tokens[2].endsWith(".png\""))
			throw new FileFormatException("Invlaid texture file");

		String textureFile = tokens[2].substring(6, tokens[2].length() - 5);
		if (name.contains("/")) {
			textureFile = name.substring(0, name.lastIndexOf("/") + 1) + textureFile;
		}

		Texture2D texture = new Texture2D(loadTextureCustomPath("res/fonts/" + textureFile));

		line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		if (!line.startsWith("chars "))
			throw new FileFormatException("Not a valid font file");

		tokens = line.split(" ");

		if (!tokens[1].startsWith("count="))
			throw new FileFormatException("Not a valid font file");

		int charCount = Integer.parseInt(tokens[1].substring(6));
		HashMap<Character, Glyph> glyphs = new HashMap<Character, Glyph>(charCount);
		for (int i = 0; i < charCount; i++) {
			line = scanner.nextLine();
			line = line.replaceAll("\\s+", " ");
			if (!line.startsWith("char "))
				throw new FileFormatException("Not a valid font file");

			tokens = line.split(" ");
			if (!tokens[1].startsWith("id=") || !tokens[2].startsWith("x=") || !tokens[3].startsWith("y=") ||
					!tokens[4].startsWith("width=") || !tokens[5].startsWith("height=") || !tokens[6].startsWith("xoffset=") ||
					!tokens[7].startsWith("yoffset=") || !tokens[8].startsWith("xadvance="))
				throw new FileFormatException("Not a valid font file");

			char character = (char) Short.parseShort(tokens[1].substring(3));

			Glyph glyph = new Glyph();

			glyph.texCoordX = Integer.parseInt(tokens[2].substring(2)) + padding[3];
			glyph.texCoordMaxY = (Integer.parseInt(tokens[3].substring(2)) - padding[0]);

			glyph.texCoordMaxX = glyph.texCoordX + (Integer.parseInt(tokens[4].substring(6)) - padding[1]);
			glyph.texCoordY = glyph.texCoordMaxY + (Integer.parseInt(tokens[5].substring(7)) + padding[2]);

			glyph.width = Integer.parseInt(tokens[4].substring(6)) - padding[3] - padding[2];
			glyph.height = Integer.parseInt(tokens[5].substring(7)) - padding[1] - padding[0];

			glyph.xOffset = Integer.parseInt(tokens[6].substring(8)) + padding[3];
			glyph.yOffset = lineHeight - (Integer.parseInt(tokens[7].substring(8)) + glyph.height + padding[0]);

			glyph.xAdvance = Integer.parseInt(tokens[8].substring(9)) - padding[2] - padding[3];

			glyph.texCoordX /= width;
			glyph.texCoordY /= height;
			glyph.texCoordMaxX /= width;
			glyph.texCoordMaxY /= height;

			glyph.width /= lineHeight;
			glyph.height /= lineHeight;
			glyph.xOffset /= lineHeight;
			glyph.yOffset /= lineHeight;
			glyph.xAdvance /= lineHeight;

			glyph.character = character;

			glyphs.put(character, glyph);
		}

		line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		if (!line.startsWith("kernings "))
			throw new FileFormatException("Not a valid font file");

		tokens = line.split(" ");

		if (!tokens[1].startsWith("count="))
			throw new FileFormatException("Not a valid font file");
		int kerningCount = Integer.parseInt(tokens[1].substring(6));

		for (int i = 0; i < kerningCount; i++) {
			line = scanner.nextLine();
			line = line.replaceAll("\\s+", " ");
			if (!line.startsWith("kerning "))
				throw new FileFormatException("Not a valid font file");

			tokens = line.split(" ");
			if (!tokens[1].startsWith("first=") || !tokens[2].startsWith("second=") || !tokens[3].startsWith("amount="))
				throw new FileFormatException("Not a valid font file");

			char first = (char) Short.parseShort(tokens[1].substring(6));
			char second = (char) Short.parseShort(tokens[2].substring(7));
			float amount = Integer.parseInt(tokens[3].substring(7)) / lineHeight;

			Glyph glyph = glyphs.get(first);
			glyph.kerning.put(second, amount);
		}

		return new Font(this, glyphs, texture, desiredPadding, width, lineHeight, (lineHeight - padding[0] - padding[1]) / lineHeight);
	}

	public void cleanup() {
		Logs.d("Loader cleaning up");
		for (int vbo : vbos)
			GL15.glDeleteBuffers(vbo);
		for (int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		for (int texture : textures)
			GL11.glDeleteTextures(texture);
	}

	public void preContextChange() {
		Logs.d("Cleaning up vertex arrays");
		for (int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		vaos.clear();

		Framebuffer3D.preContextChange();
	}

	public void postContextChange() {
		Model.contextChanged(vaos);
		Framebuffer3D.contextChanged();
	}


	public HeightMap generateHeightMap(String name, float maxHeight) throws IOException {
		return new HeightMap(this, ImageIO.read(ResourceLoader.getResource("res/textures/" + name + ".png")), maxHeight);
	}

	public void addTexture(int texture) {
		textures.add(texture);
	}
}
