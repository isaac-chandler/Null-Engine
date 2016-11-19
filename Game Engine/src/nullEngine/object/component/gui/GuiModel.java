package nullEngine.object.component.gui;

import nullEngine.gl.model.Model;
import nullEngine.gl.model.Quad;
import nullEngine.gl.renderer.Renderer;
import nullEngine.gl.texture.Texture2D;
import nullEngine.gl.texture.TextureGenerator;
import nullEngine.object.GameObject;
import util.BitFieldInt;

/**
 * A model to be renderer in the GUI
 */
public class GuiModel extends GuiComponent {

	private Texture2D texture = TextureGenerator.WHITE;
	private Model model = Quad.get();

	/**
	 * Create a new GUI Model
	 *
	 * @param x       The x
	 * @param y       the y
	 * @param width   The width
	 * @param height  The height
	 * @param model   The model to use
	 * @param texture The texture to use
	 */
	public GuiModel(float x, float y, float width, float height, Model model, Texture2D texture) {
		super(x, y, width, height);
		this.texture = texture;
		this.model = model;
	}

	/**
	 * Render this model
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param object   The object this component is attached to
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, GameObject object, BitFieldInt flags) {
		super.render(renderer, object, flags);
		texture.bind();
		model.render();
	}
}
