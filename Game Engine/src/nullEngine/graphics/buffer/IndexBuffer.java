package nullEngine.graphics.buffer;

import nullEngine.util.Buffers;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import util.Sizeof;

import java.nio.IntBuffer;

/**
 * Index buffer
 */
public class IndexBuffer extends GraphicsBuffer {

	/**
	 * Create a new index buffer
	 */
	public IndexBuffer() {
		super("ibo", GL15.GL_ELEMENT_ARRAY_BUFFER);
	}

	/**
	 * Create a new index buffer
	 * @param initialCapacity The initial capacity
	 */
	public IndexBuffer(int initialCapacity) {
		super("ibo", GL15.GL_ELEMENT_ARRAY_BUFFER, initialCapacity);
	}

	/**
	 * Set a range
	 * @param buf The buffer
	 * @param offsetInBytes The offset
	 * @param lengthInBytes The length
	 */
	public void setRange(IntBuffer buf, int offsetInBytes, int lengthInBytes) {
		setMinSize(offsetInBytes + lengthInBytes);
		MemoryUtil.memCopy(MemoryUtil.memAddress(buf), MemoryUtil.memAddress(data) + offsetInBytes, lengthInBytes);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + lengthInBytes, dirtyMax);
	}

	/**
	 * Set a value
	 * @param value The value
	 * @param offset the offset
	 */
	public void set(int value, int offset) {
		setMinSize((offset + 1) * Sizeof.INT);
		data.asIntBuffer().put(offset, value);
		dirtyMin = Math.min(offset * Sizeof.INT, dirtyMin);
		dirtyMax = Math.max((offset + 1) * Sizeof.INT, dirtyMax);
	}

	/**
	 * Create an index buffer
	 * @param indices The indices
	 * @return The index buffer
	 */
	public static IndexBuffer create(int[] indices) {
		IndexBuffer ibo = new IndexBuffer();
		ibo.setRange(Buffers.createBuffer(indices), 0, indices.length * Sizeof.INT);
		return ibo;
	}
}
