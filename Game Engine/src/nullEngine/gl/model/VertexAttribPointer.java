package nullEngine.gl.model;

import nullEngine.managing.ManagedResource;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import util.Sizeof;

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

	public VertexAttribPointer(VertexBuffer vbo, int size, int type, boolean normalized, int stride, long offset) {
		this(vbo, size, type, normalized, stride, offset, 0);
	}

	public void setInstanced(boolean instanced) {
		this.instanced = instanced;
	}

	public static VertexAttribPointer createVec2AttribPointer(VertexBuffer vbo) {
		return new VertexAttribPointer(vbo, 2, GL11.GL_FLOAT, false, 2 * Sizeof.FLOAT, 0);
	}

	public static VertexAttribPointer createVec3AttribPointer(VertexBuffer vbo) {
		return new VertexAttribPointer(vbo, 3, GL11.GL_FLOAT, false, 3 * Sizeof.FLOAT, 0);
	}

	public static VertexAttribPointer createMat4AttribPointer(VertexBuffer vbo) {
		return new VertexAttribPointer(vbo, 16, GL11.GL_FLOAT, false, 16 * Sizeof.FLOAT, 0, 4 * Sizeof.FLOAT);
	}

	public void update() {
		vbo.update();
	}

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

	@Override
	public void delete() {

	}

	public int requiredAttribs() {
		return (size + 3) / 4;
	}

	public VertexBuffer getVertexBuffer() {
		return vbo;
	}
}
