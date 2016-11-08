package nullEngine.util;


import org.lwjgl.glfw.GLFW;

/**
 * Utility class for time keeping
 */
public class Clock {
	/**
	 * The maximum resolution (per second) to record time
	 */
	public static final int TIME_RES = 100000;

	private double lastTime = 0;
	private double delta = 0;
	private double totalDelta = 0;
	private double timeTowardsSecond = 0;

	/**
	 * @see GLFW#glfwGetTimerFrequency()
	 */

	/**
	 * Get the current time
	 * @return The current time on the GLFW timer in units of <code>TIME_RES</code>
	 * @see #TIME_RES
	 * @see GLFW#glfwGetTime()
	 */
	public long getTime() {
		return (long) (GLFW.glfwGetTime() * TIME_RES);
	}

	/**
	 * Get the time in seconds
	 * @return The time returned by the GLFW timer
	 * @see GLFW#glfwGetTime()
	 */
	public double getTimeSeconds() {
		return GLFW.glfwGetTime();
	}

	/**
	 * Update the timer with the new time
	 * @return <code>true</code> if the time since update was last called is greater than <code>TIME_RES</code>
	 * @see #TIME_RES
	 */
	public boolean update() {
		if (lastTime == 0)
			lastTime = getTime();
		if (getTime() - lastTime > 0) {
			delta = (getTime() - lastTime) / (double) TIME_RES;
			lastTime = getTime();
			totalDelta += delta;
			timeTowardsSecond += delta;
			return true;
		} else
			return false;
	}

	/**
	 * Reset the total delta value stored in the timer
	 */
	public void resetTotalDelta() {
		totalDelta = 0;
	}

	/**
	 * Get the delta time
	 * @return The delta time since <code>update()</code> last returned <code>true</code>
	 * @see #update()
	 */
	public double getDelta() {
		return delta;
	}

	/**
	 * Get the total delta
	 * @return The delta time since <code>resetTotalDelta()</code> was last called
	 * @see #resetTotalDelta()
	 */
	public double getTotalDelta() {
		return totalDelta;
	}

	/**
	 * Checks wether a second has passed
	 * @return <code>true</code> if it has been more than a second since <code>hasSecondPassed()</code> was last called
	 */
	public boolean hasSecondPassed() {
		if (timeTowardsSecond > 1) {
			timeTowardsSecond--;
			return true;
		}
		return false;
	}

	/**
	 * Get the frame rate
	 * @return The frame rate (in terms of <code>getTotalDelta()</code>)
	 * @see #getTotalDelta()
	 */
	public int getFPS() {
		return (int) (1f / getTotalDelta());
	}
}
