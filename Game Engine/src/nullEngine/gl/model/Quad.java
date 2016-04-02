package nullEngine.gl.model;

import nullEngine.loading.Loader;

public class Quad {
	
	private static Model model;
	
	private static final float[] vertices = new float[] {
			-1, 1, 0f,
			-1, -1, 0f,
			1, -1, 0f,
			1, 1, 0f,
	};

	private static final int[] indices = new int[] {
			0, 1, 3,
			3, 1, 2
	};

	private static final float[] textueCoords = new float[] {
			0, 0,
			0, 1,
			1, 1,
			1, 0
	};

	private static final float[] normals = new float[] {
			0, 0, -1,
			0, 0, -1,
			0, 0, -1,
			0, 0, -1
	};
	
	public static void setup(Loader loader) {
		model = loader.loadModel(vertices, textueCoords, normals, indices);
	}
	
	public static Model get() {
		return model;
	}
}
