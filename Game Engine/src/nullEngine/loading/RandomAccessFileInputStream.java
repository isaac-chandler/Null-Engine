package nullEngine.loading;

import java.io.*;

public class RandomAccessFileInputStream extends InputStream {

	private RandomAccessFile file;
	private long mark = 0;

	public RandomAccessFileInputStream(File file) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "r");
	}

	public RandomAccessFileInputStream(String file) throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "r");
	}

	@Override
	public int read(byte[] b) throws IOException {
		return file.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return file.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException {
		return file.skipBytes((int) n);
	}

	@Override
	public int available() throws IOException {
		return (int) (file.length() - file.getFilePointer());
	}

	@Override
	public void close() throws IOException {
		file.close();
	}

	@Override
	public synchronized void mark(int readlimit) {
		try {
			mark = file.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void reset() throws IOException {
		file.seek(mark);
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read() throws IOException {
		return file.read();
	}

	public RandomAccessFile getFile() {
		return file;
	}
}
