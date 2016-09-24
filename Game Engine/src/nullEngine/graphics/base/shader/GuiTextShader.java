package nullEngine.graphics.base.shader;

import math.Vector4f;

public class GuiTextShader extends GuiShader implements TextShader {

	public static final GuiTextShader INSTANCE = new GuiTextShader();

	private int location_aspectRatio;
	private int location_offset;
	private int location_thickness;
	private int location_borderThickness;
	private int location_borderColor;
	private int location_borderOffset;

	private GuiTextShader() {
		super("default/text", "default/text");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_aspectRatio = getUniformLocation("aspectRatio");
		location_offset = getUniformLocation("offset");

		location_thickness = getUniformLocation("thickness");
		location_borderThickness = getUniformLocation("borderThickness");

		location_borderColor = getUniformLocation("borderColor");

		location_borderOffset = getUniformLocation("borderOffset");
	}

	@Override
	public void loadAspectRatio(float x, float y) {
		loadVec2(location_aspectRatio, x, y);
	}

	@Override
	public void loadOffset(float x, float y) {
		loadVec2(location_offset, x, y);
	}

	@Override
	public void loadThickness(float width, float edge) {
		loadVec2(location_thickness, width, edge);
	}

	@Override
	public void loadBorderColor(Vector4f borderColor) {
		loadVec4(location_borderColor, borderColor);
	}

	@Override
	public void loadBorderThickness(float borderWidth, float borderEdge) {
		loadVec2(location_borderThickness, borderWidth, borderEdge);
	}

	@Override
	public void loadBorderOffset(float x, float y) {
		loadVec2(location_borderOffset, x, y);
	}
}
