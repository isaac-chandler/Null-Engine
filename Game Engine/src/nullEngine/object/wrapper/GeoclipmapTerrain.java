package nullEngine.object.wrapper;

import math.Vector4f;
import nullEngine.gl.Material;
import nullEngine.gl.buffer.IndexBuffer;
import nullEngine.gl.buffer.VertexBuffer;
import nullEngine.gl.model.Model;
import nullEngine.gl.model.VertexAttribPointer;
import nullEngine.gl.renderer.Renderer;
import nullEngine.object.GameObject;
import nullEngine.object.component.ModelComponent;
import org.lwjgl.opengl.GL30;
import util.BitFieldInt;
import util.Sizeof;

/**
 * A terrain class that implements a simplified version of the <a href="https://developer.nvidia.com/gpugems/GPUGems2/gpugems2_chapter02.html">Geoclipmapping algorithm</a>
 */
public class GeoclipmapTerrain extends GameObject {

	private Material[] materials;
	private GameObject cameraObject;
	private HeightMap heightMap;
	private float size;

	private static class ModelObject extends GameObject {
		public ModelComponent component;

		public ModelObject(Model model, Material material, float scale) {
			component = new ModelComponent(material, model);
			addComponent(component);
			getTransform().setScale(new Vector4f(scale, 1, scale));
		}
	}

	private static VertexBuffer generateBlockVertices(int m) {
		VertexBuffer vertices = new VertexBuffer(((m + 1) * (m + 1) * 3) * Sizeof.FLOAT);
		for (int z = 0; z <= m; z++) {
			for (int x = 0; x <= m; x++) {
				vertices.set((float) x / m - 0.5f, ((z * (m + 1) + x) * 3));
				vertices.set(0, ((z * (m + 1) + x) * 3 + 1));
				vertices.set((float) z / m - 0.5f, ((z * (m + 1) + x) * 3 + 2));
			}
		}

		return vertices;
	}

	private static Model generateBlock(int m, VertexBuffer vertices) {
		int pointer = 0;
		IndexBuffer indices = new IndexBuffer(((m * m - m) * 6) * Sizeof.INT);
		for (int z = 0; z < m; z++) {
			for (int x = 0; x < m; x++) {
				if (x == 0) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1));
						int bottom = topLeft + m + 2;
						int topRight = bottom + m;
						indices.set(topRight, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
						indices.set(topLeft, pointer);
						pointer++;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + 1;
						int middle = top + m + 1;
						int bottom = middle + m + 1;
						int left = top + m;
						indices.set(top, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;

						indices.set(bottom, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;
					}
				} else if (x == m - 1) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1)) + m;
						int bottom = topLeft + m;
						int topRight = bottom + m + 2;
						indices.set(topRight, pointer);
						pointer++;
						indices.set(topLeft, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + x;
						int middle = top + m + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices.set(top, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(right, pointer);
						pointer++;

						indices.set(bottom, pointer);
						pointer++;
						indices.set(right, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
					}
				}
				if (z == 0) {
					if (x % 2 == 0) {
						int topLeft = x;
						int topRight = x + 2;
						int bottom = topRight + m;
						indices.set(topRight, pointer);
						pointer++;
						indices.set(topLeft, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
					} else if (x < m - 1) {
						int top = x + 1;
						int left = top + m;
						int middle = left + 1;
						int right = middle + 1;
						indices.set(top, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;

						indices.set(right, pointer);
						pointer++;
						indices.set(top, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
					}
				} else if (z == m - 1) {
					if (x % 2 == 0) {
						int top = (z * (m + 1)) + x + 1;
						int bottomLeft = top + m;
						int bottomRight = bottomLeft + 2;
						indices.set(bottomRight, pointer);
						pointer++;
						indices.set(top, pointer);
						pointer++;
						indices.set(bottomLeft, pointer);
						pointer++;
					} else if (x < m - 1) {
						int left = (z * (m + 1)) + x;
						int middle = left + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices.set(bottom, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;

						indices.set(right, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
					}
				} else if (x > 0 && x < m - 1) {
					int topLeft = (z * (m + 1)) + x;
					int topRight = topLeft + 1;
					int bottomLeft = ((z + 1) * (m + 1)) + x;
					int bottomRight = bottomLeft + 1;
					indices.set(topRight, pointer);
					pointer++;
					indices.set(topLeft, pointer);
					pointer++;
					indices.set(bottomLeft, pointer);
					pointer++;

					indices.set(bottomRight, pointer);
					pointer++;
					indices.set(topRight, pointer);
					pointer++;
					indices.set(bottomLeft, pointer);
					pointer++;
				}
			}
		}

		return new Model(GL30.glGenVertexArrays(), new int[] {(m * m - m) * 6}, new int[] {0}, (float) Math.sqrt(2) / 2, indices, VertexAttribPointer.createVec3AttribPointer(vertices));
	}

	private static Model generateRing(int m, VertexBuffer vertices) {
		int pointer = 0;
		IndexBuffer indices = new IndexBuffer((9 * m * m / 2 - 6 * m) * Sizeof.INT);
		for (int z = 0; z < m; z++) {
			for (int x = 0; x < m; x++) {
				if (x == 0) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1));
						int bottom = topLeft + m + 2;
						int topRight = bottom + m;
						indices.set(topRight, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
						indices.set(topLeft, pointer);
						pointer++;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + 1;
						int middle = top + m + 1;
						int bottom = middle + m + 1;
						int left = top + m;
						indices.set(top, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;

						indices.set(bottom, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;
					}
				} else if (x == m - 1) {
					if (z % 2 == 0) {
						int topLeft = (z * (m + 1)) + m;
						int bottom = topLeft + m;
						int topRight = bottom + m + 2;
						indices.set(topRight, pointer);
						pointer++;
						indices.set(topLeft, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
					} else if (z < m - 1) {
						int top = (z * (m + 1)) + x;
						int middle = top + m + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices.set(top, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(right, pointer);
						pointer++;

						indices.set(bottom, pointer);
						pointer++;
						indices.set(right, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
					}
				}
				if (z == 0) {
					if (x % 2 == 0) {
						int topLeft = x;
						int topRight = x + 2;
						int bottom = topRight + m;
						indices.set(topRight, pointer);
						pointer++;
						indices.set(topLeft, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
					} else if (x < m - 1) {
						int top = x + 1;
						int left = top + m;
						int middle = left + 1;
						int right = middle + 1;
						indices.set(top, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;

						indices.set(right, pointer);
						pointer++;
						indices.set(top, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
					}
				} else if (z == m - 1) {
					if (x % 2 == 0) {
						int top = (z * (m + 1)) + x + 1;
						int bottomLeft = top + m;
						int bottomRight = bottomLeft + 2;
						indices.set(bottomRight, pointer);
						pointer++;
						indices.set(top, pointer);
						pointer++;
						indices.set(bottomLeft, pointer);
						pointer++;
					} else if (x < m - 1) {
						int left = (z * (m + 1)) + x;
						int middle = left + 1;
						int right = middle + 1;
						int bottom = right + m;
						indices.set(bottom, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(left, pointer);
						pointer++;

						indices.set(right, pointer);
						pointer++;
						indices.set(middle, pointer);
						pointer++;
						indices.set(bottom, pointer);
						pointer++;
					}
				} else if (x > 0 && x < m - 1 && (x < m / 4 || x >= m - m / 4 || z < m / 4 || z >= m - m / 4)) {
					int topLeft = (z * (m + 1)) + x;
					int topRight = topLeft + 1;
					int bottomLeft = ((z + 1) * (m + 1)) + x;
					int bottomRight = bottomLeft + 1;
					indices.set(topRight, pointer);
					pointer++;
					indices.set(topLeft, pointer);
					pointer++;
					indices.set(bottomLeft, pointer);
					pointer++;

					indices.set(bottomRight, pointer);
					pointer++;
					indices.set(topRight, pointer);
					pointer++;
					indices.set(bottomLeft, pointer);
					pointer++;
				}
			}
		}

		return new Model(GL30.glGenVertexArrays(), new int[] {9 * m * m / 2 - 6 * m}, new int[] {0}, (float) Math.sqrt(2) / 2, indices, VertexAttribPointer.createVec3AttribPointer(vertices));
	}

	private static final Vector4f MUL = new Vector4f(1, 0, 1);

	/**
	 * Render this terrain
	 *
	 * @param renderer The renderer that is rendering this object
	 * @param flags    The render flags
	 */
	@Override
	public void render(Renderer renderer, BitFieldInt flags) {
		getTransform().setPos(cameraObject.getTransform().getWorldPos().mul(MUL, null));
		super.render(renderer, flags);
	}

	/**
	 * Get the terain height
	 *
	 * @param x The x position
	 * @param z The z position
	 * @return The terrain height at (x, z)
	 */
	public float getTerrainHeight(float x, float z) {
		float squareSize = size / (heightMap.getResolution() - 1);
		while (x >= size / 2)
			x -= size;
		while (z >= size / 2)
			z -= size;

		while (x < -size / 2)
			x += size;
		while (z < -size / 2)
			z += size;

		x += size / 2;
		z += size / 2;
		int gridX = (int) (x / squareSize);
		int gridZ = (int) (z / squareSize);

		float xExtra = (x / squareSize) - (int) (x / squareSize);
		float zExtra = (z / squareSize) - (int) (z / squareSize);

		float a = cubic(heightMap.getHeight(gridX - 1, gridZ - 1), heightMap.getHeight(gridX, gridZ - 1), heightMap.getHeight(gridX + 1, gridZ - 1), heightMap.getHeight(gridX + 2, gridZ - 1), xExtra);
		float b = cubic(heightMap.getHeight(gridX - 1, gridZ), heightMap.getHeight(gridX, gridZ), heightMap.getHeight(gridX + 1, gridZ), heightMap.getHeight(gridX + 2, gridZ), xExtra);
		float c = cubic(heightMap.getHeight(gridX - 1, gridZ + 1), heightMap.getHeight(gridX, gridZ + 1), heightMap.getHeight(gridX + 1, gridZ + 1), heightMap.getHeight(gridX + 2, gridZ + 1), xExtra);
		float d = cubic(heightMap.getHeight(gridX - 1, gridZ + 2), heightMap.getHeight(gridX, gridZ + 2), heightMap.getHeight(gridX + 1, gridZ + 2), heightMap.getHeight(gridX + 2, gridZ + 2), xExtra);

		return cubic(a, b, c, d, zExtra);
	}

	private static float cubic (float a, float b, float c, float d, float amount) {
		return b + 0.5f * amount * (c - a + amount * (2f * a - 5.0f * b + 4.0f * c - d + amount * (3.0f * (b - c) + d - a)));
	}

	/**
	 * Create a new terrain
	 *
	 * @param material     The material to be used
	 * @param heightMap    The height map to be used
	 * @param size         The terrain radius
	 * @param detail       The amount of detail in the terrain
	 * @param levels       The number of levels of detail
	 * @param cameraObject The camera
	 */
	public GeoclipmapTerrain(Material material, HeightMap heightMap, float size, int detail, int levels, GameObject cameraObject) {
		if (((detail & (detail - 1)) != 0) || detail < 4) {
			throw new IllegalArgumentException("n must be 2^x where x is an integer greater than 2");
		}

		this.cameraObject = cameraObject;
		this.heightMap = heightMap;
		this.size = size;

		float scale = size / (1 << (levels - 1));

		material.setFloat("size", size / 2);
		material.setTexture("height", heightMap.getHeightMap());

		materials = new Material[levels];
		float offset = 1f / detail;
		for (int i = 0; i < levels; i++) {
			materials[levels - i - 1] = material.clone();
			materials[levels - i - 1].setFloat("offset", offset);
			offset /= 2;
		}

		VertexBuffer vertices = generateBlockVertices(detail);

		Model ring = generateRing(detail, vertices);
		Model block = generateBlock(detail, vertices);

		addChild(new ModelObject(block, materials[0], scale));

		for (int i = 1; i < levels; i++) {
			scale *= 2;
			addChild(new ModelObject(ring, materials[i], scale));
		}
	}
}
