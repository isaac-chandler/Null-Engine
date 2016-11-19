package nullEngine.gl.shader.deferred.lighting;

import nullEngine.gl.shader.Shader;

/**
 * Deferred lighting shader
 */
public class DeferredLightShader extends Shader {
	private int location_colors;
	private int location_positions;
	private int location_normals;
	private int location_specular;

	/**
	 * Create a new deferred lighting shader
	 * @param shader The shader name
	 * @see Shader#Shader(String) Format details
	 */
	public DeferredLightShader(String shader) {
		super(shader);
	}

	/**
	 * Bind the attributes
	 */
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoord");
	}

	/**
	 * Get the uniform locations
	 */
	@Override
	protected void getUniformLocations() {
		location_colors = getUniformLocation("colors");
		location_positions = getUniformLocation("positions");
		location_normals = getUniformLocation("normals");
		location_specular = getUniformLocation("specular");
		setSystemTextures(4);
	}

	/**
	 * Bind this shader
	 */
	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		loadInt(location_positions, 1);
		loadInt(location_normals, 2);
		loadInt(location_specular, 3);
	}
}
