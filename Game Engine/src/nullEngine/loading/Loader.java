package nullEngine.loading;

import de.matthiasmann.twl.utils.PNGDecoder;
import nullEngine.control.Application;
import nullEngine.gl.framebuffer.Framebuffer3D;
import nullEngine.gl.model.Model;
import nullEngine.loading.model.NLMLoader;
import nullEngine.loading.model.OBJLoader;
import nullEngine.util.Buffers;
import nullEngine.util.logs.Logs;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Loader {

	private Application application;
	private GLCapabilities capabilities;

	private float anisotropyAmount = 0;
	private boolean anisotropyEnabled = false;
	private float lodBias = 0;

	private ArrayList<Integer> vaos = new ArrayList<Integer>();
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	private ArrayList<Integer> textures = new ArrayList<Integer>();

	public Loader(Application application) {
		this.application = application;
		capabilities = application.getWindow().getGLCapabilities();
	}

	public void setAnisotropyEnabled(boolean anisotropyEnabled) {
		this.anisotropyEnabled = anisotropyEnabled;
	}

	public void setAnisotropyAmount(float anisotropyAmount) {
		if (capabilities.GL_EXT_texture_filter_anisotropic) {
			this.anisotropyAmount = Math.min(anisotropyAmount, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
		}
	}

	public void setLodBias(float lodBias) {
		this.lodBias = Math.min(GL11.glGetFloat(GL14.GL_MAX_TEXTURE_LOD_BIAS), lodBias);
	}

	private int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}

	private int dataToAttrib(int attrib, float[] data, int size) {
		FloatBuffer buf = Buffers.createBuffer(data);
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, size, GL11.GL_FLOAT, false, 0, 0);
		return vbo;
	}

	private int indexBuffer(int[] indices) {
		IntBuffer buf = Buffers.createBuffer(indices);
		int ibo = GL15.glGenBuffers();
		vbos.add(ibo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
		return ibo;
	}

	public Model loadModel(float[][] vertices, float[][] texCoords, float[][] normals, int[][] indices) {
		int[] vaos = new int[vertices.length];
		int[] ibos = new int[vertices.length];
		int[] vertexVBOs = new int[vertices.length];
		int[] texCoordVBOs = new int[vertices.length];
		int[] normalVBOs = new int[vertices.length];
		int[] vertexCounts = new int[vertices.length];
		for (int  i = 0; i < vertices.length; i++) {
			vaos[i] = createVAO();
			ibos[i] = indexBuffer(indices[i]);
			vertexCounts[i] = indices[i].length;
			this.vaos.add(vaos[i]);
			vertexVBOs[i] = dataToAttrib(0, vertices[i], 3);
			texCoordVBOs[i] = dataToAttrib(1, texCoords[i], 2);
			normalVBOs[i] = dataToAttrib(2, normals[i], 3);
			GL30.glBindVertexArray(0);
		}
		return new Model(vaos, vertexCounts, ibos, vertexVBOs, texCoordVBOs, normalVBOs);
	}

	public Model loadModel(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
		return loadModel(new float[][] {vertices}, new float[][] {texCoords}, new float[][] {normals}, new int[][] {indices});
	}

	public Model loadModel(String name) {
		name = name.lastIndexOf('/') < name.lastIndexOf('.') ? name : name + ".nlm";
		if (name.endsWith(".obj")) {
			return OBJLoader.loadModel(this, name);
		} else if (name.endsWith(".nlm")) {
			return NLMLoader.loadModel(this, name);
		}
		Logs.f(new FileFormatException("Unknown model format"));
		return null;
	}

	public float[] toFloatArray(ArrayList<Float> list) {
		float[] ret = new float[list.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public int[] toIntArray(ArrayList<Integer> list) {
		int[] ret = new int[list.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public int loadTexture(String file) throws IOException {
		PNGDecoder decoder = new PNGDecoder(ResourceLoader.getResource("res/textures/" + file + ".png"));

		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lodBias);

		if (capabilities.GL_EXT_texture_filter_anisotropic && anisotropyEnabled) {
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, anisotropyAmount);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lodBias);
		}

		if (decoder.hasAlpha()) {
			ByteBuffer buf = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buf.flip();

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		} else {
			ByteBuffer buf = BufferUtils.createByteBuffer(3 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 3, PNGDecoder.Format.RGB);
			buf.flip();

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buf);
		}

		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		textures.add(texture);
		return texture;
	}

	public void cleanup() {
		Logs.d("Loader cleaning up");
		for (int vbo : vbos)
			GL15.glDeleteBuffers(vbo);
		for (int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		for (int texture : textures)
			GL11.glDeleteTextures(texture);
	}

	public void preContextChange() {
		Logs.d("Cleaning up vertex arrays");
		for (int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		vaos.clear();

		Framebuffer3D.preContextChange();
	}

	public void contextChanged() {
		Model.contextChanged(vaos);
		Framebuffer3D.contextChanged();
	}
}
