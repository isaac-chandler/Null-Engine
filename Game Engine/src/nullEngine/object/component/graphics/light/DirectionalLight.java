package nullEngine.object.component.graphics.light;

import math.Vector4f;
import nullEngine.control.physics.PhysicsEngine;
import nullEngine.control.layer.Layer;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * A component that represents an orthogonal light
 */
public class DirectionalLight extends GameComponent {
	private Vector4f lightColor;
	private Vector4f direction;

	/**
	 * Create a new directional light
	 *
	 * @param lightColor The color of light
	 * @param direction  The direction the light rays travel
	 */
	public DirectionalLight(Vector4f lightColor, Vector4f direction) {
		this.lightColor = lightColor;
		this.direction = direction.normalize(null);
	}

	/**
	 * If this is a deferred render than add ourselves to the renderer
	 *
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
	 * @param physics
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(PhysicsEngine physics, GameObject object, double delta) {

	}

	/**
	 * Get the light color
	 *
	 * @return The light color
	 */
	public Vector4f getLightColor() {
		return lightColor;
	}

	/**
	 * Set the light color
	 *
	 * @param lightColor the new light color
	 */
	public void setLightColor(Vector4f lightColor) {
		this.lightColor = lightColor;
	}

	/**
	 * Get the light direction
	 *
	 * @return the light direction
	 */
	public Vector4f getDirection() {
		return direction;
	}

	/**
	 * Set the light sirection
	 *
	 * @param direction The new light direction
	 */
	public void setDirection(Vector4f direction) {
		this.direction = direction.normalize(null);
	}
}
