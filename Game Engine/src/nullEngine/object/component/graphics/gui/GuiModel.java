package nullEngine.object.component.graphics.gui;

import nullEngine.graphics.model.Model;
import nullEngine.graphics.model.Quad;
import nullEngine.graphics.texture.Texture2D;
import nullEngine.graphics.texture.TextureGenerator;
import nullEngine.object.GameObject;

/**
 * A model to be renderer in the GUI
 */
public class GuiModel extends GuiComponent {

	private Texture2D texture = TextureGenerator.WHITE;
	private Model model = Quad.get();

	/**
	 * Create a new GUI Model
	 *
	 * @param width   The width
	 * @param height  The height
	 * @param model   The model to use
	 * @param texture The texture to use
	 */
	public GuiModel(Anchor anchor, AnchorPos anchorPos, float width, float height, Model model, Texture2D texture) {
		super(anchor, anchorPos);
		setSize(width, height);
		this.texture = texture;
		this.model = model;
	}

	/**
	 * Render this model
	 * @param object   The object this component is attached to
	 */
	@Override
	public void render(GameObject object) {
		super.render(object);
		texture.bind();
		model.render();
	}

	/**
	 * Update this component
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(GameObject object, double delta) {

	}
}
