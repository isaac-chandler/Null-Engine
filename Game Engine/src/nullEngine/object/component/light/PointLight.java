package nullEngine.object.component.light;

import math.Vector4f;
import nullEngine.control.Layer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class PointLight extends GameComponent {
	private Vector4f lightColor;
	private float squared, linear, constant;

	public PointLight(Vector4f lightColor, float squared, float linear, float constant) {
		this.lightColor = lightColor;
		this.squared = squared;
		this.linear = linear;
		this.constant = constant;
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		if (flags.get(Layer.DEFERRED_RENDER_BIT))
			renderer.add(this);
	}

	@Override
	public void update(double delta, GameObject object) {

	}

	public Vector4f getLightColor() {
		return lightColor;
	}

	public void setLightColor(Vector4f lightColor) {
		this.lightColor = lightColor;
	}

	public float getSquared() {
		return squared;
	}

	public void setSquared(float squared) {
		this.squared = squared;
	}

	public float getLinear() {
		return linear;
	}

	public void setLinear(float linear) {
		this.linear = linear;
	}

	public float getConstant() {
		return constant;
	}

	public void setConstant(float constant) {
		this.constant = constant;
	}
}
