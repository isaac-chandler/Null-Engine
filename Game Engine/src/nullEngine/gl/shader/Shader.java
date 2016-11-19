package nullEngine.gl.shader;

import math.Matrix4f;
import math.Vector4f;
import nullEngine.exception.ShaderException;
import nullEngine.gl.Material;
import nullEngine.loading.filesys.ResourceLoader;
import nullEngine.util.logs.Logs;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A shader
 */
public abstract class Shader {

	private static Shader current = null;

	private static final Pattern GLOBAL_INCLUDE_PATTERN = Pattern.compile("\\s*#include\\s*\"(.*?)\"\\s*");
	private static final Pattern LOCAL_INCLUDE_PATTERN = Pattern.compile("\\s*#include\\s*<(.*?)>\\s*");
	private HashMap<String, Integer> userFloats = new HashMap<>();
	private HashMap<String, Integer> userVectors = new HashMap<>();
	private HashMap<String, Integer> userTextures = new HashMap<>();

	private int program;
	private int vertexShader;
	private int fragmentShader;
	private int geometryShader = -1;
	private int systemTextures = 0;

	private int location_mvp;


	private static final FloatBuffer matrixbuffer = BufferUtils.createFloatBuffer(16);

	/**
	 * Get the currently bound shader
	 *
	 * @return The currently bound shader
	 */
	public static Shader bound() {
		return current;
	}

	/**
	 * Create a new shader from a file
	 *
	 * @param shader The file to be loaded from <em>res/shaders</em> without the <em>.ns</em> extension using the <a href="spec/Shading_Language_1.html">Null Engine Shading Language</a>
	 */
	public Shader(String shader) {
		shader += ".ns";
		String src = loadLongShaderSource(shader);
		Scanner in = new Scanner(src);

		Block version = getBlock(shader, "VERSION", in);
		if (version == null)
			Logs.f(new ShaderException("Shader " + shader + " does not have a version declaration"));

		String prefix = "#version " + version.src + "\n";

		String vertex = shader + " - vertex";
		String fragment = shader + " - fragment";
		String geometry = shader + " - geometry";
		String vertexSrc = createShaderSource(shader, getBlock(shader, "VS", in));
		if (vertexSrc == null)
			Logs.f(new ShaderException("Shader " + shader + " does not have a vertex shader"));
		vertexSrc = prefix + vertexSrc;

		String fragmentSrc = createShaderSource(shader, getBlock(shader, "FS", in));
		if (fragmentSrc == null)
			Logs.f(new ShaderException("Shader " + shader + " does not have a vertex shader"));
		fragmentSrc = prefix + fragmentSrc;

		String geometrySrc = createShaderSource(shader, getBlock(shader, "GS", in));
		if (geometrySrc != null)
			geometrySrc = prefix + geometrySrc;

		vertexShader = loadShader(vertex, vertexSrc, GL20.GL_VERTEX_SHADER);
		fragmentShader = loadShader(fragment, fragmentSrc, GL20.GL_FRAGMENT_SHADER);
		if (geometrySrc != null)
			geometryShader = loadShader(geometry, geometrySrc, GL32.GL_GEOMETRY_SHADER);

		program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vertexShader);
		GL20.glAttachShader(program, fragmentShader);
		if (geometryShader != -1)
			GL20.glAttachShader(program, geometryShader);

		bindAttributes();

		GL20.glLinkProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			int length = GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH);
			String names = geometryShader == -1 ? vertex + " and " + fragment : vertex + ", " + geometry + " and " + fragment;
			Logs.f("Failed to link " + names, new ShaderException(GL20.glGetProgramInfoLog(program, length)));
		}

		GL20.glValidateProgram(program);
		if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			int length = GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH);
			String names = geometryShader == -1 ? vertex + " and " + fragment : vertex + ", " + geometry + " and " + fragment;
			Logs.f("Failed to validate " + names, new ShaderException(GL20.glGetProgramInfoLog(program, length)));
		}

		location_mvp = getUniformLocation("mvp");
		getUniformLocations();
	}

	/**
	 * Bind the shader attributes
	 */
	protected abstract void bindAttributes();

	/**
	 * Get the uniform locations
	 */
	protected abstract void getUniformLocations();

	/**
	 * Bind an attribute
	 *
	 * @param attribute The attribute id
	 * @param name      The variable name
	 */
	protected void bindAttribute(int attribute, String name) {
		GL20.glBindAttribLocation(program, attribute, name);
	}

	/**
	 * Bind fragment data
	 *
	 * @param fragData The fragment data id
	 * @param name     The variable name
	 */
	protected void bindFragData(int fragData, String name) {
		GL30.glBindFragDataLocation(program, fragData, name);
	}

	/**
	 * Get a uniform location
	 *
	 * @param name The variable name
	 * @return The location
	 */
	protected int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(program, name);
	}

	/**
	 * Set how many textures there are before the start of the material
	 *
	 * @param systemTextures
	 */
	protected void setSystemTextures(int systemTextures) {
		this.systemTextures = systemTextures;
	}

	/**
	 * Add a float from a material
	 *
	 * @param name The variable name
	 */
	protected void addUserFloat(String name) {
		userFloats.put(name, getUniformLocation(name));
	}

	/**
	 * Add a vector from a material
	 *
	 * @param name The variable name
	 */
	protected void addUserVector(String name) {
		userVectors.put(name, getUniformLocation(name));
	}

	/**
	 * Add a texture from a material
	 *
	 * @param name The variable name
	 */
	protected void addUserTexture(String name) {
		userTextures.put(name, getUniformLocation(name));
	}

	/**
	 * Bind a material
	 *
	 * @param material The material
	 */
	public void loadMaterial(Material material) {
		for (Map.Entry<String, Integer> f : userFloats.entrySet())
			loadFloat(f.getValue(), material.getFloat(f.getKey()));

		for (Map.Entry<String, Integer> v : userVectors.entrySet())
			loadVec4(v.getValue(), material.getVector(v.getKey()));

		int textueLocation = systemTextures;
		for (Map.Entry<String, Integer> texture : userTextures.entrySet()) {
			loadInt(texture.getValue(), textueLocation);
			material.getTexture(texture.getKey()).bind(textueLocation++);
		}
	}

	/**
	 * Bind the model view projection matrix
	 *
	 * @param mvp The matrix
	 */
	public void loadMVP(Matrix4f mvp) {
		loadMat4(location_mvp, mvp);
	}

	/**
	 * Bind a float
	 *
	 * @param location The uniform location
	 * @param value    The value
	 */
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}

	/**
	 * Bind a vector 2
	 *
	 * @param location The uniform location
	 * @param x        The x
	 * @param y        The y
	 */
	public void loadVec2(int location, float x, float y) {
		GL20.glUniform2f(location, x, y);
	}

	/**
	 * Bind a vector 3
	 *
	 * @param location The uniform location
	 * @param vec      The value
	 */
	public void loadVec3(int location, Vector4f vec) {
		GL20.glUniform3f(location, vec.x, vec.y, vec.z);
	}

	/**
	 * Bind a vector 3
	 *
	 * @param location The uniform location
	 * @param x        The x
	 * @param y        The y
	 * @param z        The z
	 */
	public void loadVec3(int location, float x, float y, float z) {
		GL20.glUniform3f(location, x, y, z);
	}

	/**
	 * Bind a vector 4
	 *
	 * @param location The uniform locaton
	 * @param vec      The value
	 */
	public void loadVec4(int location, Vector4f vec) {
		GL20.glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
	}

	/**
	 * Bind a vector 4
	 *
	 * @param location The uniform location
	 * @param x        The x
	 * @param y        The y
	 * @param z        The z
	 * @param w        The w
	 */
	public void loadVec4(int location, float x, float y, float z, float w) {
		GL20.glUniform4f(location, x, y, z, w);
	}

	/**
	 * Bind an int
	 *
	 * @param location The uniform location
	 * @param value    The value
	 */
	public void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}

	/**
	 * Bind a 4x4 matrix
	 *
	 * @param location The uniform location
	 * @param mat      The value
	 */
	public void loadMat4(int location, Matrix4f mat) {
		synchronized (matrixbuffer) {
			mat.toFloatBuffer(matrixbuffer);
			GL20.glUniformMatrix4fv(location, true, matrixbuffer);
		}
	}

	/**
	 * Bind a boolean
	 *
	 * @param location The uniform location
	 * @param value    The value
	 */
	protected void loadBoolean(int location, boolean value) {
		loadFloat(location, value ? 1 : 0);
	}

	/**
	 * Bind this shader
	 */
	public void bind() {
		GL20.glUseProgram(program);
		current = this;
	}

	/**
	 * Unbind the shader
	 */
	public static void unbind() {
		GL20.glUseProgram(0);
		current = null;
	}

	/**
	 * Delete this shader
	 */
	public void delete() {
		GL20.glDetachShader(program, vertexShader);
		GL20.glDetachShader(program, fragmentShader);

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);

		GL20.glDeleteProgram(program);
	}

	private static String loadShaderSource(String name) {
		try {
			StringBuilder src = new StringBuilder();


			Scanner in = new Scanner(ResourceLoader.getResource("res/shaders/" + name));

			String s;

			while (in.hasNextLine()) {
				s = in.nextLine();

				Matcher mGlobal = GLOBAL_INCLUDE_PATTERN.matcher(s);
				Matcher mLocal = LOCAL_INCLUDE_PATTERN.matcher(s);

				if (mGlobal.find()) {
					src.append(getGlobalInclude(name, mGlobal.group(1)));
					src.append('\n');
				} else if (mLocal.find()) {
					src.append(getLocalInclude(name, mLocal.group(1)));
					src.append('\n');
				} else {
					src.append(s);
					src.append('\n');
				}
			}
			in.close();

			return src.toString();
		} catch (FileNotFoundException e) {
			Logs.f(e);
			return null;
		}
	}

	private static String getGlobalInclude(String name, String include) {
		Logs.d("including " + include + " in " + name);
		if (include.contains(":")) {
			String block = include.substring(0, include.indexOf(":"));
			String file = include.substring(include.indexOf(":") + 1);
			String src = loadShaderSource(file);
			return createShaderSource(include, getBlock(include, block, new Scanner(src)));
		} else {
			return loadShaderSource(include);
		}
	}

	private static String getLocalInclude(String name, String include) {
		Logs.d("including " + include + " in " + name);
		if (include.contains(":")) {
			String block = include.substring(0, include.indexOf(":"));
			String file = name.substring(0, name.lastIndexOf("/") + 1) + include.substring(include.indexOf(":") + 1);
			String src = loadShaderSource(file);
			return createShaderSource(include, getBlock(include, block, new Scanner(src)));
		} else {
			include = name.substring(0, name.lastIndexOf("/") + 1) + include;
			return loadShaderSource(include);
		}
	}

	private static String loadLongShaderSource(String name) {
		//res/shaders/default/deferred/deferred-basic.ns
		//res/shaders/default/deferred/deferred-basic.ns
		try {
			StringBuilder src = new StringBuilder();

			Scanner in = new Scanner(ResourceLoader.getResource("res/shaders/" + name));

			String s;

			while (in.hasNextLine()) {
				s = in.nextLine();

				src.append(s);
				src.append('\n');
			}
			in.close();

			return src.toString();
		} catch (FileNotFoundException e) {
			Logs.f(e);
			return null;
		}
	}

	private static int loadShader(String name, String src, int type) {
		int shader = GL20.glCreateShader(type);
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);
		if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			int length = GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
			Logs.f("Failed to compile shader " + name, new ShaderException(GL20.glGetShaderInfoLog(shader, length)));
		}

		return shader;
	}

	private static Block getBlock(String name, String type, Scanner scanner) {
		scanner.reset();
		int lineNum = 0;
		type = type.toLowerCase();

		while (scanner.hasNextLine()) {
			lineNum++;
			String line = scanner.nextLine().trim();
			if (line.toLowerCase().equals("$" + type)) {
				StringBuilder sb = new StringBuilder();
				while (scanner.hasNext()) {
					line = scanner.nextLine();

					if (line.toLowerCase().trim().equals("$end")) {
						return new Block(sb.toString(), lineNum);
					} else {
						sb.append(line);
						sb.append('\n');
					}
				}
				Logs.w("Block " + type + " not closed in  shader " + name);
				return new Block(sb.toString(), lineNum);
			}
		}
		return null;
	}

	private static String createShaderSource(String name, Block block) {
		if (block == null)
			return null;

		Scanner in = new Scanner(block.src);
		StringBuilder src = new StringBuilder();

		String s;

		while (in.hasNextLine()) {
			s = in.nextLine();

			Matcher mGlobal = GLOBAL_INCLUDE_PATTERN.matcher(s);
			Matcher mLocal = LOCAL_INCLUDE_PATTERN.matcher(s);

			if (mGlobal.find()) {
				src.append(getGlobalInclude(name, mGlobal.group(1)));
				src.append('\n');
			} else if (mLocal.find()) {
				src.append(getLocalInclude(name, mLocal.group(1)));
				src.append('\n');
			} else {
				src.append(s);
				src.append('\n');
			}
		}
		in.close();

		return src.toString();
	}

	/**
	 * Get the program id
	 * @return The id
	 */
	public int getProgram() {
		return program;
	}

	private static class Block {
		public String src;
		public int line;

		public Block(String src, int line) {
			this.src = src;
			this.line = line;
		}
	}
}
