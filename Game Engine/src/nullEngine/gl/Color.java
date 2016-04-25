package nullEngine.gl;

import math.Vector4f;

public class Color {
	public static final Vector4f BLACK = new Vector4f(0, 0, 0);
	public static final Vector4f BLUE = new Vector4f(0, 0, 1);
	public static final Vector4f GREEN = new Vector4f(0, 1, 0);
	public static final Vector4f CYAN = new Vector4f(0, 1, 1);
	public static final Vector4f RED = new Vector4f(1, 0, 0);
	public static final Vector4f MAGENTA = new Vector4f(1, 0, 1);
	public static final Vector4f YELLOW = new Vector4f(1, 1, 0);
	public static final Vector4f WHITE = new Vector4f(1, 1, 1);
	public byte r, g, b, a;

	public Color(byte r, byte g, byte b, byte a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Color) {
			Color color = (Color) obj;
			return color.r == r && color.g == g && color.b == b && color.a == a;
		}
		return false;
	}

	public int getValue() {
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}
