package nullEngine.gl.model;

import nullEngine.gl.buffer.VertexBuffer;
import nullEngine.managing.ManagedResource;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import util.Sizeof;

/**
 * Vertex attribute pointer
 */
public class VertexAttribPointer extends ManagedResource {

	private static int currentVertexAttribID = 0;
	private int vertexAttribID;

	private VertexBuffer vbo;
	private int size;
	private int type;
	private boolean normalized;
	private int stride;
	private long offset;
	private long extraOffset;
	private boolean instanced = false;

	/**
	 * Create a new vertex attribute pointer
	 * @param vbo The vertex buffer
	 * @param size The size
	 * @param type The type
	 * @param normalized Normalize?
	 * @param stride The stride
	 * @param offset The offset
	 * @param extraOffset The extra offset for each extra attribute
	 */
	public VertexAttribPointer(VertexBuffer vbo, int size, int type, boolean normalized, int stride, long offset, long extraOffset) {
		super(String.valueOf(currentVertexAttribID), "vertexAttrib", vbo);
		vertexAttribID = currentVertexAttribID++;
		this.vbo = vbo;
		this.size = size;
		this.type = type;
		this.normalized = normalized;
		this.stride = stride;
		this.offset = offset;
		this.extraOffset = extraOffset;
	}

	/**
	 * Create a new vertex attribute pointer
	 * @param vbo The vertex buffer
	 * @param size The size
	 * @param type The type
	 * @param normalized Normalize?
	 * @param stride The stride
	 * @param offset The offset
	 */
	public VertexAttribPointer(VertexBuffer vbo, int size, int type, boolean normalized, int stride, long offset) {
		this(vbo, size, type, normalized, stride, offset, 0);
	}

	/**
	 * Set wether this attribute is instanced
	 * @param instanced Wether this attribute is instanced
	 */
	public void setInstanced(boolean instanced) {
		this.instanced = instanced;
	}

	/**
	 * Create an attrib pointer for vec2
	 * @param vbo The vertex buffer
	 * @return The attrib pointer
	 */
	public static VertexAttribPointer createVec2AttribPointer(VertexBuffer vbo) {
		return new VertexAttribPointer(vbo, 2, GL11.GL_FLOAT, false, 2 * Sizeof.FLOAT, 0);
	}

	/**
	 * Create an attrib pointer for vec3
	 * @param vbo The vertex buffer
	 * @return The attrib pointer
	 */
	public static VertexAttribPointer createVec3AttribPointer(VertexBuffer vbo) {
		return new VertexAttribPointer(vbo, 3, GL11.GL_FLOAT, false, 3 * Sizeof.FLOAT, 0);
	}

	/**
	 * Create an attrib pointer for mat4
	 * @param vbo The vertex buffer
	 * @return The attrib pointer
	 */
	public static VertexAttribPointer createMat4AttribPointer(VertexBuffer vbo) {
		return new VertexAttribPointer(vbo, 16, GL11.GL_FLOAT, false, 16 * Sizeof.FLOAT, 0, 4 * Sizeof.FLOAT);
	}

	/**
	 * Update the vertex attrib pointer
	 */
	public void update() {
		vbo.update();
	}

	/**
	 * Bind this attrib, automatically binds multiple attribs for more than 4 compnent objects
	 * @param attrib The starting attrib
	 * @return The ending attrib
	 */
	public int bind(int attrib) {
		int sizeNeeded = size;
		vbo.bind();
		for (int i = 0; i < requiredAttribs(); i++) {
			GL20.glEnableVertexAttribArray(attrib);
			GL20.glVertexAttribPointer(attrib, Math.min(sizeNeeded, 4), type, normalized, stride, offset + extraOffset * i);
			if (instanced)
				GL33.glVertexAttribDivisor(attrib, 1);
			attrib++;
			sizeNeeded -= 4;
		}
		vbo.unbind();
		return attrib;
	}

	/**
	 * Delete this vertex attrib pointer
	 * @return <code>false</code>
	 */
	@Override
	public boolean delete() {
		return false;
	}

	/**
	 * Get the number of attribs required for this attrib pointer
	 * @return The number of attribs
	 */
	public int requiredAttribs() {
		return (size + 3) / 4;
	}

	/**
	 * Get the vertex buffer
	 * @return The vertex buffer
	 */
	public VertexBuffer getVertexBuffer() {
		return vbo;
	}
}
