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
			if (version != 1) {
				Logs.d(version);
				throw new FileFormatException("Invalid file version");
			}

			int vertexCount = StreamUtils.readInt(is);
			if (vertexCount < 0)
				throw new FileFormatException("Expected vertex count to be greater than 0");

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

			int indexCount = StreamUtils.readInt(is);
			if (indexCount < 0)
				throw new FileFormatException("Expected index count to be greater than 0");

			int[] indices = new int[indexCount];
			for (int i = 0; i < indexCount; i++) {
				indices[i] = StreamUtils.readInt(is);
			}

			return loader.loadModel(vertices, texCoords, normals, indices);
		} catch (IOException e) {
			Logs.f(e);
			return null;
		}
	}
}
