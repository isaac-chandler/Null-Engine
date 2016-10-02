package modelConverter.obj;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OBJModel {

	public ArrayList<Float> positions;
	public ArrayList<Float> texCoords;
	public ArrayList<Float> normals;
	public int[] indices;

	public OBJModel() {
	}

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

			this.positions = new ArrayList<Float>();
			this.texCoords = new ArrayList<Float>();
			this.normals = new ArrayList<Float>();
			this.indices = new int[indices.size()];

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

					this.positions.add(positions.get(current.positionIndex * 3));
					this.positions.add(positions.get(current.positionIndex * 3 + 1));
					this.positions.add(positions.get(current.positionIndex * 3 + 2));

					if (hasTexCoords) {
						this.texCoords.add(texCoords.get(current.texCoordIndex * 2));
						this.texCoords.add(texCoords.get(current.texCoordIndex * 2 + 1));
					} else {
						this.texCoords.add(0f);
						this.texCoords.add(0f);
					}

					if (hasNormals) {
						this.normals.add(normals.get(current.normalIndex * 3));
						this.normals.add(normals.get(current.normalIndex * 3 + 1));
						this.normals.add(normals.get(current.normalIndex * 3 + 2));
					} else {
						this.normals.add(0f);
						this.normals.add(0f);
						this.normals.add(0f);
					}

					this.indices[i] = currentIndex;
					currentIndex++;
				} else {
					this.indices[i] = indexMap.get(previous);
				}
			}
			System.out.println("Optimized.");
		} catch (Exception e) {
			System.out.println(lineNum);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
