package nullEngine.graphics.model;

import nullEngine.loading.Loader;

/**
 * Model for the quad
 */
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

	private static final float[] texCoords = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
	};

	private static final float[] normals = new float[] {
			0, 0, -1,
			0, 0, -1,
			0, 0, -1,
			0, 0, -1
	};

	/**
	 * Create the quad
	 * @param loader The loader
	 */
	public static void setup(Loader loader) {
		model = loader.loadModel(vertices, texCoords, normals, indices);
	}

	/**
	 * Get the quad
	 * @return The quad
	 */
	public static Model get() {
		return model;
	}
}
