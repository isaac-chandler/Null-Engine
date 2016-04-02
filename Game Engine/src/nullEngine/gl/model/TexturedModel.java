package nullEngine.gl.model;

import nullEngine.gl.textures.Texture2D;

public class TexturedModel {
	private Model model;
	private Texture2D texture;

	public TexturedModel(Model model, Texture2D texture) {
		this.model = model;
		this.texture = texture;
	}

	public Model getModel() {
		return model;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public void render() {
		texture.bind();
		model.render();
		Texture2D.unbind();
	}
}
