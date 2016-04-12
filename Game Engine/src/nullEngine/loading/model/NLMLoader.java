package nullEngine.loading.model;

import nullEngine.gl.model.Model;
import nullEngine.loading.FileFormatException;
import nullEngine.loading.Loader;
import nullEngine.loading.ResourceLoader;
import nullEngine.util.StreamUtils;
import nullEngine.util.logs.Logs;

import java.io.IOException;
import java.io.InputStream;

public class NLMLoader {
	public static Model loadModel(Loader loader, String name) {
		try {
			InputStream is = ResourceLoader.getResource("res/models/" + name);
			int version = is.read();
			if (version == 1) {
				return loadVersion1(loader, is);
			} else if (version == 2) {
				return loadVersion2(loader, is);
			} else {
				throw new FileFormatException("Invalid file version: " + version);
			}
		} catch (IOException e) {
			Logs.f(e);
			return null;
		}
	}

	private static Model loadVersion2(Loader loader, InputStream is) throws IOException {
		int lodCount = is.read();
		float[][] vertices = new float[lodCount][];
		float[][] texCoords = new float[lodCount][];
		float[][] normals = new float[lodCount][];
		int[][] indices = new int[lodCount][];

		for (int i = 0; i < lodCount; i++) {
			ModelData data = loadModelData(is);
			vertices[i] = data.vertices;
			texCoords[i] = data.texCoords;
			normals[i] = data.normals;
			indices[i] = data.indices;
		}
		return loader.loadModel(vertices, texCoords, normals, indices);
	}

	private static Model loadVersion1(Loader loader, InputStream is) throws IOException {
		ModelData data = loadModelData(is);
		return loader.loadModel(data.vertices, data.texCoords, data.normals, data.indices);
	}

	private static ModelData loadModelData(InputStream is) throws IOException {
		ModelData data = new ModelData();

		int vertexCount = StreamUtils.readInt(is);
		if (vertexCount < 0)
			throw new FileFormatException("Expected vertex count to be greater than 0");

		data.vertices = new float[vertexCount * 3];
		for (int i = 0; i < vertexCount * 3; i++) {
			data.vertices[i] = Float.intBitsToFloat(StreamUtils.readInt(is));
		}

		data.texCoords = new float[vertexCount * 2];
		for (int i = 0; i < vertexCount * 2; i++) {
			data.texCoords[i] = Float.intBitsToFloat(StreamUtils.readInt(is));
		}

		data.normals = new float[vertexCount * 3];
		for (int i = 0; i < vertexCount * 3; i++) {
			data.normals[i] = Float.intBitsToFloat(StreamUtils.readInt(is));
		}

		int indexCount = StreamUtils.readInt(is);
		if (indexCount < 0)
			throw new FileFormatException("Expected index count to be greater than 0");

		data.indices = new int[indexCount];
		for (int i = 0; i < indexCount; i++) {
			data.indices[i] = StreamUtils.readInt(is);
		}

		return data;
	}

	private static class ModelData {
		public float[] vertices;
		public float[] texCoords;
		public float[] normals;
		public int[] indices;
	}
}
