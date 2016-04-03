package nullEngine.gl.shader;

public class DeferredAmbientShader extends Shader {

	public static final DeferredAmbientShader INSTANCE = new DeferredAmbientShader();

	private int location_colors;
	private int location_positions;
	private int location_normals;

	private DeferredAmbientShader() {
		super("default/deferred/deferred-processing", "default/deferred/deferred-processing");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoord");
	}

	@Override
	protected void getUniformLocations() {
		location_colors = getUniformLocation("colors");
		location_positions = getUniformLocation("positions");
		location_normals = getUniformLocation("normals");

		loadInt(location_colors, 0);
		loadInt(location_positions, 1);
		loadInt(location_normals, 2);
	}
}
