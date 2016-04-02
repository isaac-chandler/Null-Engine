package bundler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	private static final byte VERSION = 1;
	//1 byte for version + 4 bytes for file count
	private static final int HEADER_SIZE = 1 + 4;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("No file selected");
			System.exit(1);
		}

		File file = new File(new File(args[0]).getAbsolutePath());

		if (!file.exists()) {
			System.err.println("Directory " + args[0] + " does not exist");
			System.exit(1);
		}

		if (!file.isDirectory()) {
			System.err.println(args[0] + " is not a directory");
			System.exit(1);
		}

		ArrayList<String> names = new ArrayList<String>();

		getNames(file, names, "");
		try {
			File output = new File(args[0] + ".res");
			if (output.exists())
				output.delete();
			output.createNewFile();
			FileOutputStream out = new FileOutputStream(output);
			out.write(VERSION);

			StreamUtils.writeInt(out, names.size());
			int offset = HEADER_SIZE;
			for (String s : names) {
				offset += 6 + s.length();
			}

			ArrayList<File> files = new ArrayList<File>();
			for (String s : names) {
				StreamUtils.writeString(out, s);
				File f;
				files.add(f = new File(file.getParent() + "/" + s));
				StreamUtils.writeInt(out, offset);
				offset += f.length() + 4;
			}

			for (File f : files) {
				StreamUtils.writeInt(out, (int) f.length());
				FileInputStream in = new FileInputStream(f);
				byte[] buf = new byte[(int) f.length()];
				in.read(buf);
				in.close();
				out.write(buf);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private static void getNames(File file, ArrayList<String> names, String path) {
		if (file.isDirectory()) {
			String newPath = path + file.getName() + "/";
			for (File f : file.listFiles())
				getNames(f, names, newPath);
		} else {
			names.add(path + file.getName());
		}
	}
}
