package nullEngine.gl.buffer;

import nullEngine.util.Buffers;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import util.Sizeof;

import java.nio.FloatBuffer;

/**
 * Vertex buffer
 */
public class VertexBuffer extends GraphicsBuffer {

	/**
	 * Create a new vertex buffer
	 */
	public VertexBuffer() {
		super("vbo", GL15.GL_ARRAY_BUFFER);
	}

	/**
	 * Create a new vertex buffer
	 * @param initialCapacity The initial capacity
	 */
	public VertexBuffer(int initialCapacity) {
		super("vbo", GL15.GL_ARRAY_BUFFER, initialCapacity);
	}

	/**
	 * Set a value
	 * @param value The value
	 * @param offset The offset
	 */
	public void set(float value, int offset) {
		setMinSize((offset + 1) * Sizeof.FLOAT);
		data.asFloatBuffer().put(offset, value);
		dirtyMin = Math.min(offset * Sizeof.FLOAT, dirtyMin);
		dirtyMax = Math.max((offset + 1) * Sizeof.FLOAT, dirtyMax);
	}

	/**
	 * Set a range
	 * @param buf The buffer
	 * @param offsetInBytes The offset
	 * @param lengthInBytes The length
	 */
	public void setRange(FloatBuffer buf, int offsetInBytes, int lengthInBytes) {
		setMinSize(offsetInBytes + lengthInBytes);
		MemoryUtil.memCopy(MemoryUtil.memAddress(buf), MemoryUtil.memAddress(data) + offsetInBytes, lengthInBytes);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + lengthInBytes, dirtyMax);
	}

	/**
	 * Create a vertex buffer
	 * @param vertices The vertices
	 * @return The vertex buffer
	 */
	public static VertexBuffer create(float[] vertices) {
		VertexBuffer vbo = new VertexBuffer(vertices.length * Sizeof.FLOAT);
		vbo.setRange(Buffers.createBuffer(vertices), 0, vertices.length * Sizeof.FLOAT);
		return vbo;
	}

	/**
	 * Create a zeroed vertex buffer
	 * @param lengthInBytes The length
	 * @return The vertex buffer
	 */
	public static VertexBuffer createZeroed(int lengthInBytes) {
		VertexBuffer vbo = new VertexBuffer();
		vbo.setRange(BufferUtils.createByteBuffer(lengthInBytes), 0, lengthInBytes);
		return vbo;
	}
}
