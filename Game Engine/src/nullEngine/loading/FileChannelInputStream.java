package nullEngine.loading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelInputStream extends InputStream {

	private long mark = 0;
	private final FileChannel channel;

	private static final ByteBuffer buf = ByteBuffer.allocate(1);

	public FileChannelInputStream(String name) throws IOException {
		channel = FileChannel.open(Paths.get(name), StandardOpenOption.READ);
	}

	public FileChannelInputStream(File file) throws IOException {
		this(file.getAbsolutePath());
	}

	@Override
	public synchronized int read() throws IOException {
		int ret = channel.read(buf);
		buf.flip();
		return ret == 0 ? -1 : buf.get(0);
	}

	@Override
	public int read(byte[] b) throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(b);
		return channel.read(buf);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(b, off, len);
		return channel.read(buf);
	}

	@Override
	public long skip(long n) throws IOException {
		if (n <= 0)
			return 0;

		long pos = channel.position();
		long size = channel.size();
		if (pos + n > size) {
			channel.position(size);
			return size - pos;
		}
		channel.position(pos + n);
		return n;
	}

	@Override
	public int available() throws IOException {
		return (int) (channel.size() - channel.position());
	}

	@Override
	public void close() throws IOException {
		channel.close();
	}

	@Override
	public synchronized void mark(int readlimit) {
		try {
			mark = channel.position();
		} catch (IOException e) {

		}
	}

	@Override
	public synchronized void reset() throws IOException {
		channel.position(mark);
	}

	@Override
	public boolean markSupported() {
		return true;
	}
}
