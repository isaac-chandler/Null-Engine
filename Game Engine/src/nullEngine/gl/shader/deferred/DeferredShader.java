package nullEngine.gl.shader.deferred;

import nullEngine.gl.shader.ModelMatrixShader;

public class DeferredShader extends ModelMatrixShader {

	private int location_modelMatrix;

	public DeferredShader(String shader) {
		super(shader);
	}


	@Override
	protected void bindAttributes() {
		bindAttribute(0, "inPosition");
		bindAttribute(1, "inTexCoords");
		bindAttribute(2, "inNormal");
		bindFragData(0, "outColor");
		bindFragData(1, "outPosition");
		bindFragData(2, "outNormal");
		bindFragData(3, "outSpecular");
	}
}
