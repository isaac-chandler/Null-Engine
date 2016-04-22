package sandbox;

import math.Quaternion;
import math.Vector4f;
import nullEngine.NullEngine;
import nullEngine.control.Application;
import nullEngine.control.Layer3D;
import nullEngine.control.State;
import nullEngine.gl.Material;
import nullEngine.gl.model.Model;
import nullEngine.gl.model.Terrain;
import nullEngine.gl.shader.deferred.DeferredTerrainShader;
import nullEngine.gl.shader.postfx.FogPostProcessing;
import nullEngine.gl.texture.Texture2D;
import nullEngine.gl.texture.TextureGenerator;
import nullEngine.loading.Loader;
import nullEngine.object.GameObject;
import nullEngine.object.component.DirectionalLight;
import nullEngine.object.component.FlyCam;
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

			FlyCam camera = new FlyCam();

			Layer3D testLayer = new Layer3D(camera, (float) Math.toRadians(90f), 0.1f, 500f);
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
			material.setShineDamper(32);
			material.setReflectivity(1);

			FogPostProcessing fog = new FogPostProcessing();
			fog.setSkyColor(new Vector4f(0.529f, 0.808f, 0.922f));
			fog.setDensity(0.0005f);
			testLayer.getRenderer().addPostFX(fog);
			testLayer.setAmbientColor(new Vector4f(0.2f, 0.2f, 0.2f));

			GameObject dragon = new GameObject();
			GameObject cameraObject = new GameObject();
			GameObject terrain = new GameObject();
			testLayer.getRoot().addChild(cameraObject);
			testLayer.getRoot().addChild(dragon);
			testLayer.getRoot().addChild(terrain);

			cameraObject.getTransform().setPos(new Vector4f(0, 1.5f, -5));
			cameraObject.getTransform().setRot(new Quaternion((float) Math.PI, Vector4f.UP));

			testLayer.getRoot().addComponent(new DirectionalLight(new Vector4f(1, 1, 1), new Vector4f(-5, 0, 20, 0)));
			testLayer.getRoot().addComponent(new DirectionalLight(new Vector4f(1, 1, 1), new Vector4f(0, -1, 0, 0)));


			cameraObject.addComponent(camera);
			dragon.getTransform().setPos(new Vector4f(0, 0, 1f));

			dragon.addComponent(new ModelComponent(material, model) {
				@Override
				public void update(float delta, GameObject object) {
//					object.getTransform().increaseRot(new Quaternion(delta / 2, new Vector4f(0, 1, 0)));
				}
			});

//			terrain.getTransform().setPos(new Vector4f(-400, 0, -400));

			Material terrainMaterial = new Material();
			terrainMaterial.setShader(DeferredTerrainShader.INSTANCE);
			terrainMaterial.setTexture("aTexture", new Texture2D(loader.loadTexture("default/grass")));
			terrainMaterial.setTexture("rTexture", new Texture2D(loader.loadTexture("default/dirt")));
			terrainMaterial.setTexture("gTexture", new Texture2D(loader.loadTexture("default/flowers")));
			terrainMaterial.setTexture("bTexture", new Texture2D(loader.loadTexture("default/path")));
			terrainMaterial.setTexture("blend", new Texture2D(loader.loadTexture("default/blend")));
			terrainMaterial.setVector("reflectivity", new Vector4f(0.2f, 0.2f, 0.2f, 0.2f));
			terrainMaterial.setVector("shineDamper", new Vector4f(8, 8, 8, 8));
			terrainMaterial.setFloat("tileCount", 200);

			terrain.addComponent(new ModelComponent(terrainMaterial, Terrain.generateFlatTerrain(loader, 200, 128)));

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
