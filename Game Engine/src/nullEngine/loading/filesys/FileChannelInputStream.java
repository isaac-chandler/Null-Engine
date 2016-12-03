package nullEngine.loading.filesys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * An implementation of InputStream that wraps FileChannel
 */
public class FileChannelInputStream extends InputStream {

	private long mark = 0;
	private final FileChannel channel;

	private static final ByteBuffer buf = ByteBuffer.allocate(1);

	/**
	 * Create a new FileChannelInputStream
	 *
	 * @param in The file to use
	 * @throws IOException If it failed to open the file
	 */
	public FileChannelInputStream(FileInputStream in) {
		channel = in.getChannel();
	}

	/**
	 * Read a byte
	 *
	 * @return The byte that was read
	 * @throws IOException If the byte failed to read
	 */
	@Override
	public int read() throws IOException {
		int ret = channel.read(buf);
		buf.flip();
		return ret == 0 ? -1 : buf.get(0);
	}

	/**
	 * Read an array of bytes
	 *
	 * @param b The array to read to
	 * @return The bytes that were read
	 * @throws IOException If the bytes failed to read
	 */
	@Override
	public int read(byte[] b) throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(b);
		return channel.read(buf);
	}

	/**
	 * Read an array of bytes
	 *
	 * @param b   The array to read to
	 * @param off The offset in the array to start writing to
	 * @param len The number of bytes to read
	 * @return The bytes that were read
	 * @throws IOException If the bytes failed to read
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(b, off, len);
		return channel.read(buf);
	}

	/**
	 * Skip some bytes
	 *
	 * @param n The maximum number of bytes to skip
	 * @return The number of bytes that were actually skipped
	 * @throws IOException If the skip fails
	 */
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

	/**
	 * get the number of bytes left
	 *
	 * @return The number of bytes left
	 * @throws IOException If the operation fails
	 */
	@Override
	public int available() throws IOException {
		return (int) (channel.size() - channel.position());
	}

	/**
	 * Close the InputStream
	 *
	 * @throws IOException If the operation fails
	 */
	@Override
	public void close() throws IOException {
		channel.close();
	}

	/**
	 * Set the mark
	 *
	 * @param readlimit Ignored
	 */
	@Override
	public void mark(int readlimit) {
		try {
			mark = channel.position();
		} catch (IOException e) {

		}
	}

	/**
	 * Reset the position to the mark
	 *
	 * @throws IOException If the operation fails
	 */
	@Override
	public synchronized void reset() throws IOException {
		channel.position(mark);
	}

	/**
	 * Returns <code>true</code>
	 *
	 * @return <code>true</code>
	 */
	@Override
	public boolean markSupported() {
		return true;
	}
}
