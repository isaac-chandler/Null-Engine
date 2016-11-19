package nullEngine.loading;

import nullEngine.control.Application;
import nullEngine.exception.UnsupportedFeatureException;
import nullEngine.gl.buffer.IndexBuffer;
import nullEngine.gl.buffer.VertexBuffer;
import nullEngine.gl.font.Font;
import nullEngine.gl.font.Glyph;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.framebuffer.Framebuffer3D;
import nullEngine.gl.framebuffer.FramebufferDeferred;
import nullEngine.gl.framebuffer.FramebufferMousePick;
import nullEngine.gl.model.Model;
import nullEngine.gl.model.VertexAttribPointer;
import nullEngine.gl.texture.Texture2D;
import nullEngine.loading.filesys.FileFormatException;
import nullEngine.loading.filesys.ResourceLoader;
import nullEngine.loading.model.NLMLoader;
import nullEngine.loading.model.OBJLoader;
import nullEngine.loading.texture.PNGLoader;
import nullEngine.object.wrapper.HeightMap;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Class for loading resouces
 */
public class Loader {

	private GLCapabilities capabilities;

	private float anisotropyAmount = 1;
	private boolean anisotropyEnabled = false;
	private float lodBias = 0;

	private List<Integer> vaos = new ArrayList<>();

	/**
	 * Create a new Loader
	 *
	 * @param application The application this loader belongs to
	 */
	public Loader(Application application) {
		capabilities = application.getWindow().getGLCapabilities();
	}

	/**
	 * Set wether anisotropic filtering should be enabled
	 *
	 * @param anisotropyEnabled New value
	 */
	public void setAnisotropyEnabled(boolean anisotropyEnabled) {
		this.anisotropyEnabled = anisotropyEnabled;
	}

	/**
	 * Get wether anisotropic filtering is set to enabled
	 *
	 * @return Wether anisotropiv filtering is enabled
	 */
	public boolean isAnisotropyEnabled() {
		return anisotropyEnabled;
	}

	/**
	 * Get wether anisotropic filtering is supported
	 *
	 * @return Wether anisotropic filtering
	 * @see GLCapabilities#GL_EXT_texture_filter_anisotropic
	 */
	public boolean isAnisotropySupported() {
		return capabilities.GL_EXT_texture_filter_anisotropic;
	}

	/**
	 * Set the maximum amount of anisotropy for each texture
	 *
	 * @param anisotropyAmount The maximum amount of anisotropy
	 */
	public void setAnisotropyAmount(float anisotropyAmount) {
		this.anisotropyAmount = anisotropyAmount;
	}

	/**
	 * Get the maximum amount of anisotropy for each texture
	 *
	 * @return The maximum amount of anisotropy
	 */
	public float getAnisotropyAmount() {
		return anisotropyAmount;
	}

	/**
	 * Set the lod bias for textures
	 *
	 * @param lodBias The lod bias
	 */
	public void setTextureLodBias(float lodBias) {
		this.lodBias = lodBias;
	}

	/**
	 * Get the lod bias for textures
	 *
	 * @return The lod bias for textures
	 */
	public float getTextureLodBias() {
		return lodBias;
	}

	private int createVAO() {
		int vao = GL30.glGenVertexArrays();
		vaos.add(vao);
		return vao;
	}

	/**
	 * Load a model with multiple levels of detail
	 *
	 * @param vertices     The vertices
	 * @param texCoords    The texture coordinates
	 * @param normals      The normals
	 * @param indices      The indices
	 * @param vertexCounts The number of vertices in each level of detail
	 * @return The model that was created
	 */
	public Model loadModel(float[] vertices, float[] texCoords, float[] normals, int[] indices, int[] vertexCounts) {
		int vao = createVAO();
		IndexBuffer ibo = IndexBuffer.create(indices);
		VertexBuffer vertexVBO = VertexBuffer.create(vertices);
		VertexBuffer texCoordVBO = VertexBuffer.create(texCoords);
		VertexBuffer normalVBO = VertexBuffer.create(normals);

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
		return new Model(vao, vertexCounts, vertexOffsets, (float) Math.sqrt(biggestRadius), ibo,
				VertexAttribPointer.createVec3AttribPointer(vertexVBO), VertexAttribPointer.createVec2AttribPointer(texCoordVBO), VertexAttribPointer.createVec3AttribPointer(normalVBO));
	}

	/**
	 * Load a model from vertex buffers
	 *
	 * @param vertexVBO   The vertex buffer
	 * @param texCoordVBO The texture coordinate buffer
	 * @param normalVBO   The normal buffer
	 * @param ibo         The index buffer
	 * @param length      The number of indices in the index buffer
	 * @param radius      The distance from the farthest point to the origin of the model
	 * @return The model that was created
	 */
	public Model loadModel(VertexBuffer vertexVBO, VertexBuffer texCoordVBO, VertexBuffer normalVBO, IndexBuffer ibo, int length, float radius) {
		int vao = createVAO();

		return new Model(vao, new int[] {length}, new int[] {0}, radius, ibo,
				VertexAttribPointer.createVec3AttribPointer(vertexVBO), VertexAttribPointer.createVec2AttribPointer(texCoordVBO), VertexAttribPointer.createVec3AttribPointer(normalVBO));
	}

	/**
	 * Load a model with a single level of detail
	 *
	 * @param vertices  The vertices
	 * @param texCoords The texture coordinates
	 * @param normals   The normals
	 * @param indices   The indices
	 * @return The model that was created
	 */
	public Model loadModel(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
		return loadModel(vertices, texCoords, normals, indices, new int[] {indices.length});
	}

	/**
	 * Load a model from a file
	 *
	 * @param name The file to be loaded from in the folder <em>res/models</em>
	 *             <ul>
	 *             <li>If there is no extension or the extension is <em>.nlm</em> the model is loaded using the <a href="spec/Model_Format.html" target="_blank">NLM Model Format</a></li>
	 *             <li>If the extension is <em>.obj</em> the model is loaded using the OBJ model format</li>
	 *             </ul>
	 * @return The model that was loaded
	 */
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

	/**
	 * Load a PNG texture
	 *
	 * @param file The file without the .png extension in <em>res/textures</em> to load
	 * @return The texture that was laoded
	 * @throws IOException If the texture failed to load
	 */
	public Texture2D loadTexture(String file) throws IOException {
		return loadTextureCustomPath("res/textures/" + file, false);
	}

	/**
	 * Load a PNG texture
	 *
	 * @param file        The name of the file without the <em>.png</em> extension in <em>res/textures</em> to load
	 * @param forceUnique If <code>true</code> make sure the texture isn't just a new reference to a cached texture
	 * @return The texture that was loaded
	 * @throws IOException If the texture failed to load
	 */
	public Texture2D loadTexture(String file, boolean forceUnique) throws IOException {
		return loadTextureCustomPath("res/textures/" + file, forceUnique);
	}

	private Texture2D loadTextureCustomPath(String file, boolean forceUnique) throws IOException {
		return PNGLoader.loadTexture(file + ".png", lodBias, anisotropyEnabled && isAnisotropySupported(), anisotropyAmount, forceUnique);
	}

	/**
	 * Load a font
	 *
	 * @param name    The name of the file without the <em>.fnt</em> extension in <em>res/fonts</em> to load
	 * @param padding The amount of padding around each character
	 * @return The font that was loaded
	 * @throws IOException If the font failed to load
	 */
	public Font loadFont(String name, int padding) throws IOException {
		Scanner scanner = new Scanner(ResourceLoader.getResource("res/fonts/" + name + ".fnt"));

		String line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		String[] tokens = line.split(" ");
		if (!line.startsWith("info ") && tokens[10].startsWith("padding="))
			throw new FileFormatException("Not a valid font file");

		String[] padStr = tokens[10].substring(8).replaceAll("\\s", "").split(",");
		int[] paddingArr = new int[padStr.length];
		for (int i = 0; i < padStr.length; i++) {
			paddingArr[i] = Integer.parseInt(padStr[i]);
		}


		line = scanner.nextLine();
		line = line.replaceAll("\\s+", " ");
		if (!line.startsWith("common "))
			throw new FileFormatException("Not a valid font file");

		tokens = line.split(" ");
		if (!tokens[1].startsWith("lineHeight=") || !tokens[3].startsWith("scaleW=") || !tokens[4].startsWith("scaleH=") || !tokens[5].startsWith("pages="))
			throw new FileFormatException("Not a valid font file");

		float lineHeight = Integer.parseInt(tokens[1].substring(11)) - paddingArr[0] - paddingArr[1];
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

		Texture2D texture = loadTextureCustomPath("res/fonts/" + textureFile, false);

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

			glyph.texCoordX = Integer.parseInt(tokens[2].substring(2)) + paddingArr[3];
			glyph.texCoordMaxY = (Integer.parseInt(tokens[3].substring(2)) - paddingArr[0]);

			glyph.texCoordMaxX = glyph.texCoordX + (Integer.parseInt(tokens[4].substring(6)) - paddingArr[1]);
			glyph.texCoordY = glyph.texCoordMaxY + (Integer.parseInt(tokens[5].substring(7)) + paddingArr[2]);

			glyph.width = Integer.parseInt(tokens[4].substring(6)) - paddingArr[3] - paddingArr[2];
			glyph.height = Integer.parseInt(tokens[5].substring(7)) - paddingArr[1] - paddingArr[0];

			glyph.xOffset = Integer.parseInt(tokens[6].substring(8)) + paddingArr[3];
			glyph.yOffset = lineHeight - (Integer.parseInt(tokens[7].substring(8)) + glyph.height + paddingArr[0]);

			glyph.xAdvance = Integer.parseInt(tokens[8].substring(9)) - paddingArr[2] - paddingArr[3];

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

		return new Font(this, glyphs, texture, padding, width, lineHeight, (lineHeight - paddingArr[0] - paddingArr[1]) / lineHeight);
	}

	/**
	 * Called before an OpenGL context change, destroys any vertex arrays and framebuffers
	 */
	public void preContextChange() {
		Logs.d("Cleaning up vertex arrays");
		for (int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		vaos.clear();

		Framebuffer3D.preContextChange();
	}

	/**
	 * Called after an OpenGL context change, recreates any vertex arrays and framebuffers that were destroyed
	 */
	public void postContextChange() {
		Model.contextChanged(vaos);
		Framebuffer2D.contextChanged();
		Framebuffer3D.contextChanged();
		FramebufferMousePick.contextChanged();
		FramebufferDeferred.contextChanged();
	}

	/**
	 * Load a height map from a texture
	 *
	 * @param name      The name of the file without the <em>.png</em> extension in <em>res/textures</em> to load
	 * @param maxHeight The height of a full white color
	 * @return The height map that was created
	 * @throws IOException If the height map failed to load
	 */
	public HeightMap generateHeightMap(String name, float maxHeight) throws IOException {
		return new HeightMap(ImageIO.read(ResourceLoader.getResource("res/textures/" + name + ".png")), maxHeight);
	}
}
