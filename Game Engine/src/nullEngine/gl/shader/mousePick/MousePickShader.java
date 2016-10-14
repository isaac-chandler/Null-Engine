package nullEngine.gl.shader.mousePick;

import nullEngine.gl.shader.ModelMatrixShader;

public class MousePickShader extends ModelMatrixShader {

	private int location_color;

	public MousePickShader(String shader) {
		super(shader);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindFragData(0, "outColor");
		bindFragData(1, "outWorldPosition");
		bindFragData(2, "outLocalPosition");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_color = getUniformLocation("color");
	}

	public void loadIdToColor(int id) {
		int r = id >>> 24;
		int g = (id >>> 16) & 0xFF;
		int b = (id >>> 8) & 0xFF;
		int a = id & 0xFF;

		loadVec4(location_color, r / 255f, g / 255f, b / 255f, a / 255f);
	}
}
