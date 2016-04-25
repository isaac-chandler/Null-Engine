package nullEngine.gl.shader;

import math.Vector4f;

public class GuiShader extends Shader {
	private int location_color;
	private int location_pos;
	private int location_size;

	public GuiShader(String vertex, String fragment) {
		super(vertex, fragment);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	@Override
	protected void getUniformLocations() {
		location_color = getUniformLocation("color");
		location_pos = getUniformLocation("pos");
		location_size = getUniformLocation("size");
	}

	public void loadColor(Vector4f color) {
		loadVec4(location_color, color);
	}

	public void loadPosition(float x, float y) {
		loadVec2(location_pos, x, y);
	}

	public void loadSize(float width, float height) {
		loadVec2(location_size, width, height);
	}
}
