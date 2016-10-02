package modelConverter;

public class Triangle {
	public int i0;
	public int i1;
	public int i2;

	public Triangle(int i0, int i1, int i2) {
		this.i0 = i0;
		this.i1 = i1;
		this.i2 = i2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triangle) {
			Triangle triangle = (Triangle) obj;
			return triangle.i0 == i0 && triangle.i1 == i1 && triangle.i2 == i2;
		}
		return false;
	}

	public boolean isLine() {
		return i0 == i1 || i1 == i2 || i0 == i2;
	}
}
