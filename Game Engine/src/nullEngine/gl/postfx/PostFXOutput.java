package nullEngine.gl.postfx;

import math.Matrix4f;
import nullEngine.input.PostResizeEvent;

public interface PostFXOutput {

	void preRender();

	void render(Matrix4f viewMatrix);

	int getTextureID();

	void preResize();

	void postResize(PostResizeEvent event);
}
