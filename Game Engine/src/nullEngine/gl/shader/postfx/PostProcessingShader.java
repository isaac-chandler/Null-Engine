package nullEngine.gl.shader.postfx;

import nullEngine.gl.shader.Shader;

public class PostProcessingShader extends Shader {

	private int location_colors;
	private int location_depth;

	public PostProcessingShader(String vertex, String geometry, String fragment) {
		super(vertex, geometry, fragment);
	}

	public PostProcessingShader(String vertex, String fragment) {
		super(vertex, fragment);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}

	@Override
	protected void getUniformLocations() {
		location_colors = getUniformLocation("colors");
		location_depth = getUniformLocation("depth");
		setSystemTextures(2);
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		loadInt(location_depth, 1);
	}
}
