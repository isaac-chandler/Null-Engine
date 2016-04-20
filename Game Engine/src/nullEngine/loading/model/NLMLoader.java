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
			} else if (version == 3) {
				return loadVersion3(loader, is);
			} else {
				throw new FileFormatException("Invalid file version: " + version);
			}
		} catch (IOException e) {
			Logs.f(e);
			return null;
		}
	}

	private static Model loadVersion3(Loader loader, InputStream is) throws IOException {
		int lodCount = is.read();
		int[] vertexCounts = new int[lodCount];

		int totalIndices = 0;

		for (int  i = 0; i < lodCount; i++) {
			vertexCounts[i] = StreamUtils.readInt(is);
			totalIndices += vertexCounts[i];
		}

		int[] indices = new int[totalIndices];
		for (int i = 0; i < totalIndices; i++) {
			indices[i] = StreamUtils.readInt(is);
		}

		int vertexCount = StreamUtils.readInt(is);
		float[] vertices = new float[vertexCount * 3];
		for (int i = 0; i < vertexCount * 3; i++) {
			vertices[i] = Float.intBitsToFloat(StreamUtils.readInt(is));
		}

		float[] texCoords = new float[vertexCount * 2];
		for (int i = 0; i < vertexCount * 2; i++) {
			texCoords[i] = Float.intBitsToFloat(StreamUtils.readInt(is));
		}

		float[] normals = new float[vertexCount * 3];
		for (int i = 0; i < vertexCount * 3; i++) {
			normals[i] = Float.intBitsToFloat(StreamUtils.readInt(is));
		}

		return loader.loadModel(vertices, texCoords, normals, indices, vertexCounts);
	}

	private static Model loadVersion2(Loader loader, InputStream is) throws IOException {
		int lodCount = is.read();
		float[][] verticesTemp = new float[lodCount][];
		float[][] texCoordsTemp = new float[lodCount][];
		float[][] normalsTemp = new float[lodCount][];
		int[][] indicesTemp = new int[lodCount][];

		for (int i = 0; i < lodCount; i++) {
			ModelData data = loadModelDataPre3(is);
			verticesTemp[i] = data.vertices;
			texCoordsTemp[i] = data.texCoords;
			normalsTemp[i] = data.normals;
			indicesTemp[i] = data.indices;
		}

		int[] vertexCounts = new int[lodCount];
		int totalVertexDataSize = 0;
		int totalIndexSize = 0;
		for (int i = 0; i < lodCount; i++) {
			vertexCounts[i] = indicesTemp[i].length;
			totalVertexDataSize += verticesTemp[i].length / 3;
			totalIndexSize += indicesTemp[i].length;
		}

		float[] vertices = new float[totalVertexDataSize * 3];
		float[] texCoords = new float[totalVertexDataSize * 2];
		float[] normals = new float[totalVertexDataSize * 3];
		int[] indices = new int[totalIndexSize];

		int totalOffset = 0;
		for (int i = 0; i < lodCount; i++) {
			int len = verticesTemp[i].length / 3;
			for (int j = 0; j < len; j++) {
				vertices[(totalOffset + j) * 3] = verticesTemp[i][j * 3];
				vertices[(totalOffset + j) * 3 + 1] = verticesTemp[i][j * 3 + 1];
				vertices[(totalOffset + j) * 3 + 2] = verticesTemp[i][j * 3 + 2];

				texCoords[(totalOffset + j) * 2] = texCoordsTemp[i][j * 2];
				texCoords[(totalOffset + j) * 2 + 1] = texCoordsTemp[i][j * 2 + 1];

				normals[(totalOffset + j) * 3] = normalsTemp[i][j * 3];
				normals[(totalOffset + j) * 3 + 1] = normalsTemp[i][j * 3 + 1];
				normals[(totalOffset + j) * 3 + 2] = normalsTemp[i][j * 3 + 2];
			}
			totalOffset += len;
		}

		totalOffset = 0;

		for (int[] indexArray : indicesTemp) {
			for (int index : indexArray) {
				indices[totalOffset++] = index;
			}
		}

		return loader.loadModel(vertices, texCoords, normals, indices, vertexCounts);
	}

	private static Model loadVersion1(Loader loader, InputStream is) throws IOException {
		ModelData data = loadModelDataPre3(is);
		return loader.loadModel(data.vertices, data.texCoords, data.normals, data.indices);
	}

	private static ModelData loadModelDataPre3(InputStream is) throws IOException {
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
