package nullEngine.util;


import org.lwjgl.glfw.GLFW;

public class Clock {
	private static final int timeRes = 100000;

	private double lastTime = 0;
	private double delta = 0;
	private double totalDelta = 0;
	private double timeTowardsSecond = 0;

	public long getTime() {
		return (long) (GLFW.glfwGetTime() * timeRes);
	}

	public double getTimeSeconds() {
		return GLFW.glfwGetTime();
	}

	public boolean update() {
		if (lastTime == 0)
			lastTime = getTime();
		if (getTime() - lastTime > 0) {
			delta = (getTime() - lastTime) / (double) timeRes;
			lastTime = getTime();
			totalDelta += delta;
			timeTowardsSecond += delta;
			return true;
		} else
			return false;
	}

	public void resetTotalDelta() {
		totalDelta = 0;
	}

	public double getDelta() {
		return delta;
	}

	public double getTotalDelta() {
		return totalDelta;
	}

	public boolean hasSecondPassed() {
		if (timeTowardsSecond > 1) {
			timeTowardsSecond--;
			return true;
		}
		return false;
	}

	public int getFPS() {
		return (int) (1f / getTotalDelta());
	}
}
