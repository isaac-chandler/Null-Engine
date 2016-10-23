package nullEngine.object.component.gui;

import nullEngine.gl.model.Model;
import nullEngine.gl.model.Quad;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.texture.Texture2D;
import nullEngine.gl.texture.TextureGenerator;
import nullEngine.object.GameObject;
import util.BitFieldInt;

public class GuiModel extends GuiComponent {

	private Texture2D texture = TextureGenerator.WHITE;
	private Model model = Quad.get();

	public GuiModel(float x, float y, float width, float height, Model model, Texture2D texture) {
		super(x, y, width, height);
		this.texture = texture;
		this.model = model;
	}

	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		super.render(renderer, object, flags);
		texture.bind();
		model.render();
	}
}
