package nullEngine.gl;

import math.Vector4f;
import nullEngine.gl.shader.BasicShader;
import nullEngine.gl.shader.Shader;
import nullEngine.gl.shader.deferred.DeferredBasicShader;
import nullEngine.gl.shader.mousePick.MousePickBasicShader;
import nullEngine.gl.texture.Texture2D;

import java.util.ArrayList;
import java.util.HashMap;

public class Material implements Cloneable {

	public static final int BASIC_SHADER_INDEX = 0;
	public static final int DEFERRED_SHADER_INDEX = 1;
	public static final int MOUSE_PICKING_SHADER_INDEX = 2;

	private HashMap<String, Float> floats;
	private HashMap<String, Vector4f> vectors;
	private HashMap<String, Texture2D> textures;

	private boolean usingLighting = true;
	private boolean alwaysRender = false;

	private ArrayList<Shader> shaders = new ArrayList<Shader>();

	private static HashMap<String, Float> defaultFloats = new HashMap<String, Float>();
	private static HashMap<String, Vector4f> defaultVectors = new HashMap<String, Vector4f>();
	private static HashMap<String, Texture2D> defaultTextures = new HashMap<String, Texture2D>();

	private Material(Object a) {
	}

	public Material() {
		floats = (HashMap<String, Float>) defaultFloats.clone();
		vectors = (HashMap<String, Vector4f>) defaultVectors.clone();
		textures = (HashMap<String, Texture2D>) defaultTextures.clone();
		setShader(BasicShader.INSTANCE, BASIC_SHADER_INDEX);
		setShader(DeferredBasicShader.INSTANCE, DEFERRED_SHADER_INDEX);
		setShader(MousePickBasicShader.INSTANCE, MOUSE_PICKING_SHADER_INDEX);
	}


	public void setFloat(String key, float f) {
		floats.put(key, f);
	}

	public void setVector(String key, Vector4f v) {
		vectors.put(key, v);
	}

	public void setTexture(String key, Texture2D t) {
		textures.put(key, t);
	}

	public boolean hasFloat(String key) {
		return floats.containsKey(key);
	}

	public boolean hasVector(String key) {
		return vectors.containsKey(key);
	}

	public boolean hasTexture(String key) {
		return textures.containsKey(key);
	}

	public float getFloat(String key) {
		return floats.get(key);
	}

	public Vector4f getVector(String key) {
		return vectors.get(key);
	}

	public Texture2D getTexture(String key) {
		return textures.get(key);
	}

	public void setReflectivity(float reflectivity) {
		setFloat("reflectivity", reflectivity);
	}

	public void setShineDamper(float shineDamper) {
		setFloat("shineDamper", shineDamper);
	}

	public void setDiffuse(Texture2D diffuse) {
		setTexture("diffuse", diffuse);
	}

	public static void setDefaultFloat(String key, float f) {
		defaultFloats.put(key, f);
	}

	public static void setDefaultVector(String key, Vector4f v) {
		defaultVectors.put(key, v);
	}

	public static void setDefaultTexture(String key, Texture2D texture) {
		defaultTextures.put(key, texture);
	}

	public boolean isUsingLighting() {
		return usingLighting;
	}

	public void setUsingLighting(boolean usingLighting) {
		this.usingLighting = usingLighting;
	}

	public Shader getShader(int i) {
		return shaders.get(i);
	}

	public void setShader(Shader shader, int i) {
		if (shaders.size() <= i)
			shaders.add(i, shader);
		else
			shaders.set(i, shader);
	}

	public boolean isAlwaysRender() {
		return alwaysRender;
	}

	public void setAlwaysRender(boolean alwaysRender) {
		this.alwaysRender = alwaysRender;
	}

	@Override
	public Material clone() {
		Material clone = new Material(null);
		clone.floats = (HashMap<String, Float>) floats.clone();
		clone.vectors = (HashMap<String, Vector4f>) vectors.clone();
		clone.textures = (HashMap<String, Texture2D>) textures.clone();
		clone.usingLighting = usingLighting;
		clone.alwaysRender = alwaysRender;
		clone.shaders = (ArrayList<Shader>) shaders.clone();
		return clone;
	}
}
