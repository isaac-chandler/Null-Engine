package nullEngine.gl.shader.deferred;

import math.Matrix4f;
import nullEngine.object.component.DirectionalLight;

public class DeferredDirectionalLightShader extends DeferredLightShader {

	public static final DeferredDirectionalLightShader INSTANCE = new DeferredDirectionalLightShader();

	private int location_lightColor;
	private int location_direction;
	private int location_viewMatrix;

	private DeferredDirectionalLightShader() {
		super("default/deferred/deferred-directional", "default/deferred/deferred-directional");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_direction = getUniformLocation("direction");
		location_viewMatrix = getUniformLocation("viewMatrix");
	}

	public void loadLight(DirectionalLight light) {
		loadVec3(location_direction, light.getDirection());
		loadVec3(location_lightColor, light.getLightColor());
	}

	public void loadViewMatrix(Matrix4f viewMarix) {
		loadMat4(location_viewMatrix, viewMarix);
	}
}
