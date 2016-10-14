package nullEngine.gl.shader.mousePick;

public class MousePickTerrainShader extends MousePickShader {

	public static final MousePickTerrainShader INSTANCE = new MousePickTerrainShader();

	public MousePickTerrainShader() {
		super("default/mousePick/mousePick-terrain");
	}

	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		addUserTexture("height");
		addUserFloat("size");
	}
}
