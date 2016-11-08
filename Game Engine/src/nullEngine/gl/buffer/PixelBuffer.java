package nullEngine.gl.buffer;

import nullEngine.managing.ManagedResource;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class PixelBuffer extends ManagedResource {

	private int id = 0;
	private int size;

	private PixelBuffer(int id, int size) {
		super(String.valueOf(id), "pbo");
		this.id = id;
		this.size = size;
		bind();
		GL15.nglBufferData(GL21.GL_PIXEL_PACK_BUFFER, size, MemoryUtil.NULL, GL15.GL_STREAM_READ);
		unbind();
	}

	public PixelBuffer(int size) {
		this(GL15.glGenBuffers(), size);
	}

	public void bind() {
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, id);
	}

	public ByteBuffer get(ByteBuffer dest) {
		if (dest == null)
			dest = BufferUtils.createByteBuffer(size);
		bind();
		ByteBuffer src = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY);
		MemoryUtil.memCopy(MemoryUtil.memAddress(src), MemoryUtil.memAddress(dest), size);
		GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
		unbind();
		return dest;
	}

	public void unbind() {
		GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
	}

	@Override
	public boolean delete() {
		GL15.glDeleteBuffers(id);
		return true;
	}
}
