package nullEngine.gl.font;

import nullEngine.gl.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Font glyph struct class
 */
public class Glyph {
	public float texCoordX;
	public float texCoordY;
	public float texCoordMaxX;
	public float texCoordMaxY;
	public float width;
	public float height;
	public float xOffset;
	public float yOffset;
	public float xAdvance;
	public Model model;
	public Map<Character, Float> kerning = new HashMap<>();
	public char character;
}
