import math.Vector4f;
import nullEngine.NullEngine;
import nullEngine.control.Application;
import nullEngine.control.Layer;
import nullEngine.control.Layer3D;
import nullEngine.control.State;
import nullEngine.gl.Renderer;
import nullEngine.gl.model.Model;
import nullEngine.gl.model.TexturedModel;
import nullEngine.gl.textures.Texture2D;
import nullEngine.loading.Loader;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import nullEngine.object.component.FirstPersonCamera;
import nullEngine.util.logs.Logs;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		try {
			NullEngine.init();
			Logs.setDebug(true);
			Application application = new Application(1280, 720, false, "Sandbox");
			application.bind();

			State test = new State();
			int TEST_STATE = application.addState(test);
			application.setState(TEST_STATE);

			FirstPersonCamera camera = new FirstPersonCamera();

			Layer testLayer = new Layer3D(camera, (float) Math.toRadians(90f), 0.1f, 1000f);
			test.addLayer(testLayer);

			Loader loader = application.getLoader();
			loader.setAnisotropyEnabled(true);
			loader.setAnisotropyAmount(4);
			loader.setLodBias(-1);
//			Model model = Quad.get();
			long start = System.nanoTime();
			Model model = loader.loadModel("default/dragon");
			Logs.d((System.nanoTime() - start) / 1e6 + "ms");

			Texture2D texture = null;
			try {
				texture = new Texture2D(loader.loadTexture("default/test"));
			} catch (FileNotFoundException e) {
				Logs.f(e);
			}
			final TexturedModel texturedModel = new TexturedModel(model, texture);

			GameObject object = new GameObject();
			GameObject cameraObject = new GameObject();
			testLayer.getRoot().addChild(cameraObject);
			testLayer.getRoot().addChild(object);

			cameraObject.addComponent(camera);
			object.getTransform().setPos(new Vector4f(0, 0, 1f));

			object.addComponent(new GameComponent() {
				@Override
				public void render(Renderer renderer, GameObject object) {
					texturedModel.render();
				}

				@Override
				public void update(float delta, GameObject object) {

				}
			});

			Throwable e = application.start();
			if (e != null) {
				Logs.e("Caught exception");
				e.printStackTrace();
				application.carefulDestroy();
			} else {
				application.destroy();
			}

			NullEngine.cleanup();
		} catch (Exception e) {
			Logs.e("Something went horribly wrong!", e);
			Logs.finish();
		}
	}
}
