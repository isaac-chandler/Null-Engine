package modelConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	private static final byte VERSION = 1;

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

		File out = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.')) + ".nlm");

		if (out.exists())
			out.delete();

		FileOutputStream fos = new FileOutputStream(out);
		fos.write(VERSION);

		OBJModel loader = new OBJModel(file);


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

		fos.close();
	}
}
