package nullEngine.gl.font;

import nullEngine.control.Application;
import nullEngine.gl.buffer.IndexBuffer;
import nullEngine.gl.buffer.VertexBuffer;
import nullEngine.gl.model.Model;
import nullEngine.gl.shader.Shader;
import nullEngine.gl.shader.TextShader;
import nullEngine.gl.texture.Texture2D;
import nullEngine.loading.Loader;
import util.Sizeof;

import java.util.HashMap;
import java.util.Map;

//TODO string formatting

/**
 * Font class
 */
public class Font {

	private int padding;
	private float yAdvance;
	private float lineHeight;
	private float textureSize;
	private Loader loader;
	private Glyph space;
	private Map<Character, Glyph> glyphs = new HashMap<>();

	private Texture2D texture;

	/**
	 * Create a new font
	 *
	 * @param loader      The loader
	 * @param glyphs      The glyphs
	 * @param texture     The texture
	 * @param padding     The amount of padding around the characters
	 * @param textureSize The texture size
	 * @param lineHeight  The line height
	 * @param yAdvance    The y advance
	 */
	public Font(Loader loader, HashMap<Character, Glyph> glyphs, Texture2D texture, int padding, float textureSize, float lineHeight, float yAdvance) {
		this.texture = texture;
		this.yAdvance = yAdvance;
		this.padding = padding;
		this.lineHeight = lineHeight;
		this.textureSize = textureSize;
		this.loader = loader;

		for (Map.Entry<Character, Glyph> glyph : glyphs.entrySet()) {
			glyph.getValue().model = genMeshData(glyph.getValue());

		}
		this.glyphs = glyphs;
		space = glyphs.get(' ');
	}

	private Model genMeshData(Glyph glyph) {
		float positionPadding = padding / lineHeight;
		float texCoordPadding = padding / textureSize;

		int[] indices = new int[] {
				0, 1, 3,
				3, 1, 2
		};
		float[] positions = new float[] {
				glyph.xOffset - positionPadding, glyph.yOffset + glyph.height + positionPadding, 0,
				glyph.xOffset - positionPadding, glyph.yOffset - positionPadding, 0,
				glyph.xOffset + glyph.width + positionPadding, glyph.yOffset - positionPadding, 0,
				glyph.xOffset + glyph.width + positionPadding, glyph.yOffset + glyph.height + positionPadding, 0
		};
		float[] texCoords = new float[] {
				glyph.texCoordX - texCoordPadding, glyph.texCoordMaxY + texCoordPadding,
				glyph.texCoordX - texCoordPadding, glyph.texCoordY + texCoordPadding,
				glyph.texCoordMaxX + texCoordPadding, glyph.texCoordY + texCoordPadding,
				glyph.texCoordMaxX + texCoordPadding, glyph.texCoordMaxY + texCoordPadding
		};
		float[] normals = new float[] {
				0, 0, -1,
				0, 0, -1,
				0, 0, -1
		};
		return loader.loadModel(positions, texCoords, normals, indices);
	}

	private int genMeshData(Glyph glyph, float xOffset, float yOffset, int offset, int[] indices, float[] positions, float[] texCoords, float[] normals) {
		float positionPadding = padding / lineHeight;
		float texCoordPadding = padding / textureSize;

		indices[offset * 6] = offset * 4;
		indices[offset * 6 + 1] = offset * 4 + 1;
		indices[offset * 6 + 2] = offset * 4 + 3;
		indices[offset * 6 + 3] = offset * 4 + 3;
		indices[offset * 6 + 4] = offset * 4 + 1;
		indices[offset * 6 + 5] = offset * 4 + 2;

		positions[offset * 12] = xOffset + glyph.xOffset - positionPadding;
		positions[offset * 12 + 1] = yOffset + glyph.yOffset + glyph.height + positionPadding;
		positions[offset * 12 + 2] = 0;
		positions[offset * 12 + 3] = xOffset + glyph.xOffset - positionPadding;
		positions[offset * 12 + 4] = yOffset + glyph.yOffset - positionPadding;
		positions[offset * 12 + 5] = 0;
		positions[offset * 12 + 6] = xOffset + glyph.xOffset + glyph.width + positionPadding;
		positions[offset * 12 + 7] = yOffset + glyph.yOffset - positionPadding;
		positions[offset * 12 + 8] = 0;
		positions[offset * 12 + 9] = xOffset + glyph.xOffset + glyph.width + positionPadding;
		positions[offset * 12 + 10] = yOffset + glyph.yOffset + glyph.height + positionPadding;
		positions[offset * 12 + 11] = 0;

		texCoords[offset * 8] = glyph.texCoordX - texCoordPadding;
		texCoords[offset * 8 + 1] = glyph.texCoordMaxY + texCoordPadding;
		texCoords[offset * 8 + 2] = glyph.texCoordX - texCoordPadding;
		texCoords[offset * 8 + 3] = glyph.texCoordY + texCoordPadding;
		texCoords[offset * 8 + 4] = glyph.texCoordMaxX + texCoordPadding;
		texCoords[offset * 8 + 5] = glyph.texCoordY + texCoordPadding;
		texCoords[offset * 8 + 6] = glyph.texCoordMaxX + texCoordPadding;
		texCoords[offset * 8 + 7] = glyph.texCoordMaxY + texCoordPadding;

		normals[offset * 12] = 0;
		normals[offset * 12 + 1] = 0;
		normals[offset * 12 + 2] = -1;
		normals[offset * 12 + 3] = 0;
		normals[offset * 12 + 4] = 0;
		normals[offset * 12 + 5] = -1;
		normals[offset * 12 + 6] = 0;
		normals[offset * 12 + 7] = 0;
		normals[offset * 12 + 8] = -1;
		normals[offset * 12 + 9] = 0;
		normals[offset * 12 + 10] = 0;
		normals[offset * 12 + 11] = -1;

		return offset + 1;
	}

	private int genMeshData(Glyph glyph, float xOffset, float yOffset, int offset, IndexBuffer indices, VertexBuffer positions, VertexBuffer texCoords, VertexBuffer normals) {
		float positionPadding = padding / lineHeight;
		float texCoordPadding = padding / textureSize;

		indices.set(offset * 4, (offset * 6));
		indices.set(offset * 4 + 1, (offset * 6 + 1));
		indices.set(offset * 4 + 3, (offset * 6 + 2));
		indices.set(offset * 4 + 3, (offset * 6 + 3));
		indices.set(offset * 4 + 1, (offset * 6 + 4));
		indices.set(offset * 4 + 2, (offset * 6 + 5));

		positions.set(xOffset + glyph.xOffset - positionPadding, (offset * 12));
		positions.set(yOffset + glyph.yOffset + glyph.height + positionPadding, (offset * 12 + 1));
		positions.set(0, (offset * 12 + 2));
		positions.set(xOffset + glyph.xOffset - positionPadding, (offset * 12 + 3));
		positions.set(yOffset + glyph.yOffset - positionPadding, (offset * 12 + 4));
		positions.set(0, (offset * 12 + 5));
		positions.set(xOffset + glyph.xOffset + glyph.width + positionPadding, (offset * 12 + 6));
		positions.set(yOffset + glyph.yOffset - positionPadding, (offset * 12 + 7));
		positions.set(0, (offset * 12 + 8));
		positions.set(xOffset + glyph.xOffset + glyph.width + positionPadding, (offset * 12 + 9));
		positions.set(yOffset + glyph.yOffset + glyph.height + positionPadding, (offset * 12 + 10));
		positions.set(0, (offset * 12 + 11));

		texCoords.set(glyph.texCoordX - texCoordPadding, (offset * 8));
		texCoords.set(glyph.texCoordMaxY + texCoordPadding, (offset * 8 + 1));
		texCoords.set(glyph.texCoordX - texCoordPadding, (offset * 8 + 2));
		texCoords.set(glyph.texCoordY + texCoordPadding, (offset * 8 + 3));
		texCoords.set(glyph.texCoordMaxX + texCoordPadding, (offset * 8 + 4));
		texCoords.set(glyph.texCoordY + texCoordPadding, (offset * 8 + 5));
		texCoords.set(glyph.texCoordMaxX + texCoordPadding, (offset * 8 + 6));
		texCoords.set(glyph.texCoordMaxY + texCoordPadding, (offset * 8 + 7));

		normals.set(0, (offset * 12));
		normals.set(0, (offset * 12 + 1));
		normals.set(-1, (offset * 12 + 2));
		normals.set(0, (offset * 12 + 3));
		normals.set(0, (offset * 12 + 4));
		normals.set(-1, (offset * 12 + 5));
		normals.set(0, (offset * 12 + 6));
		normals.set(0, (offset * 12 + 7));
		normals.set(-1, (offset * 12 + 8));
		normals.set(0, (offset * 12 + 9));
		normals.set(0, (offset * 12 + 10));
		normals.set(-1, (offset * 12 + 11));

		return offset + 1;
	}

	/**
	 * Get the texture
	 *
	 * @return The texture
	 */
	public Texture2D getTexture() {
		return texture;
	}

	/**
	 * Get wether a character is supported
	 * @param character The character
	 * @return Wether the character is supported
	 */
	public boolean isCharacterSupported(char character) {
		return glyphs.containsKey(character) || character == '\n' || character == '\r' || character == ' ';
	}


	/**
	 * Draw a string
	 * @param text The string
	 */
	public void drawString(String text) {
		if (Shader.bound() instanceof TextShader) {
			TextShader shader = (TextShader) Shader.bound();
			shader.loadAspectRatio((float) Application.get().getHeight() / (float) Application.get().getWidth(), 1);
			float cursorX = 0;
			float cursorY = 0;

			text = text.replace("\r\n", "\n").replace("\r", "\n");
			texture.bind();

			char next = text.charAt(0);
			for (int i = 0; i < text.length(); i++) {
				char character = text.charAt(i);

				Glyph glyph = glyphs.get(next);
				next = i < text.length() - 1 ? text.charAt(i + 1) : '\0';

				if (character == '\n') {
					cursorX = 0;
					cursorY -= yAdvance;
				} else {
					if (glyph == null) glyph = space;
					if (glyph == space) {
						cursorX += space.xAdvance;
					} else {
						shader.loadOffset(cursorX, cursorY);
						glyph.model.render();
						cursorX += glyph.xAdvance;
					}
					Float kerning = glyph.kerning.get(next);
					if (next != '\0' && kerning != null)
						cursorX += kerning;
				}
			}
		}
	}

	/**
	 * Create a string mesh
	 * @param text The text
	 * @return The mesh that was created
	 */
	public Model createString(String text) {
		text = text.replace("\r\n", "\n").replace("\r", "\n");

		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			if (!(character == '\n' || character == ' ' || !glyphs.containsKey(character)))
				count++;
		}

		int[] indices = new int[6 * count];
		float[] positions = new float[12 * count];
		float[] texCoords = new float[8 * count];
		float[] normals = new float[12 * count];

		float cursorX = 0;
		float cursorY = 0;
		char next = text.charAt(0);
		int index = 0;
		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);

			Glyph glyph = glyphs.get(next);
			next = i < text.length() - 1 ? text.charAt(i + 1) : '\0';

			if (character == '\n') {
				cursorX = 0;
				cursorY -= yAdvance;
			} else {
				if (glyph == null) glyph = space;
				if (glyph == space) {
					cursorX += space.xAdvance;
				} else {
					index = genMeshData(glyph, cursorX, cursorY, index, indices, positions, texCoords, normals);
					cursorX += glyph.xAdvance;
				}
				Float kerning = glyph.kerning.get(next);
				if (next != '\0' && kerning != null)
					cursorX += kerning;
			}
		}
		return loader.loadModel(positions, texCoords, normals, indices);
	}

	/**
	 * Update a string mesh
	 * @param model The current mesh
	 * @param text The text
	 */
	public void updateString(Model model, String text) {
		VertexBuffer positions = model.getVertexBuffer(0);
		VertexBuffer texCoords = model.getVertexBuffer(1);
		VertexBuffer normals = model.getVertexBuffer(2);
		IndexBuffer indices = model.getIndexBuffer();

		text = text.replace("\r\n", "\n").replace("\r", "\n");

		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			if (!(character == '\n' || character == ' ' || !glyphs.containsKey(character)))
				count++;
		}
		int oldCount = indices.getCapacity() / 6 / Sizeof.INT;

		if (count < oldCount * 2 || count > oldCount) {
			indices.setSize(count * 6 * Sizeof.INT);
			positions.setSize(count * 4 * 3 * Sizeof.FLOAT);
			texCoords.setSize(count * 4 * 2 * Sizeof.FLOAT);
			normals.setSize(count * 4 * 3 * Sizeof.FLOAT);
		}

		float cursorX = 0;
		float cursorY = 0;
		char next = text.charAt(0);
		int index = 0;
		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);

			Glyph glyph = glyphs.get(next);
			next = i < text.length() - 1 ? text.charAt(i + 1) : '\0';

			if (character == '\n') {
				cursorX = 0;
				cursorY -= yAdvance;
			} else {
				if (glyph == null) glyph = space;
				if (glyph == space) {
					cursorX += space.xAdvance;
				} else {
					index = genMeshData(glyph, cursorX, cursorY, index, indices, positions, texCoords, normals);
					cursorX += glyph.xAdvance;
				}
				Float kerning = glyph.kerning.get(next);
				if (next != '\0' && kerning != null)
					cursorX += kerning;
			}
		}
	}

	/**
	 * Get the model for a character
	 * @param character The character
	 * @return The model
	 */
	public Model getModel(char character) {
		return glyphs.getOrDefault(character, space).model;
	}
}
