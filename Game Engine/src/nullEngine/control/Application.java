package nullEngine.control;

import com.sun.istack.internal.Nullable;
import nullEngine.gl.renderer.MasterRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import nullEngine.exception.InitializationException;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.Window;
import nullEngine.gl.model.Quad;
import nullEngine.loading.Loader;
import nullEngine.util.Clock;
import nullEngine.util.logs.Logs;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class Application {

	private Window window;
	private MasterRenderer renderer;
	private float lastFrameTime;

	private Loader loader;

	private Clock clock = new Clock();

	private boolean running = false;
	private Throwable exception = null;

	private HashMap<Integer, State> states = new HashMap<Integer, State>();
	private int nextStateID = 1;
	private State currentState;

	private static Application current;

	public static Application get() {
		return current;
	}

	public Application(int width, int height, boolean fullscreen, String title) throws InitializationException {
		Logs.setApplication(this);

		GLFWVidMode displayMode = null;
		if (fullscreen) {
			displayMode = Window.getBestFullscreenVideoMode(Window.getFullscreenVideoModes(GLFW.glfwGetPrimaryMonitor()));
		}

		window = new Window(title, width, height, fullscreen, displayMode, GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwShowWindow(window.getWindow());

		loader = new Loader(this);
		renderer = new MasterRenderer();
		renderer.viewport(0, 0, window.getWidth(), window.getHeight());
		renderer.init();

		Quad.setup(loader);
	}

	public int addState(State state) {
		states.put(nextStateID, state);
		return nextStateID++;
	}

	public void setState(int state) {
		currentState = states.get(state);
		window.getDistributor().setListener(currentState);
	}

	public int getHeight() {
		return window.getHeight();
	}

	public Throwable start() {
		running = true;
		try {
			while (running) {
				if (clock.update()) {
					if (GLFW.glfwWindowShouldClose(window.getWindow()) == GLFW.GLFW_TRUE || GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
						running = false;
						continue;
					}

					if (GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_F11) == GLFW.GLFW_PRESS) {
						setFullscreen(!isFullscreen(), Window.getBestFullscreenVideoMode(Window.getFullscreenVideoModes(window.getMonitor())));
						GLFW.glfwShowWindow(window.getWindow());
					}

					GLFW.glfwPollEvents();
					update(clock.getDelta());

					if (clock.getTotalDelta() > 0.0165) {
						render();
						clock.resetTotalDelta();
					}
				}
			}
		} catch (Throwable e) {
			exception = e;
		}
		return exception;
	}

	public void render() {
		float start = clock.getTimeSeconds();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		currentState.render(renderer);
		lastFrameTime = clock.getTimeSeconds() - start;
		GLFW.glfwSwapBuffers(window.getWindow());
	}

	public void update(float delta) {
		currentState.update(delta);
	}

	public void stop(Throwable e) {
		exception = e;
		running = false;
	}

	public void stop() {
		stop(null);
	}

	public void destroy() {
		Logs.d("Cleaning up");
		stop();
		loader.cleanup();
		renderer.cleanup();
		window.free();
		Logs.d("Cleanup successful");
		Logs.finish();
	}

	public void carefulDestroy() {
		Logs.w("Starting safe destroy");
		try {
			stop();
		} catch (Exception e) {
			Logs.e("Failed game stop call", e);
		}

		try {
			loader.cleanup();
		} catch (Exception e) {
			Logs.e("Failed loader cleanup", e);
		}

		try {
			renderer.cleanup();
		} catch (Exception e) {
			Logs.e("Failed renderer cleanup", e);
		}

		try {
			window.free();
		} catch (Exception e) {
			Logs.e("Failed to destroy window", e);
		}

		try {
			Logs.finish();
		} catch (Exception e) {
			Logs.e("Failed to destroy display", e);
		}
	}

	public State getCurrentState() {
		return currentState;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public Loader getLoader() {
		return loader;
	}

	public boolean isFullscreen() {
		return window.isFullscreen();
	}

	public void setFullscreen(boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode) {
		window.setFullscreen(fullscreen, fullscreenVideoMode, loader);
		renderer.viewport(0, 0, getWidth(), getHeight());
		renderer.init();
	}

	public Window getWindow() {
		return window;
	}

	public void bind() {
		current = this;
		Logs.setApplication(this);
		window.bind();
		renderer.bind();
	}

	public int getWidth() {
		return window.getWidth();
	}

	public float getLastFrameTime() {
		return lastFrameTime;
	}
}
