package modelConverter;

import modelConverter.obj.OBJModel;

import java.util.ArrayList;
import java.util.HashMap;

public class VoxelSimpilier implements Simplifier {
	@Override
	public OBJModel simplify(double lodBias, OBJModel model) {
		OBJModel result = new OBJModel();
		int size = (int) (Math.pow(model.positions.size() / 3, 1. / 3.) / Math.pow(lodBias, 1. / 3.));

		ArrayList<Float> positions = new ArrayList<Float>();
		ArrayList<Float> texCoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		ArrayList<Integer>[][][] grid = new ArrayList[size][size][size];
		float[][][][] averagePostition = new float[size][size][size][3];
		float[][][][] averageTexCoord = new float[size][size][size][2];
		float[][][][] averageNormal = new float[size][size][size][3];

		float minX = Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float minZ = Float.MAX_VALUE;

		float maxX = Float.MIN_VALUE;
		float maxY = Float.MIN_VALUE;
		float maxZ = Float.MIN_VALUE;

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					grid[x][y][z] = new ArrayList<Integer>();
				}
			}
		}

		for (int i = 0; i < model.positions.size() / 3; i++) {
			if (model.positions.get(i * 3) < minX)
				minX = model.positions.get(i * 3);

			if (model.positions.get(i * 3 + 1) < minY)
				minY = model.positions.get(i * 3 + 1);

			if (model.positions.get(i * 3 + 2) < minZ)
				minZ = model.positions.get(i * 3 + 2);

			if (model.positions.get(i * 3) > maxX)
				maxX = model.positions.get(i * 3);

			if (model.positions.get(i * 3 + 1) > maxY)
				maxY = model.positions.get(i * 3 + 1);

			if (model.positions.get(i * 3 + 2) > maxZ)
				maxZ = model.positions.get(i * 3 + 2);
		}

		float sectionWidth = (maxX - minX) / size;
		float sectionHeight = (maxY - minY) / size;
		float sectionDepth = (maxZ - minZ) / size;

		for (int i = 0; i < model.positions.size() / 3; i++) {
			int x = clamp((int) ((model.positions.get(i * 3) - minX) / sectionWidth), 0, size - 1);
			int y = clamp((int) ((model.positions.get(i * 3 + 1) - minY) / sectionHeight), 0, size - 1);
			int z = clamp((int) ((model.positions.get(i * 3 + 2) - minZ) / sectionDepth), 0, size - 1);

			grid[x][y][z].add(i);
		}

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					int length = grid[x][y][z].size();
					if (length == 0) continue;

					float xTotal = 0, yTotal = 0, zTotal = 0;

					for (int i : grid[x][y][z]) {
						xTotal += model.positions.get(i * 3);
						yTotal += model.positions.get(i * 3 + 1);
						zTotal += model.positions.get(i * 3 + 2);
					}
					averagePostition[x][y][z][0] = xTotal / length;
					averagePostition[x][y][z][1] = yTotal / length;
					averagePostition[x][y][z][2] = zTotal / length;

					xTotal = 0;
					yTotal = 0;

					for (int i : grid[x][y][z]) {
						xTotal += model.texCoords.get(i * 2);
						yTotal += model.texCoords.get(i * 2 + 1);
					}
					averageTexCoord[x][y][z][0] = xTotal / length;
					averageTexCoord[x][y][z][1] = yTotal / length;

					xTotal = 0;
					yTotal = 0;
					zTotal = 0;

					for (int i : grid[x][y][z]) {
						xTotal += model.normals.get(i * 3);
						yTotal += model.normals.get(i * 3 + 1);
						zTotal += model.normals.get(i * 3 + 2);
					}
					averageNormal[x][y][z][0] = xTotal / length;
					averageNormal[x][y][z][1] = yTotal / length;
					averageNormal[x][y][z][2] = zTotal / length;
				}
			}
		}

		ArrayList<Triangle> triangles = new ArrayList<Triangle>();

		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		int index = 0;

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					int length = grid[x][y][z].size();
					if (length == 0) continue;

					for (int i : grid[x][y][z])
						indexMap.put(i, index);

					positions.add(averagePostition[x][y][z][0]);
					positions.add(averagePostition[x][y][z][1]);
					positions.add(averagePostition[x][y][z][2]);

					texCoords.add(averageTexCoord[x][y][z][0]);
					texCoords.add(averageTexCoord[x][y][z][1]);

					normals.add(averageNormal[x][y][z][0]);
					normals.add(averageNormal[x][y][z][1]);
					normals.add(averageNormal[x][y][z][2]);

					index++;
				}
			}
		}

		for (int i = 0; i < model.indices.length / 3; i++) {
			Triangle triangle = new Triangle(indexMap.get(model.indices[i * 3]), indexMap.get(model.indices[i * 3 + 1]), indexMap.get(model.indices[i * 3 + 2]));
			if (!triangle.isLine() && !triangles.contains(triangle))
				triangles.add(triangle);
		}

		for (Triangle triangle : triangles) {
			indices.add(triangle.i0);
			indices.add(triangle.i1);
			indices.add(triangle.i2);
		}

		result.positions = positions;
		result.texCoords = texCoords;
		result.normals = normals;
		result.indices = toIntArray(indices);
		return result;
	}

	@Override
	public double getDefaultLodBias() {
		return 1;
	}

	@Override
	public boolean getDefaultShouldLodChain() {
		return true;
	}

	private static int clamp(int val, int min, int max) {
		if (val < min) {
			return min;
		}
		if (val > max) {
			return max;
		}
		return val;
	}

	private static int[] toIntArray(ArrayList<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
}
