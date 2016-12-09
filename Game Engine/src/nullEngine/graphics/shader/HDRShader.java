package nullEngine.graphics.shader;

/**
 * Basic shader
 */
public class HDRShader extends Shader {

	/**
	 * Singleton instance
	 */
	public static final HDRShader INSTANCE = new HDRShader();

	private int location_exposureTime;

	private HDRShader() {
		super("default/hdr");
	}

	/**
	 * Bind the attributes
	 */
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	/**
	 * Bind the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		location_exposureTime = getUniformLocation("exposureTime");
	}

	public void loadExposureTime(float exposureTime) {
		loadFloat(location_exposureTime, exposureTime);
	}
}
