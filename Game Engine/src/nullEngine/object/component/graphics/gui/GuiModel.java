package nullEngine.object.component.graphics.gui;

import nullEngine.control.physics.PhysicsEngine;
import nullEngine.graphics.model.Model;
import nullEngine.graphics.model.Quad;
import nullEngine.graphics.renderer.Renderer;
import nullEngine.graphics.texture.Texture2D;
import nullEngine.graphics.texture.TextureGenerator;
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

	/**
	 * Update this component
	 * @param physics
	 * @param object The object this component is attached to
	 * @param delta  The time since update was last called
	 */
	@Override
	public void update(PhysicsEngine physics, GameObject object, double delta) {

	}
}
