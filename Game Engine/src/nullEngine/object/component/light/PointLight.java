package nullEngine.object.component.light;

import math.Vector4f;
import nullEngine.control.Layer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * A light at a point in the world (positioned at its parent GameObject)
 */
public class PointLight extends GameComponent {
	private Vector4f lightColor;
	private float squared, linear, constant;

	/**
	 * Create a new point light
	 * @param lightColor The color of the light
	 * @param squared The squared factor for attenuation
	 * @param linear The linear factor for attenuation
	 * @param constant The constant factor for attenuation
	 */
	public PointLight(Vector4f lightColor, float squared, float linear, float constant) {
		this.lightColor = lightColor;
		this.squared = squared;
		this.linear = linear;
		this.constant = constant;
	}

	/**
	 * If this is a deferred render than add ourselves to the renderer
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if (flags.get(Layer.DEFERRED_RENDER_BIT))
			renderer.add(this);
	}

	/**
	 * Do nothing
	 * @param delta  The time since update was last called
	 * @param object The object this component is attached to
	 */
	@Override
	public void update(double delta, GameObject object) {

	}

	/**
	 * Gets the light color
	 * @return The light color
	 */
	public Vector4f getLightColor() {
		return lightColor;
	}

	/**
	 * Set the light color
	 * @param lightColor The new light color
	 */
	public void setLightColor(Vector4f lightColor) {
		this.lightColor = lightColor;
	}

	/**
	 * Get the attenuation squared factor
	 * @return The attenuation squared factor
	 */
	public float getSquared() {
		return squared;
	}

	/**
	 * Set the attenuation squared factor
	 * @param squared The new attenuation squared factor
	 */
	public void setSquared(float squared) {
		this.squared = squared;
	}

	/**
	 * Get the attenuation linear factor
	 * @return The attenuation linear factor
	 */
	public float getLinear() {
		return linear;
	}

	/**
	 * Set the attenuation linear factor
	 * @param linear The new attenuation linear factor
	 */
	public void setLinear(float linear) {
		this.linear = linear;
	}

	/**
	 * Get the attenuation constant factor
	 * @return The attenuation constant factor
	 */
	public float getConstant() {
		return constant;
	}

	/**
	 * Set the attenuation constant factor
	 * @param constant The new attenuation constant factor
	 */
	public void setConstant(float constant) {
		this.constant = constant;
	}
}
