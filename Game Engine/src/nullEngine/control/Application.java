package nullEngine.control;

import com.sun.istack.internal.Nullable;
import nullEngine.exception.InitializationException;
import nullEngine.gl.Window;
import nullEngine.gl.framebuffer.Framebuffer2D;
import nullEngine.gl.model.Quad;
import nullEngine.gl.renderer.MasterRenderer;
import nullEngine.gl.renderer.Renderer;
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
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Application {

	private Window window;
	private MasterRenderer renderer;
	private float lastFrameTime;

	private Loader loader;

	private Thread updateThread;

	private Clock renderClock = new Clock();
	private Clock updateClock;

	private ReadWriteLock runningLock = new ReentrantReadWriteLock();
	private volatile boolean running = false;
	private Throwable exception = null;

	private HashMap<Integer, State> states = new HashMap<Integer, State>();
	private int nextStateID = 1;
	private State currentState;

	private static Application current;
	private boolean screenshot;

	public Framebuffer2D getRenderTarget() {
		return renderTarget;
	}

	private Framebuffer2D renderTarget = null;

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
		window.setVsync(false);

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
						try {
							if (window.getDistributor() instanceof ThreadedEventDistributor)
								((ThreadedEventDistributor) window.getDistributor()).passEvents();
							update(updateClock.getDelta());
						} catch (Throwable e) {
							exception = e;
							runningLock.writeLock().lock();
							running = false;
							runningLock.writeLock().unlock();
						}
					}
				}
			} catch (Throwable e) {
				exception = e;
			}
			runningLock.writeLock().lock();
			running = false;
			runningLock.writeLock().unlock();
		}
	};

	//FIXME add thread safety to GameObject and GameComponent
	public Throwable start() {
		runningLock.writeLock().lock();
		running = true;
		runningLock.writeLock().unlock();
		try {
			Thread.currentThread().setName("RENDER");
			updateThread = new Thread(updateThreadRunnable, "UPDATE");
			updateThread.start();
			while (true) {
				runningLock.readLock().lock();
				if (!running) {
					runningLock.readLock().unlock();
					break;
				}
				runningLock.readLock().unlock();
				if (renderClock.update()) {
					try {
						GLFW.glfwPollEvents();
						if (GLFW.glfwWindowShouldClose(window.getWindow()) == GLFW.GLFW_TRUE || GLFW.glfwGetKey(window.getWindow(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
							stop();
							continue;
						}

						render();
					} catch (Throwable e) {
						exception = e;
						runningLock.writeLock().lock();
						running = false;
						runningLock.writeLock().unlock();
					}
				}
			}
		} catch (Throwable e) {
			exception = e;
		}
		runningLock.writeLock().lock();
		running = false;
		runningLock.writeLock().unlock();
		return exception;
	}

	public void render() {
		float start = renderClock.getTimeSeconds();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		currentState.render(renderer);
		GLFW.glfwSwapBuffers(window.getWindow());
		lastFrameTime = renderClock.getTimeSeconds() - start;
		if (screenshot) {
			screenshotImpl();
		}
	}

	public void update(float delta) {
		currentState.update(delta);
	}

	public void stop(Throwable e) {
		exception = e;
		runningLock.writeLock().lock();
		running = false;
		runningLock.writeLock().unlock();
	}

	public void stop() {
		stop(null);
	}

	public void destroy() {
		Logs.d("Cleaning up");
		stop();
		loader.cleanup();
		ResourceManager.deleteAll();
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

	public float getScreenCoordX(float x) {
		return 2 * x / getWidth() - 1;
	}

	public float getScreenCoordY(float y) {
		return 2 * y / getHeight() - 1;
	}

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
		} catch (java.io.IOException e) {
			Logs.e(e);
		}
	}

	public void preResize() {

	}

	public void postResize(PostResizeEvent event) {

	}

	public boolean getCursorEnabled() {
		return window.getCursorEnabled();
	}

	public void setCursorEnabled(boolean enabled) {
		window.setCursorEnabled(enabled);
	}
}
