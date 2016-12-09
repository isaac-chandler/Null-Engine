package nullEngine.graphics.shader.gui;

/**
 * Basic shader for the GUI
 */
public class GuiQuadShader extends GuiShader {
	/**
	 * Singleton instance
	 */
	public static final GuiQuadShader INSTANCE = new GuiQuadShader();

	private int location_size;

	private GuiQuadShader() {
		super("default/gui/gui-quad");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		location_size = getUniformLocation("size");
	}

	public void loadSize(float width, float height) {
		loadVec2(location_size, width, height);
	}
}
