package modelConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	//VERSION 1 Multiple LOD not supported
	//VERSION 2 Multiple LOD supported
	//VERSION 3 Multiple LODs are interleaved

	private static final byte VERSION = 3;

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("No file selected");
			System.exit(1);
		}

		File file = new File(new File(args[0]).getAbsolutePath());

		if (!file.exists()) {
			System.err.println("File " + args[0] + " does not exist");
			System.exit(1);
		}

		if (file.isDirectory()) {
			System.err.println(args[0] + " is a directory");
			System.exit(1);
		}

		int lodCount = 5;
		double lodBias = 1;
		boolean cw = false;

		for (String arg : args) {
			if (arg.matches("\\-lodcount=\\d+")) {
				lodCount = Byte.parseByte(arg.substring(10));
				if (lodCount < 1) {
					System.err.println("Level of detail count cannot be less than 1");
					System.exit(1);
				}
			} else if (arg.matches("\\-lodbias=\\d+\\.?\\d*")) {
				lodBias = Double.parseDouble(arg.substring(9));
				if (lodBias < 1) {
					System.err.println("Level of detail bias cannot be less than 1");
					System.exit(1);
				}
			} else if (arg.equals("-cw") || arg.equals("-clockwise")) {
				cw = true;
			} else if (arg.equals("-ccw") || arg.equals("-counterclockwise") || arg.equals("-acw") || arg.equals("-anticlockwise")) {
				cw = false;
			}
		}

		File out = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.')) + ".nlm");

		OBJModel loader = new OBJModel(file, cw);
		ArrayList<Float> positions = new ArrayList<Float>(loader.newPositions);
		ArrayList<Float> texCoords = new ArrayList<Float>(loader.newTexCoords);
		ArrayList<Float> normals = new ArrayList<Float>(loader.newNormals);
		ArrayList<Integer> indices = new ArrayList<Integer>(loader.newIndices.length);
		for (int  i : loader.newIndices)
			indices.add(i);
		int[] vertexCounts = new int[lodCount];
		vertexCounts[0] = loader.newIndices.length;

		for (int i = 1; i < lodCount; i ++) {
			System.out.println("Simpilifying (" + ((100 / (lodCount - 1)) * (i - 1)) + "%)...");
			int offset = positions.size() / 3;
			loader.simplify(lodBias);
			positions.addAll(loader.newPositions);
			texCoords.addAll(loader.newTexCoords);
			normals.addAll(loader.newNormals);
			for (int j : loader.newIndices) {
				indices.add(j + offset);
			}
			vertexCounts[i] = loader.newIndices.length;
		}

		System.out.println("Simplifying (100%)...");
		System.out.println("Simplified.");

		System.out.println("Writing to file...");

		if (out.exists())
			out.delete();

		FileOutputStream fos = new FileOutputStream(out);
		fos.write(VERSION);
		fos.write(lodCount);

		for (int vertexCount : vertexCounts) {
			StreamUtils.writeInt(fos, vertexCount);
		}

		for (int index : indices) {
			StreamUtils.writeInt(fos, index);
		}

		StreamUtils.writeInt(fos, positions.size() / 3);
		for (float position : positions) {
			StreamUtils.writeInt(fos, Float.floatToIntBits(position));
		}

		for (float texCoord : texCoords) {
			StreamUtils.writeInt(fos, Float.floatToIntBits(texCoord));
		}

		for (float normal : normals) {
			StreamUtils.writeInt(fos, Float.floatToIntBits(normal));
		}

		fos.close();

		System.out.println("Done.");
	}
}
