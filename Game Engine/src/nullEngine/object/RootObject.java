package nullEngine.object;

import nullEngine.control.Layer;

public class RootObject extends GameObject {

	private Layer layer;

	public RootObject(Layer layer) {
		this.layer = layer;
	}

	@Override
	public Layer getLayer() {
		return layer;
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
	}

	@Override
	public void preRender() {
		super.preRender();
	}
}
