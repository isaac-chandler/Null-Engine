package nullEngine.gl.shader.postfx;

import math.Matrix4f;
import nullEngine.gl.shader.Shader;

public class PostProcessingShader extends Shader {

	private int location_colors;
	private int location_positions;
	private int location_normals;
	private int location_specular;
	private int location_depth;
	private int location_viewMatrix;

	@Override
	protected void getUniformLocations() {
		location_colors = getUniformLocation("colors");
		location_positions = getUniformLocation("positions");
		location_normals = getUniformLocation("normals");
		location_specular = getUniformLocation("specular");
		location_depth = getUniformLocation("depth");
		location_viewMatrix = getUniformLocation("viewMatrix");
		setSystemTextures(5);
	}

	public void updateViewMatrix(Matrix4f viewMatrix) {
		loadMat4(location_viewMatrix, viewMatrix);
	}

	@Override
	public void bind() {
		super.bind();
		loadInt(location_colors, 0);
		loadInt(location_positions, 1);
		loadInt(location_normals, 2);
		loadInt(location_specular, 3);
		loadInt(location_depth, 4);
	}



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
}
