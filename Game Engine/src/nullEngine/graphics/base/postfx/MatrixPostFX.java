package nullEngine.graphics.base.postfx;

import math.Matrix4f;
import nullEngine.graphics.base.shader.postfx.MatrixShader;
import nullEngine.graphics.base.shader.postfx.PostFXShader;

public class MatrixPostFX extends PostFX {
	private Matrix4f matrix;

	public MatrixPostFX(PostFXOutput colors, Matrix4f matrix) {
		super(MatrixShader.INSTANCE, new PostFXOutput[]{colors});
		this.matrix = matrix;
	}

	public void setMatrix(Matrix4f matrix) {
		this.matrix = matrix;
	}

	@Override
	public void updateUniforms(PostFXShader shader) {
		MatrixShader.INSTANCE.updateUniforms(matrix);
	}

	public Matrix4f getMatrix() {
		return matrix;
	}
}
