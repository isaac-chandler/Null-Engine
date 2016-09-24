package nullEngine.graphics.base.shader;

public class GuiBasicShader extends GuiShader {
	public static final GuiBasicShader INSTANCE = new GuiBasicShader();

	private GuiBasicShader() {
		super("default/gui", "default/gui");
	}
}
