package nullEngine.gl;

public class Color {
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
