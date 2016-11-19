package nullEngine.gl;

import com.sun.istack.internal.Nullable;
import nullEngine.control.Application;
import nullEngine.exception.InitializationException;
import nullEngine.input.CharEvent;
import nullEngine.input.Event;
import nullEngine.input.EventDistributor;
import nullEngine.input.Input;
import nullEngine.input.InputData;
import nullEngine.input.KeyEvent;
import nullEngine.input.MouseEvent;
import nullEngine.input.PostResizeEvent;
import nullEngine.input.ThreadedEventDistributor;
import nullEngine.util.logs.Logs;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Wrapper for the GLFW windowing system
 */
public class Window {

	private InputData inputData = new InputData();

	private long window;

	private int width, height;

	private boolean fullscreen;
	private boolean vsync = false;
	private boolean cursorEnabled = true;

	private GLFWCharModsCallback charModsCallback;
	private GLFWCursorEnterCallback cursorEnterCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWDropCallback dropCallback;
	private GLFWFramebufferSizeCallback framebufferSizeCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWWindowCloseCallback closeCallback;
	private GLFWWindowFocusCallback focusCallback;
	private GLFWWindowIconifyCallback iconifyCallback;
	private GLFWWindowPosCallback posCallback;
	private GLFWWindowRefreshCallback refreshCallback;
	private GLFWWindowSizeCallback sizeCallback;

	private GLCapabilities glCapabilities;


	private static Window current;
	private String title;
	private EventDistributor distributor = new ThreadedEventDistributor();

	private boolean ignoreNextCursorEvent = false;

	private static final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
	private static final DoubleBuffer doublebuffer = BufferUtils.createDoubleBuffer(1);

	private static final GLFWErrorCallback errorCallback = new GLFWErrorCallback() {

		@Override
		public void invoke(int error, long description) {
			Logs.f("GLFW error " + String.format("0x%x", error), new RuntimeException(MemoryUtil.memUTF8(description)));
		}

	};

	/**
	 * Set up the windowing system
	 *
	 * @throws InitializationException If GLFW failed to initialize
	 */
	public static void init() throws InitializationException {
		if (!GLFW.glfwInit())
			throw new InitializationException("Could not initialize GLFWW");

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwSetErrorCallback(errorCallback);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);

		errorCallback.set();
	}

	/**
	 * Shut down the windowing system
	 */
	public static void destroy() {
		errorCallback.free();
		GLFW.glfwTerminate();
	}

	/**
	 * Create a window
	 *
	 * @param title               The window title
	 * @param width               The window width
	 * @param height              The window height
	 * @param fullscreen          Wether it should be a fullscreen window
	 * @param fullscreenVideoMode The video mode (ignored if fullscreen is <code>false</code>)
	 * @param monitor             The monitor to put the window on (ignored if fullscreen is <code>false</code>)
	 */
	public Window(String title, int width, int height, boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode, long monitor) {
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
		setCursorEnabled(cursorEnabled);
	}

	/**
	 * Get the GLCapabilities
	 *
	 * @return The OpenGL capabilities
	 */
	public GLCapabilities getGLCapabilities() {
		return glCapabilities;
	}

	/**
	 * Get the window handle
	 *
	 * @return The window handle
	 */
	public long getWindow() {
		return window;
	}

	/**
	 * Get wether vertical sync is enabled
	 *
	 * @return Wether vertical sync is enabled
	 */
	public boolean isVsync() {
		return vsync;
	}

	/**
	 * Set wether vertical sync is enabled
	 *
	 * @param vsync Wether vertical sync is enabled
	 */
	public void setVsync(boolean vsync) {
		GLFW.glfwSwapInterval(vsync ? 1 : 0);
		this.vsync = vsync;
	}

	/**
	 * Get the width
	 *
	 * @return The width of the drawing area
	 */
	public int getWidth() {
		GLFW.glfwGetFramebufferSize(window, intBuffer, null);
		return intBuffer.get(0);
	}

	/**
	 * Get the height
	 *
	 * @return The height of the drawing area
	 */
	public int getHeight() {
		GLFW.glfwGetFramebufferSize(window, null, intBuffer);
		return intBuffer.get(0);
	}

	private void initCallbacks() {
		charModsCallback = new GLFWCharModsCallback() {
			@Override
			public void invoke(long window, int codepoint, int mods) {
				try {
					inputData.setMods(mods);
					CharEvent event = new CharEvent();
					event.character = (char) codepoint;
					event.mods = mods;
					distributor.charTyped(event);
				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		cursorEnterCallback = new GLFWCursorEnterCallback() {
			@Override
			public void invoke(long window, boolean entered) {
				try {
					GLFW.glfwGetCursorPos(window, doublebuffer, null);
					inputData.setCursorX((int) doublebuffer.get(0));
					GLFW.glfwGetCursorPos(window, null, doublebuffer);
					inputData.setCursorY((int) doublebuffer.get(0));
				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		cursorPosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				try {
					MouseEvent event = new MouseEvent(Event.EventType.MOUSE_MOVED);
					event.button = -1;
					event.x = inputData.cursorX((int) xpos);
					event.y = inputData.cursorY((int) ypos);
					event.mods = inputData.getMods();
					if (ignoreNextCursorEvent) {
						Logs.d("Ignored cursor event");
						ignoreNextCursorEvent = false;
					} else
						distributor.mouseMoved(event);
				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		dropCallback = new GLFWDropCallback() {
			@Override
			public void invoke(long window, int count, long names) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width_, int height_) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				try {
					inputData.setMods(mods);
					if (action == GLFW.GLFW_PRESS) {
						KeyEvent event = new KeyEvent(Event.EventType.KEY_PRESSED);
						event.key = key;
						event.mods = mods;
						inputData.keyPressed(key);
						distributor.keyPressed(event);
					} else if (action == GLFW.GLFW_REPEAT) {
						KeyEvent event = new KeyEvent(Event.EventType.KEY_REPEATED);
						event.key = key;
						event.mods = mods;
						inputData.keyRepeated(key);
						distributor.keyRepeated(event);
					} else {
						KeyEvent event = new KeyEvent(Event.EventType.KEY_RELEASED);
						event.key = key;
						event.mods = mods;
						inputData.keyReleased(key);
						distributor.keyRepeated(event);
					}
				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				try {
					inputData.setMods(mods);

					if (action == GLFW.GLFW_PRESS) {
						MouseEvent event = new MouseEvent(Event.EventType.MOUSE_PRESSED);
						event.button = button;
						event.mods = mods;
						event.x = inputData.getCursorX();
						event.y = inputData.getCursorY();
						inputData.mousePressed(button);
						distributor.mousePressed(event);
					} else {
						MouseEvent event = new MouseEvent(Event.EventType.MOUSE_RELEASED);
						event.button = button;
						event.mods = mods;
						event.x = inputData.getCursorX();
						event.y = inputData.getCursorY();
						inputData.mouseReleased(button);
						distributor.mouseReleased(event);
					}
				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		closeCallback = new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long window) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		focusCallback = new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long window, boolean focused) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		iconifyCallback = new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long window, boolean iconified) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		posCallback = new GLFWWindowPosCallback() {
			@Override
			public void invoke(long window, int xpos, int ypos) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		refreshCallback = new GLFWWindowRefreshCallback() {
			@Override
			public void invoke(long window) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		sizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width_, int height_) {
				try {
					if (width_ != 0 && height_ != 0) {
						if (!isFullscreen()) {
							width = width_;
							height = height_;
						}
						if (distributor.listener != null) {
							PostResizeEvent event = new PostResizeEvent();
							event.width = width_;
							event.height = height_;
							distributor.preResize();
							Application.get().preResize();
							distributor.postResize(event);
							Application.get().postResize(event);
						}
						Logs.i("resized to " + width_ + "x" + height_);
					}
				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
	}

	private void destroyCallbacks() {
		charModsCallback.free();
		cursorEnterCallback.free();
		cursorPosCallback.free();
		dropCallback.free();
		framebufferSizeCallback.free();
		keyCallback.free();
		mouseButtonCallback.free();
		closeCallback.free();
		focusCallback.free();
		iconifyCallback.free();
		posCallback.free();
		refreshCallback.free();
		sizeCallback.free();
	}

	public void setDistributor(EventDistributor distributor) {
		this.distributor = distributor;
	}

	public void free() {
		GLFW.glfwHideWindow(window);
		destroyCallbacks();
		GLFW.glfwDestroyWindow(window);
	}

	/**
	 * Get all of the available video modes
	 *
	 * @return The video modes
	 */
	public static ArrayList<GLFWVidMode> getFullscreenVideoModes() {
		PointerBuffer monitors = GLFW.glfwGetMonitors();

		ArrayList<GLFWVidMode> modes = new ArrayList<>();

		for (int i = 0; i < monitors.capacity(); i++) {
			GLFWVidMode.Buffer vidModes = GLFW.glfwGetVideoModes(monitors.get(i));
			for (int j = 0; j < vidModes.capacity(); j++) {
				modes.add(vidModes.get(j));
			}
		}

		return modes;
	}

	/**
	 * Get all of the available video modes from a specific monitor
	 *
	 * @param monitor The monitor
	 * @return The video modes
	 */
	public static ArrayList<GLFWVidMode> getFullscreenVideoModes(long monitor) {
		ArrayList<GLFWVidMode> modes = new ArrayList<>();

		GLFWVidMode.Buffer vidModes = GLFW.glfwGetVideoModes(monitor);
		for (int i = 0; i < vidModes.capacity(); i++) {
			modes.add(vidModes.get(i));
		}

		return modes;
	}

	/**
	 * Get the best video mode
	 *
	 * @param vidModes The video modes to choose from
	 * @return The video mode
	 */
	public static GLFWVidMode getBestFullscreenVideoMode(ArrayList<GLFWVidMode> vidModes) {
		int bestWidth = 0;
		int bestHeight = 0;

		ArrayList<GLFWVidMode> sameSize = new ArrayList<>();

		for (GLFWVidMode vidMode : vidModes) {
			if (vidMode.width() < bestWidth || vidMode.height() < bestHeight)
				continue;

			if (vidMode.width() > bestWidth) {
				sameSize.clear();
				bestWidth = vidMode.width();
			}
			if (vidMode.height() > bestHeight) {
				sameSize.clear();
				bestHeight = vidMode.height();
			}
			sameSize.add(vidMode);
		}

		int bestRefresh = 0;
		ArrayList<GLFWVidMode> sameRefresh = new ArrayList<>();

		for (GLFWVidMode vidMode : sameSize) {
			if (vidMode.refreshRate() < bestRefresh)
				continue;

			if (vidMode.refreshRate() > bestRefresh) {
				sameRefresh.clear();
				bestRefresh = vidMode.refreshRate();
			}
			sameRefresh.add(vidMode);
		}

		GLFWVidMode bestVidMode = sameRefresh.get(0);

		for (GLFWVidMode vidMode : sameRefresh) {
			if (vidMode.redBits() < bestVidMode.redBits() || vidMode.greenBits() < bestVidMode.greenBits() || vidMode.blueBits() < bestVidMode.blueBits())
				continue;
			bestVidMode = vidMode;
		}

		return bestVidMode;
	}

	/**
	 * Get the worst video mode
	 *
	 * @param vidModes The video modes to choose from
	 * @return The video mode
	 */
	public static GLFWVidMode getWorstFullscreenVideoMode(ArrayList<GLFWVidMode> vidModes) {
		int worstWidth = 0;
		int worstHeight = 0;

		ArrayList<GLFWVidMode> sameSize = new ArrayList<>();

		for (GLFWVidMode vidMode : vidModes) {
			if (vidMode.width() > worstWidth || vidMode.height() > worstHeight)
				continue;

			if (vidMode.width() < worstWidth) {
				sameSize.clear();
				worstWidth = vidMode.width();
			}
			if (vidMode.height() < worstHeight) {
				sameSize.clear();
				worstHeight = vidMode.height();
			}
			sameSize.add(vidMode);
		}

		int worstRefresh = 0;
		ArrayList<GLFWVidMode> sameRefresh = new ArrayList<>();

		for (GLFWVidMode vidMode : sameSize) {
			if (vidMode.refreshRate() > worstRefresh)
				continue;

			if (vidMode.refreshRate() < worstRefresh) {
				sameRefresh.clear();
				worstRefresh = vidMode.refreshRate();
			}
			sameRefresh.add(vidMode);
		}

		GLFWVidMode worstVidMode = sameRefresh.get(0);

		for (GLFWVidMode vidMode : sameRefresh) {
			if (vidMode.redBits() > worstVidMode.redBits() || vidMode.greenBits() > worstVidMode.greenBits() || vidMode.blueBits() > worstVidMode.blueBits())
				continue;
			worstVidMode = vidMode;
		}

		return worstVidMode;
	}

	/**
	 * Make this window the current window for input and OpenGL
	 */
	public void bind() {
		current = this;
		GLFW.glfwMakeContextCurrent(window);
		Input.setInputData(inputData);
	}

	/**
	 * Get wether this window is fullscreen
	 *
	 * @return Wether this window is fullscreen
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * Set wether this window is fullscreen
	 *
	 * @param fullscreen          Wether this window is fullscreen
	 * @param fullscreenVideoMode The video mode (ignored if fullscreen is <code>false</code>)
	 */
	public void setFullscreen(boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode) {
		this.fullscreen = fullscreen;

		int width, height;

		if (distributor.listener != null)
			distributor.preResize();
		Application.get().preResize();

		ignoreNextCursorEvent = true;
		if (fullscreen) {
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, fullscreenVideoMode.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, fullscreenVideoMode.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, fullscreenVideoMode.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, fullscreenVideoMode.refreshRate());

			width = fullscreenVideoMode.width();
			height = fullscreenVideoMode.height();

			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, fullscreenVideoMode.refreshRate());
			GLFW.glfwSwapInterval(vsync ? 1 : 0);
		} else {
			width = this.width;
			height = this.height;

			GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2, width, height, 0);

			GLFW.glfwSwapInterval(vsync ? 1 : 0);
		}

		PostResizeEvent event = new PostResizeEvent();
		event.width = width;
		event.height = height;

		if (distributor.listener != null)
			distributor.postResize(event);
		Application.get().postResize(event);

//		long newWindow = GLFW.glfwCreateWindow(width, height, title,
//				fullscreen ? GLFW.glfwGetPrimaryMonitor() : MemoryUtil.NULL, window);
//
//		distributor.preResize();
//		Application.get().preResize();
//		loader.preContextChange();
//		free();
//		window = newWindow;
//
//		GLFW.glfwMakeContextCurrent(window);
//		GLFW.glfwSwapInterval(vsync ? 1 : 0);
//		glCapabilities = GL.createCapabilities();
//		initCallbacks();
//		loader.postContextChange();
//		if (distributor.getListener() != null) {
//			PostResizeEvent event = new PostResizeEvent();
//			event.width = width;
//			event.height = height;
//			distributor.postResize(event);
//			Application.get().postResize(event);
//		}
//		setCursorEnabled(cursorEnabled);
//		GLFW.glfwShowWindow(window);
//		return true;


		Logs.d("resized to " + width + "x" + height);
	}

	/**
	 * Get the monitor this window is on
	 * @return The monitor
	 */
	public long getMonitor() {
		long monitor = GLFW.glfwGetWindowMonitor(window);
		return monitor == 0 ? GLFW.glfwGetPrimaryMonitor() : monitor;
	}

	/**
	 * Get this windows title
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set this windows title
	 * @param title The new title
	 */
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
		this.title = title;
	}

	/**
	 * Get the event distributor
	 * @return The event distributor
	 */
	public EventDistributor getDistributor() {
		return distributor;
	}

	/**
	 * Get wether the cursor is enabled
	 * @return Wether the cursor is enabled
	 */
	public boolean getCursorEnabled() {
		return GLFW.glfwGetInputMode(window, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_NORMAL;
	}

	/**
	 * Set wether the cursor is enabled
	 * @param enabled Wether the cursor is enabled
	 */
	public void setCursorEnabled(boolean enabled) {
		if (enabled != this.cursorEnabled) {
			this.cursorEnabled = enabled;
			ignoreNextCursorEvent = true;
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, cursorEnabled ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
		}
	}
}
