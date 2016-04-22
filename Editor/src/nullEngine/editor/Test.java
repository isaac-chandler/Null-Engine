package nullEngine.editor;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;
import java.io.File;

public class Test {
	public static void main(String[] args) {
		try {
//			System.setProperty("java.library.path", System.getProperty("java.library.path") + ";" + new File("lib/bin/windows").getAbsolutePath());
//			System.out.println(System.getProperty("java.library.path"));
			loadLib("jogl_desktop");
			loadLib("jogl_mobile");
			loadLib("nativewindow_awt");
			loadLib("nativewindow_win32");
			loadLib("newt");
			loadLib("gluegen-rt");
		} catch (UnsatisfiedLinkError e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		GLProfile.initSingleton();
		GLProfile glp = GLProfile.getMaxProgrammableCore(true);
		GLCapabilities capabilities = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(capabilities);
		JFrame frame = new JFrame("Test");
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.setVisible(true);
	}

	private static void loadLib(String name) {
		System.out.println(new File("lib/bin/windows/" + name + ".dll").getAbsolutePath());
		System.load(new File("lib/bin/windows/" + name + ".dll").getAbsolutePath());
	}
}
