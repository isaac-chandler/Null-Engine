package nullEngine.gl;

import math.Vector4f;

/**
 * A color
 */
public class Color {
	/**
	 * The color black (0, 0, 0, 255)
	 */
	public static final Vector4f BLACK = new Vector4f(0, 0, 0);
	/**
	 * The color blue (0, 0, 255, 255)
	 */
	public static final Vector4f BLUE = new Vector4f(0, 0, 1);
	/**
	 * The color green (0, 255, 0, 255)
	 */
	public static final Vector4f GREEN = new Vector4f(0, 1, 0);
	/**
	 * The color cyan (0, 255, 255, 255)
	 */
	public static final Vector4f CYAN = new Vector4f(0, 1, 1);
	/**
	 * THe colo red (255, 0, 0 255)
	 */
	public static final Vector4f RED = new Vector4f(1, 0, 0);
	/**
	 * The color magenta (255, 0, 255, 255)
	 */
	public static final Vector4f MAGENTA = new Vector4f(1, 0, 1);
	/**
	 * The color yellow (255, 255, 0, 255)
	 */
	public static final Vector4f YELLOW = new Vector4f(1, 1, 0);
	/**
	 * The color white (255, 255, 255, 255)
	 */
	public static final Vector4f WHITE = new Vector4f(1, 1, 1);

	/**
	 * The red vaule
	 */
	public byte r;
	/**
	 * The green value
	 */
	public byte g;
	/**
	 * The blue value
	 */
	public byte b;
	/**
	 * The alpha value
	 */
	public byte a;

	/**
	 * Create a color from bytes
	 *
	 * @param r The red value
	 * @param g The green vaule
	 * @param b The blue value
	 * @param a The alpha value
	 */
	public Color(byte r, byte g, byte b, byte a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Create a color from bytes with full alpha
	 *
	 * @param r The red value
	 * @param g The green vaule
	 * @param b The blue value
	 */
	public Color(byte r, byte g, byte b) {
		this(r, g, b, (byte) 255);
	}

	/**
	 * Check wether a color equals another object
	 *
	 * @param obj The other object
	 * @return <code>true</code> if all of the channels are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Color) {
			Color color = (Color) obj;
			return color.r == r && color.g == g && color.b == b && color.a == a;
		}
		return false;
	}

	/**
	 * Get the color as an int
	 *
	 * @return The color in the form 0xAARRGGBB
	 */
	public int getValue() {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}
