package imageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

	private static final String[] extnesions = new String[] {
			"jpg", "jpeg", "jpe", "jif", "jfif", "jfi",
			"bmp", "dib",
			"wbmp",
			"gif"};

	public static void main(String[] args) {
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

		if (args[0].toLowerCase().endsWith(".png"))
			return;

		boolean correct = false;
		for (String extension : extnesions)
			if (args[0].toLowerCase().endsWith("." + extension))
				correct = true;

		if (!correct) {
			System.err.println("Unsupported image format: " + args[0].substring(args[0].lastIndexOf('.') + 1));
			System.exit(1);
		}

		File out = new File(args[0].substring(0, args[0].lastIndexOf('.')) + ".png");

		try {
			BufferedImage img = ImageIO.read(file);
			if (out.exists())
				out.delete();
			ImageIO.write(img, "PNG", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
