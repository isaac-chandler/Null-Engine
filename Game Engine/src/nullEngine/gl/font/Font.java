package nullEngine.gl.font;

import nullEngine.control.Application;
import nullEngine.gl.model.Model;
import nullEngine.gl.shader.Shader;
import nullEngine.gl.shader.TextShader;
import nullEngine.gl.texture.Texture2D;
import nullEngine.loading.Loader;

import java.util.HashMap;
import java.util.Map;

//TODO multi character mesh generation, built in string formatting
public class Font {

	private float yAdvance;
	private Glyph space;
	private HashMap<Character, Glyph> glyphs = new HashMap<Character, Glyph>();

	private Texture2D texture;

	public Font(Loader loader, HashMap<Character, Glyph> glyphs, Texture2D texture, int desiredPadding, float textureSize, float lineHeight, float yAdvance) {
		this.texture = texture;
		this.yAdvance = yAdvance;

		for (Map.Entry<Character, Glyph> glyph : glyphs.entrySet()) {
			glyph.getValue().model = genMeshData(loader, glyph.getValue(), desiredPadding, textureSize, lineHeight);
		}
		this.glyphs = glyphs;
		space = glyphs.get(' ');
	}

	private Model genMeshData(Loader loader, Glyph glyph, int desiredPadding, float textureSize, float lineHeight) {
		float positionPadding = desiredPadding / lineHeight;
		float texCoordPadding = desiredPadding / textureSize;

		int[] indoces = new int[] {
				0, 1, 3,
				3, 1, 2
		};
		float[] positions = new float[] {
				glyph.xOffset - positionPadding, glyph.yOffset + glyph.height + positionPadding, 0,
				glyph.xOffset - positionPadding, glyph.yOffset - positionPadding, 0,
				glyph.xOffset + glyph.width + positionPadding, glyph.yOffset - positionPadding,  0,
				glyph.xOffset + glyph.width + positionPadding, glyph.yOffset + glyph.height + positionPadding, 0
		};
		float[] texCoords = new float[] {
				glyph.texCoordX - texCoordPadding, glyph.texCoordMaxY + texCoordPadding,
				glyph.texCoordX - texCoordPadding, glyph.texCoordY + texCoordPadding,
				glyph.texCoordMaxX + texCoordPadding, glyph.texCoordY + texCoordPadding,
				glyph.texCoordMaxX + texCoordPadding, glyph.texCoordMaxY + texCoordPadding
		};
		return loader.loadModel(positions, texCoords, new float[12], indoces);
	}

	public Texture2D getTexture() {
		return texture;
	}

	public boolean isCharacterSupported(char character) {
		return glyphs.containsKey(character) || character == '\n' || character == '\r';
	}

	//TODO fix kerning
	public void drawString(String text) {
		if (Shader.bound() instanceof TextShader) {
			TextShader shader = (TextShader) Shader.bound();
			shader.loadAspectRatio((float) Application.get().getHeight() / (float) Application.get().getWidth(), 1);
			float cursorX = 0;
			float cursorY = 0;

			text = text.replace("\r\n", "\n").replace("\r", "\n");
			texture.bind();

			Glyph previous = null;
			for (int i = 0; i < text.length(); i++) {
				char character = text.charAt(i);

				Glyph glyph = glyphs.get(character);

				if (character == '\n') {
					cursorX = 0;
					cursorY -= yAdvance;
				} else if (character == ' ' || glyph == null) {
					cursorX += space.xAdvance;
				} else {
					shader.loadOffset(cursorX, cursorY);
					glyph.model.render();
					cursorX += glyph.xAdvance;
					if (previous != null && previous.kerning.get(character) != null) {
						cursorX += previous.kerning.get(character);
					}
				}

				previous = glyph;
			}
		}
	}

	public Model getModel(char character) {
		return glyphs.get(character).model;
	}
}
