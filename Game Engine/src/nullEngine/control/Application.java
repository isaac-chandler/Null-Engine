package nullEngine.control;

import com.sun.istack.internal.Nullable;
import nullEngine.exception.InitializationException;
import nullEngine.gl.Window;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.model.Quad;
import nullEngine.gl.renderer.MasterRenderer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.input.Event;
import nullEngine.input.PostResizeEvent;
import nullEngine.input.ThreadedEventDistributor;
import nullEngine.loading.Loader;
import nullEngine.managing.ResourceManager;
import nullEngine.util.Clock;
import nullEngine.util.logs.Logs;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Application {

	private Window window;
	private MasterRenderer renderer;

	private Loader loader;

	private Thread updateThread;
	private Thread renderThread;

	private Clock renderClock = new Clock();
	private Clock updateClock;
	private double lastFrameTime;
	private double lastUpdateTime;

	private ReadWriteLock runningLock = new ReentrantReadWriteLock();
	private volatile boolean running = false;
	private Throwable exception = null;

	private Map<Integer, State> states = new HashMap<>();
	private int nextStateID = 1;
	private State currentState;

	private static Application current;
	private boolean screenshot;

	private byte changeCursorEnabledRequest = 0;
	private boolean changeScreenStateRequest = false;
	private boolean changeScreenStateFullscreen;
	private GLFWVidMode changeScreenStateVidMode;

	/**
	 * The render target
	 * @return The render target
	 */
	public Framebuffer2D getRenderTarget() {
		return renderTarget;
	}

	private Framebuffer2D renderTarget = null;

	/**
	 * Get the current application
	 * @return The current application
	 */
	public static Application get() {
		return current;
	}

	/**
	 * Create a new application
	 * @param width The width
	 * @param height The height
	 * @param fullscreen Wether the window should be fullscreen
	 * @param title The title
	 * @throws InitializationException If the application fails to initialize
	 */
	public Application(int width, int height, boolean fullscreen, String title) throws InitializationException {
		Logs.setApplication(this);

		GLFWVidMode displayMode = null;
		if (fullscreen) {
			displayMode = Window.getBestFullscreenVideoMode(Window.getFullscreenVideoModes(GLFW.glfwGetPrimaryMonitor()));
		}

		window = new Window(title, width, height, fullscreen, displayMode, GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwShowWindow(window.getWindow());
		window.setVsync(false);

		loader = new Loader(this);
		renderer = new MasterRenderer();
		renderer.viewport(0, 0, window.getWidth(), window.getHeight());
		renderer.init();

		Quad.setup(loader);
	}

	/**
	 * Add a state
	 * @param state The state
	 * @return The state index
	 */
	public int addState(State state) {
		states.put(nextStateID, state);
		return nextStateID++;
	}

	/**
	 * Set the state
	 * @param state The state id
	 */
	public void setState(int state) {
		currentState = states.get(state);
		window.getDistributor().listener = currentState;
	}

	/**
	 * Get the height
	 * @return The height
	 */
	public int getHeight() {
		return window.getHeight();
	}

	private final Runnable updateThreadRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				updateClock = new Clock();
				while (true) {
					runningLock.readLock().lock();
					if (!running) {
						runningLock.readLock().unlock();
						break;
					}
					runningLock.readLock().unlock();

					if (updateClock.update()) {
						double start = updateClock.getTimeSeconds();
						if (window.getDistributor() instanceof ThreadedEventDistributor)
							((ThreadedEventDistributor) window.getDistributor()).passEvents();
						update(updateClock.getDelta());
						lastUpdateTime = updateClock.getTimeSeconds() - start;
					}
				}
			} catch (Throwable e) {
				exception = e;
			} finally {
				runningLock.writeLock().lock();
				running = false;
				runningLock.writeLock().unlock();
			}
		}
	};

	/**
	 * Start the application
	 * @return The exception that was thrown or <code>null</code> if the application finsihed normally
	 */
	public Throwable start() {
		try {
			runningLock.writeLock().lock();
			running = true;
			runningLock.writeLock().unlock();
			Thread.currentThread().setName("RENDER");
			renderThread = Thread.currentThread();
			updateThread = new Thread(updateThreadRunnable, "UPDATE");
			updateThread.start();
			while (true) {
				runningLock.readLock().lock();
				if (!running) {
					runningLock.readLock().unlock();
					break;
				}
				runningLock.readLock().unlock();

				GLFW.glfwPollEvents();
				if (GLFW.glfwWindowShouldClose(window.getWindow()) || GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
					stop();
				}

				if (changeScreenStateRequest)
					setFullscreenImpl();

				if (changeCursorEnabledRequest != 0) {
					setCursorEnabledImpl();
				}

				render();
			}
		} catch (Throwable e) {
			exception = e;
		} finally {
			runningLock.writeLock().lock();
			running = false;
			runningLock.writeLock().unlock();
		}
		return exception;
	}

	/**
	 * Add an event to the queue
	 * @param event The event
	 */
	public void queueEvent(Event event) {
		if (window.getDistributor() instanceof ThreadedEventDistributor)
			((ThreadedEventDistributor) window.getDistributor()).queueEvent(event);
	}

	private void render() {
		double start = renderClock.getTimeSeconds();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		currentState.render(renderer);
		GLFW.glfwSwapBuffers(window.getWindow());
		lastFrameTime = renderClock.getTimeSeconds() - start;
		if (screenshot) {
			screenshotImpl();
		}
	}

	private void update(double delta) {
		currentState.update(delta);
	}

	/**
	 * Stop the application
	 * @param e The exception that was thrown
	 */
	public void stop(Throwable e) {
		exception = e;
		runningLock.writeLock().lock();
		running = false;
		runningLock.writeLock().unlock();
	}

	/**
	 * Stop the application
	 */
	public void stop() {
		stop(null);
	}

	/**
	 * Clean up after the application
	 */
	public void destroy() {
		Logs.d("Cleaning up");
		stop();
		ResourceManager.deleteAll();
		renderer.cleanup();
		window.free();
		Logs.d("Cleanup successful");
		Logs.finish();
	}

	/**
	 * Do a careful destroy
	 */
	public void carefulDestroy() {
		Logs.w("Starting safe destroy");
		try {
			stop();
		} catch (Exception e) {
			Logs.e("Failed game stop call", e);
		}

		try {
			ResourceManager.deleteAll();
		} catch (Exception e) {
			Logs.e("Failed resource manager cleanup", e);
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

	/**
	 * Get the current state
	 * @return The current state
	 */
	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Get the renderer
	 * @return The renderer
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	/**
	 * Get the loader
	 * @return The loader
	 */
	public Loader getLoader() {
		return loader;
	}

	/**
	 * Get wether the window is fullscreen
	 * @return Wether the window is fullscreen
	 */
	public boolean isFullscreen() {
		return window.isFullscreen();
	}

	/**
	 * Set wether the window is fullscreen
	 * @param fullscreen Wether the window is fullscreen
	 * @param fullscreenVideoMode The video mode (ignored if fullscreen is <code>false</code>
	 */
	public void setFullscreen(boolean fullscreen, @Nullable GLFWVidMode fullscreenVideoMode) {
		changeScreenStateRequest = true;
		changeScreenStateFullscreen = fullscreen;
		changeScreenStateVidMode = fullscreenVideoMode;
	}

	private void setFullscreenImpl() {
		changeScreenStateRequest = false;
		window.setFullscreen(changeScreenStateFullscreen, changeScreenStateVidMode);
		renderer.viewport(0, 0, getWidth(), getHeight());
		renderer.init();
	}

	/**
	 * Get the window
	 * @return The window
	 */
	public Window getWindow() {
		return window;
	}

	/**
	 * Set the current application to this
	 */
	public void bind() {
		current = this;
		Logs.setApplication(this);
		window.bind();
		renderer.bind();
	}

	/**
	 * Get the width
	 * @return The width
	 */
	public int getWidth() {
		return window.getWidth();
	}

	/**
	 * Get the last frame time
	 * @return The last frame time
	 */
	public double getLastFrameTime() {
		return lastFrameTime;
	}

	/**
	 * Get the last update time
	 * @return The last update time
	 */
	public double getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * Convert screen coords to OpenGL coords
	 * @param x The x
	 * @return The x in OpenGL space
	 */
	public float getScreenCoordX(float x) {
		return 2 * x / getWidth() - 1;
	}

	/**
	 * Convert screen coords to OpenGL coords
	 * @param y The y
	 * @return The y in OpenGL space
	 */
	public float getScreenCoordY(float y) {
		return 2 * y / getHeight() - 1;
	}

	/**
	 * Request a screenshot
	 */
	public void screenshot() {
		Logs.d("Screenshot requested");
		if (screenshot) {
			Logs.w("Unnecessary screenshot request");
		}
		screenshot = true;
	}

	private void screenshotImpl() {
		screenshot = false;
		File out = new File("saves/screenshot.png");
		int i = 1;
		while (out.exists()) {
			out = new File("saves/screenshot" + i++ + ".png");
		}
		out.getParentFile().mkdirs();
		try {
			FileOutputStream fos = new FileOutputStream(out);

			GL11.glReadBuffer(GL11.GL_FRONT);
			ByteBuffer buf = BufferUtils.createByteBuffer(getWidth() * getHeight() * 3);
			GL11.glReadPixels(0, 0, window.getWidth(), getWidth(), GL12.GL_BGR, GL11.GL_UNSIGNED_BYTE, buf);
			BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			for (int y = getHeight() - 1; y >= 0; y--) {
				buf.get(((DataBufferByte) img.getRaster().getDataBuffer()).getData(), getWidth() * 3 * y, getWidth() * 3);
			}

			ImageIO.write(img, "PNG", fos);
			fos.close();
			Logs.d("Took screenshot");
		} catch (IOException e) {
			Logs.e(e);
		}
	}

	/**
	 * Does nothing
	 */
	public void preResize() {

	}

	/**
	 * Does nothing
	 * @param event The event
	 */
	public void postResize(PostResizeEvent event) {

	}

	/**
	 * Get wether the cursor is enabled
	 * @return Wether the cursor is enabled
	 */
	public boolean getCursorEnabled() {
		return window.getCursorEnabled();
	}

	/**
	 * Set wehter the cursor is enabled
	 * @param enabled Wether the cursor is enabled
	 */
	public void setCursorEnabled(boolean enabled) {
		this.changeCursorEnabledRequest = enabled ? (byte) 0b11 : (byte) 0b01;
	}

	private void setCursorEnabledImpl() {
		window.setCursorEnabled((changeCursorEnabledRequest & (byte) 0b10) == (byte) 0b10);
	}

	/**
	 * Get the bes available vide mode
	 * @return The best video mode
	 */
	public GLFWVidMode getBestFullscreenVideoMode() {
		return Window.getBestFullscreenVideoMode(Window.getFullscreenVideoModes(window.getMonitor()));
	}
}
