package nullEngine.graphics.buffer;

import nullEngine.managing.ManagedResource;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import util.Sizeof;

import java.nio.ByteBuffer;

/**
 * Wrapper for OpenGL buffer
 */
public class GraphicsBuffer extends ManagedResource {

	private int target;
	private int id;

	private int dataHint = GL15.GL_STATIC_DRAW;
	private int capacity = 0;
	private int bufferSize = 0;
	protected ByteBuffer data = null;
	/**
	 * The lowest index that needs to be updated
	 */
	protected int dirtyMin = 0;
	/**
	 * The highest index that needs to be updated
	 */
	protected int dirtyMax = 0;

	private GraphicsBuffer(String type, int id, int target, int dummy) {
		super(String.valueOf(id), type);
		this.id = id;
		this.target = target;
	}

	/**
	 * Create a new buffer
	 * @param type The type
	 * @param target The target
	 */
	public GraphicsBuffer(String type, int target) {
		this(type, GL15.glGenBuffers(), target, 0);
	}

	/**
	 * Create a new buffer
	 * @param type The type
	 * @param target The target
	 * @param initialCapacity The initial capacity
	 */
	public GraphicsBuffer(String type, int target, int initialCapacity) {
		this(type, GL15.glGenBuffers(), target, 0);
		setSize(initialCapacity);
	}

	/**
	 * Set a range
	 * @param buf The buffer
	 * @param offsetInBytes The offset
	 * @param lengthInBytes The bytes
	 */
	public void setRange(ByteBuffer buf, int offsetInBytes, int lengthInBytes) {
		setMinSize(offsetInBytes + lengthInBytes);
		MemoryUtil.memCopy(MemoryUtil.memAddress(buf), MemoryUtil.memAddress(data) + offsetInBytes, lengthInBytes);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + lengthInBytes, dirtyMax);
	}

	/**
	 * Set a value
	 * @param value The value
	 * @param offsetInBytes The offset
	 */
	public void set(byte value, int offsetInBytes) {
		setMinSize(offsetInBytes + Sizeof.BYTE);
		data.put(offsetInBytes, value);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + Sizeof.BYTE, dirtyMax);
	}

	/**
	 * Update the portion of the buffer that needs updating
	 */
	public void update() {
		if (bufferSize < capacity) {
			forceUpload();
		} else if (dirtyMax > dirtyMin) {
			bind();
			GL15.nglBufferSubData(target, dirtyMin, dirtyMax - dirtyMin, MemoryUtil.memAddress(data) + dirtyMin);
			dirtyMin = dirtyMax = 0;
		}
	}

	/**
	 * Forcibly upload the entire buffer immediately
	 */
	public void forceUpload() {
		bind();
		GL15.glBufferData(target, data, dataHint);
		bufferSize = capacity;
		dirtyMin = dirtyMax = 0;
	}

	/**
	 * Set the size of the buffer
	 * @param newCapacity The new capacity
	 */
	public void setSize(int newCapacity) {
		if (data == null) {
			data = MemoryUtil.memAlloc(newCapacity);
		} else {
			ByteBuffer newData = MemoryUtil.memRealloc(data, newCapacity);
			if (data != null && MemoryUtil.memAddress(newData) != MemoryUtil.memAddress(data)) {
				MemoryUtil.memFree(data);
				data = newData;
			}
		}
		if (capacity > newCapacity)
			bufferSize = 0;
		capacity = newCapacity;
	}

	/**
	 * Set the minumum size of the buffer
	 * @param minCapacity The minimum size
	 */
	protected void setMinSize(int minCapacity) {
		if (minCapacity > capacity)
			setSize(minCapacity);
	}

	/**
	 * Get the id
	 * @return The buffer id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Bind this buffer
	 */
	public void bind() {
		GL15.glBindBuffer(target, id);
	}

	/**
	 * Unbind this buffer
	 */
	public void unbind() {
		GL15.glBindBuffer(target, 0);
	}

	/**
	 * Set the data hint
	 * @param dataHint The data hint
	 */
	public void setDataHint(int dataHint) {
		this.dataHint = dataHint;
	}

	/**
	 * Get the capacity
	 * @return The capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Delete this buffer
	 * @return <code>false</code>
	 */
	@Override
	public boolean delete() {
		if (id != 0)
			GL15.glDeleteBuffers(id);
		if (data != null)
			MemoryUtil.memFree(data);
		return false;
	}
}
