package nullEngine.graphics.gl;

import com.sun.istack.internal.Nullable;
import nullEngine.control.Application;
import nullEngine.graphics.base.Window;
import nullEngine.input.ResizeEvent;
import nullEngine.loading.Loader;
import nullEngine.util.logs.Logs;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

public class GLWindow extends Window {
	private GLCapabilities glCapabilities;

	public GLWindow(String title, int width, int height, boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode, long monitor) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;

		if (fullscreen) {
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, fullscreenVideoMode.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, fullscreenVideoMode.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, fullscreenVideoMode.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, fullscreenVideoMode.refreshRate());
			width = fullscreenVideoMode.width();
			height = fullscreenVideoMode.height();
		}

		window = GLFW.glfwCreateWindow(width, height, title, fullscreen ? monitor : MemoryUtil.NULL, MemoryUtil.NULL
		);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(0);
		glCapabilities = GL.createCapabilities();
		initCallbacks();
	}

	public GLCapabilities getGLCapabilities() {
		return glCapabilities;
	}

	public void setFullscreen(boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode, Loader loader) {
		if (this.fullscreen == fullscreen)
			return;

		this.fullscreen = fullscreen;

		int width = 0, height = 0;

		if (fullscreen) {
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, fullscreenVideoMode.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, fullscreenVideoMode.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, fullscreenVideoMode.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, fullscreenVideoMode.refreshRate());
			width = fullscreenVideoMode.width();
			height = fullscreenVideoMode.height();
		}

		width = fullscreen ? width : this.width;
		height = fullscreen ? height : this.height;

		long newWindow = GLFW.glfwCreateWindow(width, height, title,
				fullscreen ? GLFW.glfwGetPrimaryMonitor() : MemoryUtil.NULL, window);

		distributor.preResize();
		Application.get().preResize();
		loader.preContextChange();
		free();
		window = newWindow;

		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(0);
		glCapabilities = GL.createCapabilities();
		initCallbacks();
		loader.postContextChange();
		if (distributor.getListener() != null) {
			ResizeEvent event = new ResizeEvent();
			event.width = width;
			event.height = height;
			distributor.postResize(event);
			Application.get().postResize(event);
		}
		Logs.d("resized to " + width + "x" + height);
	}

	@Override
	public void resized(int newWidth, int newHeight) {

	}
}
