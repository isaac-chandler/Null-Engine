package nullEngine.gl.model;

import nullEngine.loading.Loader;

public class Terrain {

	public static Model generateFlatTerrain(Loader loader, float size, int vertexCount) {
		int count = vertexCount * vertexCount;
		count += count / 4 + count / 16 + count / 64;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] texCoords = new float[count * 2];
		int[] indices = new int[
				(6 * (vertexCount - 1) * (vertexCount - 1)) +
				(6 * (vertexCount / 2 - 1) * (vertexCount / 2 - 1)) +
				(6 * (vertexCount / 4 - 1) * (vertexCount / 4 - 1)) +
				(6 * (vertexCount / 8 - 1) * (vertexCount / 8 - 1))];

		int pointer = 0;
		for (int x = 0; x < vertexCount; x++) {
			for (int z = 0; z < vertexCount; z++) {
				vertices[pointer * 3] = (float) x / (float) (vertexCount - 1) * size - size / 2;
				vertices[pointer * 3 + 1] = 0;
				vertices[pointer * 3 + 2] = (float) z / (float) (vertexCount - 1) * size - size / 2;
				normals[pointer * 3] = 0;
				normals[pointer * 3 + 1] = 1;
				normals[pointer * 3 + 2] = 0;
				texCoords[pointer * 2] = (float) x / (float) (vertexCount - 1);
				texCoords[pointer * 2 + 1] = (float) z / (float) (vertexCount - 1);
				pointer++;
			}
		}

		pointer = 0;
		int lod = 0;
		int[] vertexCounts = new int[4];
		for (int scale = 1; scale <= 8; scale *= 2) {
			for (int x = 0; x < vertexCount - scale; x+= scale) {
				for (int z = 0; z < vertexCount - scale; z+= scale) {
					int topLeft = (z * vertexCount) + x;
					int topRight = topLeft + scale;
					int bottomLeft = ((z + scale) * vertexCount) + x;
					int bottomRight = bottomLeft + scale;
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = topRight;
					indices[pointer++] = topRight;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = bottomRight;
					vertexCounts[lod] += 6;
				}
			}
			lod++;
		}

		return loader.loadModel(vertices, texCoords, normals, indices, vertexCounts);
	}
}
