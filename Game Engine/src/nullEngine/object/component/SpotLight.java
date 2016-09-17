package nullEngine.object.component;

import math.Vector4f;
import nullEngine.gl.renderer.Renderer;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;

public class SpotLight extends GameComponent {
	private Vector4f lightColor;
	private Vector4f direction;
	private float squared, linear, constant;
	private float cutoff;

	public SpotLight(Vector4f lightColor, Vector4f direction, float squared, float linear, float constant, float angle) {
		this.lightColor = lightColor;
		this.direction = direction.normalize(null);
		this.squared = squared;
		this.linear = linear;
		this.constant = constant;
		this.cutoff = (float) Math.cos(angle);
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

	public Vector4f getDirection() {
		return direction;
	}

	public void setDirection(Vector4f direction) {
		this.direction = direction.normalize();
	}

	public float getCutoff() {
		return cutoff;
	}

	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

	public void setAngle(float angle) {
		this.cutoff = (float) Math.cos(angle);
	}
}