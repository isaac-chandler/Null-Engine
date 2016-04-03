import math.Quaternion;
import math.Vector4f;
import nullEngine.NullEngine;
import nullEngine.control.Application;
import nullEngine.control.Layer3D;
import nullEngine.control.State;
import nullEngine.gl.Renderer;
import nullEngine.gl.model.Model;
import nullEngine.gl.model.TexturedModel;
import nullEngine.gl.texture.Texture2D;
import nullEngine.loading.Loader;
import nullEngine.object.GameComponent;
import nullEngine.object.GameObject;
import nullEngine.object.component.DirectionalLight;
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

			Layer3D testLayer = new Layer3D(camera, (float) Math.toRadians(90f), 0.1f, 1000f);
			test.addLayer(testLayer);

			Loader loader = application.getLoader();
			loader.setAnisotropyEnabled(true);
			loader.setAnisotropyAmount(4);
			loader.setLodBias(-1);
//			Model model = Quad.get();
			Model model = loader.loadModel("default/dragon");

			Texture2D texture = null;
			try {
				texture = new Texture2D(loader.loadTexture("default/test"));
			} catch (FileNotFoundException e) {
				Logs.f(e);
			}
			final TexturedModel texturedModel = new TexturedModel(model, texture);

			GameObject dragon = new GameObject();
			GameObject cameraObject = new GameObject();
			testLayer.getRoot().addChild(cameraObject);
			testLayer.getRoot().addChild(dragon);

			GameObject light1 = new GameObject();
			light1.addComponent(new DirectionalLight(new Vector4f(0.5f, 0, 0)));
			light1.getTransform().setPos(new Vector4f(5, 0, -20));

			GameObject light2 = new GameObject();
			light2.addComponent(new DirectionalLight(new Vector4f(1, 1, 1)));
			light2.getTransform().setPos(new Vector4f(-20, 0, 0));

			GameObject light3 = new GameObject();
			light3.addComponent(new DirectionalLight(new Vector4f(0, 1, 0)));
			light3.getTransform().setPos(new Vector4f(0, 0, 20));

			dragon.addChild(light1);
			light1.addChild(light2);
			dragon.addChild(light3);

			light1.addComponent(new GameComponent() {
				@Override
				public void render(Renderer renderer, GameObject object) {

				}

				@Override
				public void update(float delta, GameObject object) {
					object.getTransform().increaseRot(new Quaternion(delta, Vector4f.FORWARD));
				}
			});


//			testLayer.setAmbientColor(new Vector4f(0.2f, 0.2f, 0.2f));

			cameraObject.addComponent(camera);
			dragon.getTransform().setPos(new Vector4f(0, 0, 1f));

			dragon.addComponent(new GameComponent() {
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
