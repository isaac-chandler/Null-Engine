package nullEngine.gl.shader.deferred;

import math.Vector4f;
import nullEngine.object.component.DirectionalLight;

public class DeferredDirectionalShader extends DeferredLightingShader {

	public static final DeferredDirectionalShader INSTANCE = new DeferredDirectionalShader();

	private int location_lightColor;
	private int location_direction;
	private int location_cameraPos;

	private DeferredDirectionalShader() {
		super("default/deferred/deferred-directional", "default/deferred/deferred-directional");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_lightColor = getUniformLocation("lightColor");
		location_direction = getUniformLocation("direction");
		location_cameraPos = getUniformLocation("cameraPos");
	}

	public void loadLight(DirectionalLight light) {
		loadVec3(location_direction, light.getDirection());
		loadVec4(location_lightColor, light.getLightColor());
	}

	public void loadCameraPos(Vector4f cameraPos) {
		loadVec3(location_cameraPos, cameraPos);
	}
}
