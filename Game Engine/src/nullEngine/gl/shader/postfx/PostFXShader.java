package nullEngine.gl.shader.postfx;

import math.Matrix4f;
import nullEngine.gl.shader.Shader;

public class PostFXShader extends Shader {

	private int location_viewMatrix;

	public PostFXShader(String shader) {
		super(shader);
	}

	@Override
	protected void getUniformLocations() {
		location_viewMatrix = getUniformLocation("viewMatrix");
	}

	public void updateViewMatrix(Matrix4f viewMatrix) {
		loadMat4(location_viewMatrix, viewMatrix);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
	}
}
