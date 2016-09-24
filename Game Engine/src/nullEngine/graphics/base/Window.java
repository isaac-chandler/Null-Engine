package nullEngine.graphics.base;

import com.sun.istack.internal.Nullable;
import nullEngine.control.Application;
import nullEngine.exception.InitializationException;
import nullEngine.input.*;
import nullEngine.loading.Loader;
import nullEngine.util.logs.Logs;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryUtil;

import java.lang.reflect.Field;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Window {

	protected InputData inputData = new InputData();

	protected long window;

	protected int width, height;

	protected boolean fullscreen;
	protected boolean vsync = false;

	protected GLFWCharModsCallback charModsCallback;
	protected GLFWCursorEnterCallback cursorEnterCallback;
	protected GLFWCursorPosCallback cursorPosCallback;
	protected GLFWDropCallback dropCallback;
	protected GLFWFramebufferSizeCallback framebufferSizeCallback;
	protected GLFWKeyCallback keyCallback;
	protected GLFWMouseButtonCallback mouseButtonCallback;
	protected GLFWWindowCloseCallback closeCallback;
	protected GLFWWindowFocusCallback focusCallback;
	protected GLFWWindowIconifyCallback iconifyCallback;
	protected GLFWWindowPosCallback posCallback;
	protected GLFWWindowRefreshCallback refreshCallback;
	protected GLFWWindowSizeCallback sizeCallback;


	private static Window current;
	protected String title;
	protected EventDistributor distributor = new EventDistributor();

	private static Window get() {
		return current;
	}

	protected boolean ignoreNextCursorEvent = false;

	protected static final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
	protected static final DoubleBuffer doublebuffer = BufferUtils.createDoubleBuffer(1);

	private static final Map<Integer, String> errors = APIUtil.apiClassTokens(new APIUtil.TokenFilter() {
		@Override
		public boolean accept(Field field, int value) {
			return 0x10000 < value && value < 0x20000;
		}
	}, null, GLFW.class);

	private static final GLFWErrorCallback errorCallback = new GLFWErrorCallback() {
		@Override
		public void invoke(int error, long description) {
			Logs.f("GLFW error " + errors.get(error), new RuntimeException(MemoryUtil.memDecodeUTF8(description)));
		}
	};

	public static void init() throws InitializationException {
		if (GLFW.glfwInit() != GL11.GL_TRUE) {
			throw new InitializationException("Could not initialize GLFWW");
		}
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwSetErrorCallback(errorCallback);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

		errorCallback.set();
	}

	public static void destroy() {
		errorCallback.free();
		GLFW.glfwTerminate();
	}

	public long getWindow() {
		return window;
	}

	public boolean isVsync() {
		return vsync;
	}

	public void setVsync(boolean vsync) {
		GLFW.glfwSwapInterval(vsync ? 1 : 0);
		this.vsync = vsync;
	}

	public int getWidth() {
		GLFW.glfwGetFramebufferSize(window, intBuffer, null);
		return intBuffer.get(0);
	}

	public int getHeight() {
		GLFW.glfwGetFramebufferSize(window, null, intBuffer);
		return intBuffer.get(0);
	}

	public void initCallbacks() {
		charModsCallback = new GLFWCharModsCallback() {
			@Override
			public void invoke(long window, int codepoint, int mods) {
				try {
					inputData.mods(mods);
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
			public void invoke(long window, int entered) {
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
					MouseEvent event = new MouseEvent();
					event.button = -1;
					event.buttons = inputData.getButtons();
					event.x = inputData.cursorX((int) xpos);
					event.y = inputData.cursorY((int) ypos);
					event.mods = inputData.mods();
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
					inputData.mods(mods);
					KeyEvent event = new KeyEvent();
					event.key = key;
					event.mods = mods;
					if (action == GLFW.GLFW_PRESS) {
						inputData.keyPressed(key);
						distributor.keyPressed(event);
					} else if (action == GLFW.GLFW_REPEAT) {
						inputData.keyRepeated(key);
						distributor.keyRepeated(event);
					} else {
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
					inputData.mods(mods);
					MouseEvent event = new MouseEvent();
					event.button = button;
					event.mods = mods;
					event.x = inputData.getCursorX();
					event.y = inputData.getCursorY();

					if (action == GLFW.GLFW_PRESS) {
						inputData.mousePressed(button);
						event.buttons = inputData.getButtons();
						distributor.mousePressed(event);
					} else {
						inputData.mouseReleased(button);
						event.buttons = inputData.getButtons();
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
			public void invoke(long window, int focused) {
				try {

				} catch (Exception e) {
					Logs.e(e);
				}
			}
		}.set(window);
		iconifyCallback = new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long window, int iconified) {
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
						resized(width_, height_);
						if (!isFullscreen()) {
							width = width_;
							height = height_;
						}
						if (distributor.getListener() != null) {
							ResizeEvent event = new ResizeEvent();
							event.width = width_;
							event.height = height_;
							distributor.preResize();
							Application.get().preResize();
							distributor.postResize(event);
							Application.get().postResize(event);
						}
						Logs.d("resized to " + width_ + "x" + height_);
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

	public static List<GLFWVidMode> getFullscreenVideoModes() {
		PointerBuffer monitors = GLFW.glfwGetMonitors();

		ArrayList<GLFWVidMode> modes = new ArrayList<GLFWVidMode>();

		for (int i = 0; i < monitors.capacity(); i++) {
			GLFWVidMode.Buffer vidModes = GLFW.glfwGetVideoModes(monitors.get(i));
			for (int j = 0; j < vidModes.capacity(); j++) {
				modes.add(vidModes.get(j));
			}
		}

		return modes;
	}

	public static ArrayList<GLFWVidMode> getFullscreenVideoModes(long monitor) {
		ArrayList<GLFWVidMode> modes = new ArrayList<GLFWVidMode>();

		GLFWVidMode.Buffer vidModes = GLFW.glfwGetVideoModes(monitor);
		for (int i = 0; i < vidModes.capacity(); i++) {
			modes.add(vidModes.get(i));
		}

		return modes;
	}

	public static GLFWVidMode getBestFullscreenVideoMode(ArrayList<GLFWVidMode> vidModes) {
		int bestWidth = 0;
		int bestHeight = 0;

		ArrayList<GLFWVidMode> sameSize = new ArrayList<GLFWVidMode>();

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
		ArrayList<GLFWVidMode> sameRefresh = new ArrayList<GLFWVidMode>();

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

	public static GLFWVidMode getWorstFullscreenVideoMode(ArrayList<GLFWVidMode> vidModes) {
		int worstWidth = 0;
		int worstHeight = 0;

		ArrayList<GLFWVidMode> sameSize = new ArrayList<GLFWVidMode>();

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
		ArrayList<GLFWVidMode> sameRefresh = new ArrayList<GLFWVidMode>();

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

	public void bind() {
		current = this;
		GLFW.glfwMakeContextCurrent(window);
		Input.setInputData(inputData);
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public abstract void setFullscreen(boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode, Loader loader);

	public abstract void resized(int newWidth, int newHeight);

	public long getMonitor() {
		long monitor = GLFW.glfwGetWindowMonitor(window);
		return monitor == 0 ? GLFW.glfwGetPrimaryMonitor() : monitor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
		this.title = title;
	}

	public EventDistributor getDistributor() {
		return distributor;
	}

	public boolean getCursorEnabled() {
		return GLFW.glfwGetInputMode(window, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_NORMAL;
	}

	public void setCursorEnabled(boolean enabled) {
		ignoreNextCursorEvent = true;
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, enabled ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
	}
}
