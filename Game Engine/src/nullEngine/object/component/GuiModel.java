package nullEngine.object.component;

import nullEngine.graphics.base.model.Model;
import nullEngine.graphics.base.model.Quad;
import nullEngine.graphics.base.renderer.Renderer;
import nullEngine.graphics.base.texture.Texture2D;
import nullEngine.graphics.base.texture.TextureGenerator;
import nullEngine.object.GameObject;

public class GuiModel extends GuiComponent {

	private Texture2D texture = TextureGenerator.WHITE;
	private Model model = Quad.get();

	public GuiModel(float x, float y, float width, float height, Model model, Texture2D texture) {
		super(x, y, width, height);
		this.texture = texture;
		this.model = model;
	}

	@Override
	public void render(Renderer renderer, GameObject object) {
		super.render(renderer, object);
		texture.bind();
		model.render();
	}
}
