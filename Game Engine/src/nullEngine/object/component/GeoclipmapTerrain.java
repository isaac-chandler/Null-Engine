package nullEngine.object.component;

import math.Vector4f;
import nullEngine.gl.Material;
import nullEngine.gl.model.Model;
import nullEngine.gl.renderer.Renderer;
import nullEngine.loading.Loader;
import nullEngine.object.GameObject;

public class GeoclipmapTerrain extends GameObject {

	private Material material;
	private GameObject cameraObject;

	private static class ModelObject extends GameObject {
		public ModelObject(Model model, Material material, float scale) {
			addComponent(new ModelComponent(material, model));
			getTransform().setScale(new Vector4f(scale, 1, scale));
		}
	}

	private static int generateBlockVertices(int m, Loader loader) {
		float[] vertices = new float[(m + 1) * (m + 1) * 3];
		for (int z = 0; z < m + 1; z++) {
			for (int x = 0; x < m + 1; x++) {
				vertices[(z * (m + 1) + x) * 3] = (float) x / m - 0.5f;
				vertices[(z * (m + 1) + x) * 3 + 2] = (float) z / m - 0.5f;
			}
		}

		return loader.createVBO(vertices);
	}

	private static Model generateBlock(int m, int vbo, int texCoords, int normals, Loader loader) {
		int pointer = 0;
		int[] indices = new int[(m * m - m) * 6];
		for (int z = 0; z < m; z++) {
			for (int x = 0; x < m; x++) {
				if (x == 0) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1));
						int bottom = topLeft + m + 2;
						int topRight = bottom + m;
						indices[pointer++] = topRight;
						indices[pointer++] = bottom;
						indices[pointer++] = topLeft;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + 1;
						int middle = top + m + 1;
						int bottom = middle + m + 1;
						int left = top + m;
						indices[pointer++] = top;
						indices[pointer++] = left;
						indices[pointer++] = middle;

						indices[pointer++] = bottom;
						indices[pointer++] = middle;
						indices[pointer++] = left;
					}
				} else if (x == m - 1) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1)) + m;
						int bottom = topLeft + m;
						int topRight = bottom + m + 2;
						indices[pointer++] = topRight;
						indices[pointer++] = topLeft;
						indices[pointer++] = bottom;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + x;
						int middle = top + m + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices[pointer++] = top;
						indices[pointer++] = middle;
						indices[pointer++] = right;

						indices[pointer++] = bottom;
						indices[pointer++] = right;
						indices[pointer++] = middle;
					}
				}
				if (z == 0) {
					if (x % 2 == 0) {
						int topLeft = x;
						int topRight = x + 2;
						int bottom = topRight + m;
						indices[pointer++] = topRight;
						indices[pointer++] = topLeft;
						indices[pointer++] = bottom;
					} else if (x < m - 1) {
						int top = x + 1;
						int left = top + m;
						int middle = left + 1;
						int right = middle + 1;
						indices[pointer++] = top;
						indices[pointer++] = left;
						indices[pointer++] = middle;

						indices[pointer++] = right;
						indices[pointer++] = top;
						indices[pointer++] = middle;
					}
				} else if (z == m - 1) {
					if (x % 2 == 0) {
						int top = (z * (m + 1)) + x + 1;
						int bottomLeft = top + m;
						int bottomRight = bottomLeft + 2;
						indices[pointer++] = bottomRight;
						indices[pointer++] = top;
						indices[pointer++] = bottomLeft;
					} else if (x < m - 1) {
						int left = (z * (m + 1)) + x;
						int middle = left + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices[pointer++] = bottom;
						indices[pointer++] = middle;
						indices[pointer++] = left;

						indices[pointer++] = right;
						indices[pointer++] = middle;
						indices[pointer++] = bottom;
					}
				} else if (x > 0 && x < m - 1) {
					int topLeft = (z * (m + 1)) + x;
					int topRight = topLeft + 1;
					int bottomLeft = ((z + 1) * (m + 1)) + x;
					int bottomRight = bottomLeft + 1;
					indices[pointer++] = topRight;
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomLeft;

					indices[pointer++] = bottomRight;
					indices[pointer++] = topRight;
					indices[pointer++] = bottomLeft;
				}
			}
		}

		return loader.loadModel(vbo, (float) Math.sqrt(2) / 2, texCoords, normals, indices);
	}

	private static Model generateRing(int m, int vbo, int texCoords, int normals, Loader loader) {
		int pointer = 0;
		int[] indices = new int[9 * m * m / 2 -  6 * m];
		for (int z = 0; z < m; z++) {
			for (int x = 0; x < m; x++) {
				if (x == 0) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1));
						int bottom = topLeft + m + 2;
						int topRight = bottom + m;
						indices[pointer++] = topRight;
						indices[pointer++] = bottom;
						indices[pointer++] = topLeft;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + 1;
						int middle = top + m + 1;
						int bottom = middle + m + 1;
						int left = top + m;
						indices[pointer++] = top;
						indices[pointer++] = left;
						indices[pointer++] = middle;

						indices[pointer++] = bottom;
						indices[pointer++] = middle;
						indices[pointer++] = left;
					}
				} else if (x == m - 1) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1)) + m;
						int bottom = topLeft + m;
						int topRight = bottom + m + 2;
						indices[pointer++] = topRight;
						indices[pointer++] = topLeft;
						indices[pointer++] = bottom;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + x;
						int middle = top + m + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices[pointer++] = top;
						indices[pointer++] = middle;
						indices[pointer++] = right;

						indices[pointer++] = bottom;
						indices[pointer++] = right;
						indices[pointer++] = middle;
					}
				}
				if (z == 0) {
					if (x % 2 == 0) {
						int topLeft = x;
						int topRight = x + 2;
						int bottom = topRight + m;
						indices[pointer++] = topRight;
						indices[pointer++] = topLeft;
						indices[pointer++] = bottom;
					} else if (x < m - 1) {
						int top = x + 1;
						int left = top + m;
						int middle = left + 1;
						int right = middle + 1;
						indices[pointer++] = top;
						indices[pointer++] = left;
						indices[pointer++] = middle;

						indices[pointer++] = right;
						indices[pointer++] = top;
						indices[pointer++] = middle;
					}
				} else if (z == m - 1) {
					if (x % 2 == 0) {
						int top = (z * (m + 1)) + x + 1;
						int bottomLeft = top + m;
						int bottomRight = bottomLeft + 2;
						indices[pointer++] = bottomRight;
						indices[pointer++] = top;
						indices[pointer++] = bottomLeft;
					} else if (x < m - 1) {
						int left = (z * (m + 1)) + x;
						int middle = left + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices[pointer++] = bottom;
						indices[pointer++] = middle;
						indices[pointer++] = left;

						indices[pointer++] = right;
						indices[pointer++] = middle;
						indices[pointer++] = bottom;
					}
				} else if (x > 0 && x < m - 1 && (x < m / 4 || x >= m - m / 4 || z < m / 4 || z >= m - m / 4)) {
					int topLeft = (z * (m + 1)) + x;
					int topRight = topLeft + 1;
					int bottomLeft = ((z + 1) * (m + 1)) + x;
					int bottomRight = bottomLeft + 1;
					indices[pointer++] = topRight;
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomLeft;

					indices[pointer++] = bottomRight;
					indices[pointer++] = topRight;
					indices[pointer++] = bottomLeft;
				}
			}
		}

		return loader.loadModel(vbo, (float) Math.sqrt(2) / 2, texCoords, normals, indices);
	}

	private static final Vector4f MUL = new Vector4f(1, 0, 1);

	@Override
	public void render(Renderer renderer) {
		getTransform().setPos(cameraObject.getTransform().getWorldPos().mul(MUL, null));
		super.render(renderer);
	}

	public GeoclipmapTerrain(Material material, float size, int detail, int levels, Loader loader, GameObject cameraObject) {
		if (((detail & (detail - 1)) != 0) || detail < 4) {
			throw new IllegalArgumentException("n must be 2^x where x is an integer greater than 2");
		}

		this.material = material;
		this.cameraObject = cameraObject;

		float scale = size / (1 << (levels - 1));

		material.setFloat("size", size / 2);

		int vbo = generateBlockVertices(detail, loader);
		int texCoords = loader.createVBO(new float[(detail + 1) * (detail + 1) * 2]);
		int normals = loader.createVBO(new float[(detail + 1) * (detail + 1) * 3]);

		Model block = generateBlock(detail, vbo, texCoords, normals, loader);
		Model ring = generateRing(detail, vbo, texCoords, normals, loader);

		addChild(new ModelObject(block, material, scale));

		for (int  i = 1; i < levels; i++) {
			scale *= 2;
			addChild(new ModelObject(ring, material, scale));
		}
	}
}
