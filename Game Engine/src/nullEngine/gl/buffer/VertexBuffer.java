package nullEngine.gl.buffer;

import nullEngine.util.Buffers;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import util.Sizeof;

import java.nio.FloatBuffer;

public class VertexBuffer extends GraphicsBuffer {

	public VertexBuffer() {
		super("vbo", GL15.GL_ARRAY_BUFFER);
	}

	public VertexBuffer(int initialCapacity) {
		super("vbo", GL15.GL_ARRAY_BUFFER, initialCapacity);
	}

	public void set(float value, int offset) {
		setMinSize((offset + 1) * Sizeof.FLOAT);
		data.asFloatBuffer().put(offset, value);
		dirtyMin = Math.min(offset * Sizeof.FLOAT, dirtyMin);
		dirtyMax = Math.max((offset + 1) * Sizeof.FLOAT, dirtyMax);
	}

	public void setRange(FloatBuffer buf, int offsetInBytes, int lengthInBytes) {
		setMinSize(offsetInBytes + lengthInBytes);
		MemoryUtil.memCopy(MemoryUtil.memAddress(buf), MemoryUtil.memAddress(data) + offsetInBytes, lengthInBytes);
		dirtyMin = Math.min(offsetInBytes, dirtyMin);
		dirtyMax = Math.max(offsetInBytes + lengthInBytes, dirtyMax);
	}

	public static VertexBuffer create(float[] vertices) {
		VertexBuffer vbo = new VertexBuffer(vertices.length * Sizeof.FLOAT);
		vbo.setRange(Buffers.createBuffer(vertices), 0, vertices.length * Sizeof.FLOAT);
		return vbo;
	}

	public static VertexBuffer createZeroed(int lengthInBytes) {
		VertexBuffer vbo = new VertexBuffer();
		vbo.setRange(BufferUtils.createByteBuffer(lengthInBytes), 0, lengthInBytes);
		return vbo;
	}
}
