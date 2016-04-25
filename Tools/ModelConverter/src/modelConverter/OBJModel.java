package modelConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OBJModel {

	public ArrayList<Float> newPositions;
	public ArrayList<Float> newTexCoords;
	public ArrayList<Float> newNormals;
	public int[] newIndices;

	public OBJModel(File name, boolean cw) {
		ArrayList<Float> positions = new ArrayList<Float>();
		ArrayList<Float> texCoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		boolean hasTexCoords = false;
		boolean hasNormals = false;
		int lineNum = 0;

		try {
			System.out.println("Reading...");
			Scanner scanner = new Scanner(name);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNum++;
				line = line.replaceAll("\\s+", " ");
				String[] tokens = line.split(" ");
				if (tokens.length == 0 || tokens[0].startsWith("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					positions.add(Float.parseFloat(tokens[1]));
					positions.add(Float.parseFloat(tokens[2]));
					positions.add(Float.parseFloat(tokens[3]));
				} else if (tokens[0].equals("vt")) {
					hasTexCoords = true;
					texCoords.add(Float.parseFloat(tokens[1]));
					texCoords.add(Float.parseFloat(tokens[2]));
				} else if (tokens[0].equals("vn")) {
					hasNormals = true;
					normals.add(Float.parseFloat(tokens[1]));
					normals.add(Float.parseFloat(tokens[2]));
					normals.add(Float.parseFloat(tokens[3]));
				} else if (tokens[0].equals("f")) {
					if (tokens.length > 4) {
						scanner.close();
						System.err.println("Faces must be triangulated");
						System.exit(1);
					}
					if (cw) {
						indices.add(new OBJIndex(tokens[3], hasTexCoords, hasNormals));
						indices.add(new OBJIndex(tokens[2], hasTexCoords, hasNormals));
						indices.add(new OBJIndex(tokens[1], hasTexCoords, hasNormals));
					} else {
						indices.add(new OBJIndex(tokens[1], hasTexCoords, hasNormals));
						indices.add(new OBJIndex(tokens[2], hasTexCoords, hasNormals));
						indices.add(new OBJIndex(tokens[3], hasTexCoords, hasNormals));
					}
				}
			}
			scanner.close();
			System.out.println("Read.");

			newPositions = new ArrayList<Float>();
			newTexCoords = new ArrayList<Float>();
			newNormals = new ArrayList<Float>();
			newIndices = new int[indices.size()];

			HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			int currentIndex = 0;

			System.out.println("Optimizing...");
			for (int i = 0; i < indices.size(); i++) {
				OBJIndex current = indices.get(i);

				int previous = -1;

				for (int j = 0; j < i; j++) {
					OBJIndex old = indices.get(j);

					if (current.positionIndex == old.positionIndex && current.texCoordIndex == old.texCoordIndex && current.normalIndex == old.normalIndex) {
						previous = j;
						break;
					}
				}

				if (previous == -1) {
					indexMap.put(i, currentIndex);

					newPositions.add(positions.get(current.positionIndex * 3));
					newPositions.add(positions.get(current.positionIndex * 3 + 1));
					newPositions.add(positions.get(current.positionIndex * 3 + 2));

					if (hasTexCoords) {
						newTexCoords.add(texCoords.get(current.texCoordIndex * 2));
						newTexCoords.add(texCoords.get(current.texCoordIndex * 2 + 1));
					} else {
						newTexCoords.add(0f);
						newTexCoords.add(0f);
					}

					if (hasNormals) {
						newNormals.add(normals.get(current.normalIndex * 3));
						newNormals.add(normals.get(current.normalIndex * 3 + 1));
						newNormals.add(normals.get(current.normalIndex * 3 + 2));
					} else {
						newNormals.add(0f);
						newNormals.add(0f);
						newNormals.add(0f);
					}

					newIndices[i] = currentIndex;
					currentIndex++;
				} else {
					newIndices[i] = indexMap.get(previous);
				}
			}
			System.out.println("Optimized.");
		} catch (Exception e) {
			System.out.println(lineNum);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void simplify(double lodBias) {
		int size = (int) (Math.pow(newPositions.size() / 3, 1. / 3.) / Math.pow(lodBias, 1. / 3.));

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

		for (int i = 0; i < newPositions.size() / 3; i++) {
			if (newPositions.get(i * 3) < minX)
				minX = newPositions.get(i * 3);

			if (newPositions.get(i * 3 + 1) < minY)
				minY = newPositions.get(i * 3 + 1);

			if (newPositions.get(i * 3 + 2) < minZ)
				minZ = newPositions.get(i * 3 + 2);

			if (newPositions.get(i * 3) > maxX)
				maxX = newPositions.get(i * 3);

			if (newPositions.get(i * 3 + 1) > maxY)
				maxY = newPositions.get(i * 3 + 1);

			if (newPositions.get(i * 3 + 2) > maxZ)
				maxZ = newPositions.get(i * 3 + 2);
		}

		float sectionWidth = (maxX - minX) / size;
		float sectionHeight = (maxY - minY) / size;
		float sectionDepth = (maxZ - minZ) / size;

		for (int i = 0; i < newPositions.size() / 3; i++) {
			int x = clamp((int) ((newPositions.get(i * 3) - minX) / sectionWidth), 0, size - 1);
			int y = clamp((int) ((newPositions.get(i * 3 + 1) - minY) / sectionHeight), 0, size - 1);
			int z = clamp((int) ((newPositions.get(i * 3 + 2) - minZ) / sectionDepth), 0, size - 1);

			grid[x][y][z].add(i);
		}

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					int length = grid[x][y][z].size();
					if (length == 0) continue;

					float xTotal = 0, yTotal = 0, zTotal = 0;

					for (int i : grid[x][y][z]) {
						xTotal += newPositions.get(i * 3);
						yTotal += newPositions.get(i * 3 + 1);
						zTotal += newPositions.get(i * 3 + 2);
					}
					averagePostition[x][y][z][0] = xTotal / length;
					averagePostition[x][y][z][1] = yTotal / length;
					averagePostition[x][y][z][2] = zTotal / length;

					xTotal = 0;
					yTotal = 0;

					for (int i : grid[x][y][z]) {
						xTotal += newTexCoords.get(i * 2);
						yTotal += newTexCoords.get(i * 2 + 1);
					}
					averageTexCoord[x][y][z][0] = xTotal / length;
					averageTexCoord[x][y][z][1] = yTotal / length;

					xTotal = 0;
					yTotal = 0;
					zTotal = 0;

					for (int i : grid[x][y][z]) {
						xTotal += newNormals.get(i * 3);
						yTotal += newNormals.get(i * 3 + 1);
						zTotal += newNormals.get(i * 3 + 2);
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

		for (int i = 0; i < newIndices.length / 3; i++) {
			Triangle triangle = new Triangle(indexMap.get(newIndices[i * 3]), indexMap.get(newIndices[i * 3 + 1]), indexMap.get(newIndices[i * 3 + 2]));
			if (!triangles.contains(triangle))
				triangles.add(triangle);
		}

		for (Triangle triangle : triangles) {
			indices.add(triangle.i0);
			indices.add(triangle.i1);
			indices.add(triangle.i2);
		}

		newPositions = positions;
		newTexCoords = texCoords;
		newNormals = normals;
		newIndices = toIntArray(indices);
	}

	private static class OBJIndex {
		public int positionIndex;
		public int texCoordIndex;
		public int normalIndex;

		public OBJIndex(String token, boolean hasTexCoords, boolean hasNormals) {
			String[] tokens = token.split("/");
			positionIndex = Integer.parseInt(tokens[0]) - 1;
			if (hasTexCoords)
				texCoordIndex = Integer.parseInt(tokens[1]) - 1;
			if (hasNormals)
				normalIndex = Integer.parseInt(tokens[2]) - 1;
		}
	}

	private static class Triangle {
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
	}

	private static int[] toIntArray(ArrayList<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	private int clamp(int val, int min, int max) {
		if (val < min) {
			return min;
		}
		if (val > max) {
			return max;
		}
		return val;
	}
}
