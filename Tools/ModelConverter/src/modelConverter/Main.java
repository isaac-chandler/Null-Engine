package modelConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

	//VERSION 1 Multiple LOD not supported
	//VERSION 2 Multiple LOD supported

	private static final byte VERSION = 2;

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
			}
		}

		File out = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.')) + ".nlm");

		if (out.exists())
			out.delete();

		FileOutputStream fos = new FileOutputStream(out);
		fos.write(VERSION);
		fos.write(lodCount);

		OBJModel loader = new OBJModel(file);
		write(loader, fos);

		for (int i = 0; i < lodCount - 1; i ++) {
			loader.simplify(lodBias);
			write(loader, fos);
		}

		fos.close();
	}

	private static void write(OBJModel loader, FileOutputStream fos) throws IOException {
		StreamUtils.writeInt(fos, loader.newPositions.size() / 3);
		for (float f : loader.newPositions)
			StreamUtils.writeInt(fos, Float.floatToIntBits(f));

		for (float f : loader.newTexCoords)
			StreamUtils.writeInt(fos, Float.floatToIntBits(f));

		for (float f : loader.newNormals)
			StreamUtils.writeInt(fos, Float.floatToIntBits(f));

		StreamUtils.writeInt(fos, loader.newIndices.length);

		for (int i : loader.newIndices)
			StreamUtils.writeInt(fos, i);
	}
}
