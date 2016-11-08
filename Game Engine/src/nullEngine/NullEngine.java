package nullEngine;

import nullEngine.exception.InitializationException;
import nullEngine.gl.Window;
import nullEngine.loading.filesys.ResourceLoader;
import nullEngine.util.logs.Logs;
import org.lwjgl.system.Platform;

import java.io.File;

public class NullEngine {

	public static final String NATIVE_DIR = "natives";

	/**
	 * Setup the engine to run and start the Logs
	 * @throws InitializationException If initialization failed
	 */
	public static void init() throws InitializationException {
		Logs.dummy();
		Logs.init();
		initNatives();
		Window.init();
		ResourceLoader.init();
	}

	/**
	 * Clean up after the engine has finished and finish the Logs
	 */
	public static void cleanup() {
		Window.destroy();
		Logs.finish();
	}

	private static void initNatives() throws InitializationException {
		Platform platform = Platform.get();
		String path = NATIVE_DIR + "/";
		if (platform == Platform.WINDOWS)
			path += "windows";
		else if (platform == Platform.MACOSX)
			path += "macos";
		else if (platform == Platform.LINUX)
			path += "linux";
		else
			throw new InitializationException("Unsupported platform");
		System.setProperty("org.lwjgl.librarypath", new File(path).getAbsolutePath());
	}
}
