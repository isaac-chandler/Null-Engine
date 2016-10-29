package nullEngine.gl.buffer;

import nullEngine.managing.ManagedResource;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import util.Sizeof;

import java.nio.ByteBuffer;

public class GraphicsBuffer extends ManagedResource {

	private int target;
	private int id;

	private int dataHint = GL15.GL_STATIC_DRAW;
	private int capacity = 0;
	private int bufferSize = 0;
	protected ByteBuffer data = null;
	protected int dirtyMin = 0;
	protected int dirtyMax = 0;


	private GraphicsBuffer(String type, int id, int target, int dummy) {
		super(String.valueOf(id), type);
		this.id = id;
		this.target = target;
	}

	public GraphicsBuffer(String type, int target) {
		this(type, GL15.glGenBuffers(), target, 0);
	}

	public GraphicsBuffer(String type, int target, int initialCapacity) {
		this(type, GL15.glGenBuffers(), target, 0);
		setSize(initialCapacity);
	}

	public void setRange(ByteBuffer buf, int offsetInBytes, int lengthInBytes) {
		setMinSize(offsetInBytes + lengthInBytes);
		MemoryUtil.memCopy(MemoryUtil.memAddress(buf), MemoryUtil.memAddress(data) + offsetInBytes, lengthInBytes);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + lengthInBytes, dirtyMax);
	}

	public void set(byte value, int offsetInBytes) {
		setMinSize(offsetInBytes + Sizeof.BYTE);
		data.put(offsetInBytes, value);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + Sizeof.BYTE, dirtyMax);
	}


	public void update() {
		if (bufferSize < capacity) {
			forceUpload();
		} else if (dirtyMax > dirtyMin) {
			bind();
			GL15.nglBufferSubData(target, dirtyMin, dirtyMax - dirtyMin, MemoryUtil.memAddress(data) + dirtyMin);
			dirtyMin = dirtyMax = 0;
		}
	}

	public void forceUpload() {
		bind();
		GL15.glBufferData(target, capacity, data, dataHint);
		bufferSize = capacity;
		dirtyMin = dirtyMax = 0;
	}

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

	protected void setMinSize(int minCapacity) {
		if (minCapacity > capacity)
			setSize(minCapacity);
	}

	public int getId() {
		return id;
	}

	public void bind() {
		GL15.glBindBuffer(target, id);
	}

	public void unbind() {
		GL15.glBindBuffer(target, 0);
	}

	public void setDataHint(int dataHint) {
		this.dataHint = dataHint;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public boolean delete() {
		if (id != 0)
			GL15.glDeleteBuffers(id);
		if (data != null)
			MemoryUtil.memFree(data);
		return false;
	}
}
