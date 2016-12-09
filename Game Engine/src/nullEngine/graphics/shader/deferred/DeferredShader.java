package nullEngine.graphics.shader.deferred;

import nullEngine.graphics.shader.ModelMatrixShader;
import nullEngine.graphics.shader.Shader;

/**
 * Deferred rendering shader
 */
public class DeferredShader extends ModelMatrixShader {

	/**
	 * Create a new deferred rendering shader
	 * @param shader The shader name
	 * @see Shader#Shader(String) Format details
	 */
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
