package nullEngine.object.component;

import math.Vector4f;
import nullEngine.graphics.base.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;

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
	public void render(Renderer renderer, GameObject object) {
		renderer.add(this);
	}

	@Override
	public void update(float delta, GameObject object) {

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
