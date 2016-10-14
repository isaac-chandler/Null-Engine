package nullEngine.gl.shader.mousePick;

public class MousePickBasicShader extends MousePickShader {

	public static final MousePickBasicShader INSTANCE = new MousePickBasicShader();

	public MousePickBasicShader() {
		super("default/mousePick/mousePick-basic");
	}
}
