package nullEngine.graphics;

import math.Vector4f;
import nullEngine.graphics.shader.BasicShader;
import nullEngine.graphics.shader.Shader;
import nullEngine.graphics.shader.deferred.DeferredBasicShader;
import nullEngine.graphics.shader.mousePick.MousePickBasicShader;
import nullEngine.graphics.texture.Texture2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create a new material
 */
public class Material implements Cloneable {

	private static int nextShaderIndex = 0;

	public static synchronized int getNextShaderIndex() {
		return nextShaderIndex++;
	}

	/**
	 * The index of a materials basic shader
	 */
	public static final int BASIC_SHADER_INDEX = getNextShaderIndex();
	/**
	 * The index of a materials deferred rendering shader
	 */
	public static final int DEFERRED_SHADER_INDEX = getNextShaderIndex();
	/**
	 * The index of a materials mouse pixking shader
	 */
	public static final int MOUSE_PICKING_SHADER_INDEX = getNextShaderIndex();

	private Map<String, Float> floats;
	private Map<String, Vector4f> vectors;
	private Map<String, Texture2D> textures;

	private boolean alwaysRender = false;

	private List<Shader> shaders = new ArrayList<>();

	/**
	 * The default values for new materials
	 */
	public static final Material DEFAULT_MATERIAL = new Material(0);

	static {
		DEFAULT_MATERIAL.setShader(BasicShader.INSTANCE, BASIC_SHADER_INDEX);
		DEFAULT_MATERIAL.setShader(DeferredBasicShader.INSTANCE, DEFERRED_SHADER_INDEX);
		DEFAULT_MATERIAL.setShader(MousePickBasicShader.INSTANCE, MOUSE_PICKING_SHADER_INDEX);
		DEFAULT_MATERIAL.setFloat("lightingAmount", 1);
	}

	private Material(Material material) {
		floats = new HashMap<>(material.floats);
		vectors = new HashMap<>(material.vectors);
		textures = new HashMap<>(material.textures);
		alwaysRender = material.alwaysRender;
		shaders = new ArrayList<>(material.shaders);
	}

	/**
	 * Create a new material from the default material
	 */
	public Material() {
		this(DEFAULT_MATERIAL);
	}

	private Material(@SuppressWarnings("unused") int dummy) {
		floats = new HashMap<>();
		vectors = new HashMap<>();
		textures = new HashMap<>();
		shaders = new ArrayList<>(3);
	}

	/**
	 * Set a float value
	 * @param key The name
	 * @param f The float
	 */
	public void setFloat(String key, float f) {
		floats.put(key, f);
	}

	/**
	 * Set a Vector 4 value
	 * @param key The name
	 * @param v The vector
	 */
	public void setVector(String key, Vector4f v) {
		vectors.put(key, v);
	}

	/**
	 * Set a texture value
	 * @param key The name
	 * @param t The texture
	 */
	public void setTexture(String key, Texture2D t) {
		textures.put(key, t);
	}

	/**
	 * Check if the material has a float
	 * @param key The name
	 * @return If the material has a float
	 */
	public boolean hasFloat(String key) {
		return floats.containsKey(key);
	}

	/**
	 * Check if the material has a vector
	 * @param key The name
	 * @return If the material has a vector
	 */
	public boolean hasVector(String key) {
		return vectors.containsKey(key);
	}

	/**
	 * Check if the material has a texture
	 * @param key The name
	 * @return If the material has a texture
	 */
	public boolean hasTexture(String key) {
		return textures.containsKey(key);
	}

	/**
	 * Get a float value
	 * @param key The name
	 * @return The float value stored in name or <code>0</code> if it doesn't exist
	 */
	public float getFloat(String key) {
		return floats.getOrDefault(key, 0f);
	}

	/**
	 * Get a vector value
	 * @param key The name
	 * @return The vector value stored in name or <code>null</code> if it doesn't exist
	 */
	public Vector4f getVector(String key) {
		return vectors.get(key);
	}

	/**
	 * Get a texture value
	 * @param key The name
	 * @return The texture value stored in name or <code>null</code> if it doesn't exist
	 */
	public Texture2D getTexture(String key) {
		return textures.get(key);
	}

	/**
	 * Get a shader
	 * @param i the shader index
	 * @return Get the shader ar i
	 */
	public Shader getShader(int i) {
		return shaders.get(i);
	}

	/**
	 * Set a shader
	 * @param shader The shader
	 * @param i The index
	 */
	public void setShader(Shader shader, int i) {
		if (shaders.size() <= i)
			shaders.add(i, shader);
		else
			shaders.set(i, shader);
	}

	/**
	 * Get wether this material should always be rendered
	 * @return Wether this material should always be rendered
	 */
	public boolean isAlwaysRender() {
		return alwaysRender;
	}
	/**
	 * Set wether this material should always be rendered
	 * @param alwaysRender Wether this material should always be rendered
	 */
	public void setAlwaysRender(boolean alwaysRender) {
		this.alwaysRender = alwaysRender;
	}

	/**
	 * Create a copy of this material
	 * @return The copy
	 */
	@Override
	public Material clone() {
		return new Material(this);
	}
}
