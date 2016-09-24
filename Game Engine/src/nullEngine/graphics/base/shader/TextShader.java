package nullEngine.graphics.base.shader;

import math.Vector4f;

public interface TextShader {
	void loadAspectRatio(float x, float y);

	void loadOffset(float x, float y);

	void loadColor(Vector4f color);

	void loadThickness(float width, float edge);

	void loadBorderColor(Vector4f borderColor);

	void loadBorderThickness(float borderWidth, float borderEdge);

	void loadBorderOffset(float x, float y);
}
