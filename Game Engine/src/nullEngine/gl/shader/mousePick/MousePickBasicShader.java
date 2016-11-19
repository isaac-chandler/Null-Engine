package nullEngine.gl.shader.mousePick;

/**
 * Mouse picking basic shader
 */
public class MousePickBasicShader extends MousePickShader {

	/**
	 * Singleton instance
	 */
	public static final MousePickBasicShader INSTANCE = new MousePickBasicShader();

	private MousePickBasicShader() {
		super("default/mousePick/mousePick-basic");
	}
}
