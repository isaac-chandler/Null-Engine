import math.Quaternion;
import math.Vector4f;
import nullEngine.NullEngine;
import nullEngine.control.Application;
import nullEngine.control.Layer3D;
import nullEngine.control.State;
import nullEngine.gl.Material;
import nullEngine.gl.model.Model;
import nullEngine.gl.model.Terrain;
import nullEngine.gl.shader.postfx.FogPostProcessing;
import nullEngine.gl.texture.Texture2D;
import nullEngine.gl.texture.TextureGenerator;
import nullEngine.loading.Loader;
import nullEngine.object.GameObject;
import nullEngine.object.component.DirectionalLight;
import nullEngine.object.component.FirstPersonCamera;
import nullEngine.object.component.ModelComponent;
import nullEngine.util.logs.Logs;

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

			Layer3D testLayer = new Layer3D(camera, (float) Math.toRadians(90f), 0.1f, 100f);
			test.addLayer(testLayer);

			Loader loader = application.getLoader();
			loader.setAnisotropyEnabled(true);
			loader.setAnisotropyAmount(4);
			loader.setLodBias(-1);
//			Model model = Quad.get();
			final Model model = loader.loadModel("default/dragon");

//			Texture2D texture = null;
//			try {
//				texture = new Texture2D(loader.loadTexture("default/test"));
//			} catch (FileNotFoundException e) {
//				Logs.f(e);
//			}
			Texture2D texture = TextureGenerator.genColored(218, 165, 32, 255);

			Material material = new Material();
			material.setDiffuse(texture);
			material.setShineDamper(15);
			material.setReflectivity(1);

//			testLayer.setAmbientColor(new Vector4f(1, 1, 1));
			FogPostProcessing fog = new FogPostProcessing(1280, 720);
			fog.setSkyColor(new Vector4f(0.529f, 0.808f, 0.922f));
			testLayer.getRenderer().addPostFX(fog);
			testLayer.setAmbientColor(new Vector4f(0.2f, 0.2f, 0.2f));

			GameObject dragon = new GameObject();
			GameObject cameraObject = new GameObject();
			GameObject terrain = new GameObject();
			GameObject light1 = new GameObject();
			testLayer.getRoot().addChild(cameraObject);
			testLayer.getRoot().addChild(dragon);
			testLayer.getRoot().addChild(terrain);
			testLayer.getRoot().addChild(light1);

			light1.addComponent(new DirectionalLight(new Vector4f(1, 1, 1)));
			light1.getTransform().setPos(new Vector4f(5, 0, -20));


			cameraObject.addComponent(camera);
			dragon.getTransform().setPos(new Vector4f(0, 0, 1f));

			dragon.addComponent(new ModelComponent(material, model) {
				@Override
				public void update(float delta, GameObject object) {
					object.getTransform().increaseRot(new Quaternion(delta / 2, new Vector4f(0, 1, 0)));
				}
			});

//			terrain.getTransform().setPos(new Vector4f(-400, 0, -400));

			Material terrainMaterial = new Material();
			terrainMaterial.setDiffuse(TextureGenerator.genColored(new Vector4f(0, 0.557f, 0.067f)));
			terrainMaterial.setReflectivity(0.2f);

			terrain.addComponent(new ModelComponent(terrainMaterial, Terrain.generateFlatTerrain(loader, 800, 128, 1)));

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
