package nullEngine.object.component.graphics.light;

import math.Vector4f;
import nullEngine.object.GameComponent;

/**
 * A light at a point in the world with a limited field of view, could be used for a flashlight effect
 * (positioned at its parent GameObject)
 */
public class SpotLight extends GameComponent {
	private Vector4f lightColor;
	private float squared, linear, constant;
	private float cutoff;

	/**
	 * Create a new spot light
	 *
	 * @param lightColor The color of the light
	 * @param squared    The squared factor for attenuation
	 * @param linear     The linear factor for attenuation
	 * @param constant   The constant factor for attenuation
	 * @param angle      The maximum angle in radians from the direction that the light will have an effect on
	 */
	public SpotLight(Vector4f lightColor, float squared, float linear, float constant, float angle) {
		this.lightColor = lightColor;
		this.squared = squared;
		this.linear = linear;
		this.constant = constant;
		this.cutoff = (float) Math.cos(angle);
	}

	/**
	 * Get the light color
	 *
	 * @return the light color
	 */
	public Vector4f getLightColor() {
		return lightColor;
	}

	/**
	 * Set the light color
	 *
	 * @param lightColor The new light color
	 */
	public void setLightColor(Vector4f lightColor) {
		this.lightColor = lightColor;
	}

	/**
	 * Get the attenuation squared factor
	 *
	 * @return The attenuation squared factor
	 */
	public float getSquared() {
		return squared;
	}

	/**
	 * Set the attenuation squared factor
	 *
	 * @param squared The new attenuation squared factor
	 */
	public void setSquared(float squared) {
		this.squared = squared;
	}

	/**
	 * Get the attenuation linear factor
	 *
	 * @return The attenuation linear factor
	 */
	public float getLinear() {
		return linear;
	}

	/**
	 * Set the attenuation linear factor
	 *
	 * @param linear The new attenuation linear factor
	 */
	public void setLinear(float linear) {
		this.linear = linear;
	}

	/**
	 * Get the attenuation constant factor
	 *
	 * @return The attenuation constant factor
	 */
	public float getConstant() {
		return constant;
	}

	/**
	 * Set the attenuation constant factor
	 *
	 * @param constant The new attenuation constant factor
	 */
	public void setConstant(float constant) {
		this.constant = constant;
	}

	/**
	 * Get the cosine of the maximum angle in radians from the direction that the light will have an effect on
	 *
	 * @return The cosine of the maximum angle in radians from the direction that the light will have an effect on
	 */
	public float getCutoff() {
		return cutoff;
	}

	/**
	 * Set the maximum angle in radians from the direction that the light will have an effect on
	 *
	 * @param angle The new angle
	 */
	public void setAngle(float angle) {
		this.cutoff = (float) Math.cos(angle);
	}
}
