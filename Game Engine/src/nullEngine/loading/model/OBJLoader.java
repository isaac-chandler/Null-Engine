package nullEngine.loading.model;

import nullEngine.graphics.base.model.Model;
import nullEngine.loading.Loader;
import nullEngine.loading.ResourceLoader;
import nullEngine.util.logs.Logs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OBJLoader {
	public static Model loadModel(Loader loader, String name) {
		ArrayList<Float> positions = new ArrayList<Float>();
		ArrayList<Float> texCoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		ArrayList<OBJIndex> indices = new ArrayList<OBJIndex>();
		boolean hasTexCoords = false;
		boolean hasNormals = false;

		try {
			Scanner scanner = new Scanner(ResourceLoader.getResource("res/models/" + name));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
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
					for (int i = 0; i < tokens.length - 3; i++) {
						indices.add(new OBJIndex(tokens[1], hasTexCoords, hasNormals));
						indices.add(new OBJIndex(tokens[i + 2], hasTexCoords, hasNormals));
						indices.add(new OBJIndex(tokens[i + 3], hasTexCoords, hasNormals));
					}
				}
			}
			scanner.close();

			ArrayList<Float> newPositions = new ArrayList<Float>();
			ArrayList<Float> newTexCoords = new ArrayList<Float>();
			ArrayList<Float> newNormals = new ArrayList<Float>();
			int[] newIndices = new int[indices.size()];

			HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			int currentIndex = 0;

			for (int i = 0; i < indices.size(); i++) {
				OBJIndex current = indices.get(i);

				int previous = -1;

				for (int j = 0; j < i; j++ ) {
					OBJIndex old = indices.get(j);

					if (current.positionIndex == old.positionIndex && current.texCoordIndex == old.texCoordIndex && current.normalIndex == old.normalIndex) {
						previous = j;
						break;
					}
				}

				if (previous == -1) {
					indexMap.put(i, currentIndex);

					newPositions.add(positions.get(current.positionIndex * 3));
					newPositions.add(positions.get(current.positionIndex  * 3 + 1));
					newPositions.add(positions.get(current.positionIndex  * 3 + 2));

					if (hasTexCoords) {
						newTexCoords.add(texCoords.get(current.texCoordIndex * 2));
						newTexCoords.add(texCoords.get(current.texCoordIndex * 2 + 1));
					} else {
						newTexCoords.add(0f);
						newTexCoords.add(0f);
					}

					if (hasNormals) {
						newNormals.add(normals.get(current.normalIndex  * 3));
						newNormals.add(normals.get(current.normalIndex  * 3 + 1));
						newNormals.add(normals.get(current.normalIndex  * 3 + 2));
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

			return loader.loadModel(loader.toFloatArray(newPositions), loader.toFloatArray(newTexCoords), loader.toFloatArray(newNormals), newIndices);
		} catch (FileNotFoundException e) {
			Logs.f(e);
			return null;
		}
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
}
