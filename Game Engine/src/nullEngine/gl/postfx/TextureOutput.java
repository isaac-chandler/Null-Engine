package nullEngine.gl.postfx;

import math.Matrix4f;
import nullEngine.input.PostResizeEvent;

public class TextureOutput implements PostFXOutput {
	private int textureID;

	public TextureOutput(int textureID) {
		this.textureID = textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	@Override
	public void preRender() {}

	@Override
	public void render(Matrix4f viewMatrix) {}

	@Override
	public int getTextureID() {
		return textureID;
	}

	@Override
	public void preResize() {}

	@Override
	public void postResize(PostResizeEvent event) {}
}
