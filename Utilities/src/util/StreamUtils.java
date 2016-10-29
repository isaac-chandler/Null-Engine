package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilites for reading and writing InputStreams
 * @see java.io.InputStream
 */
public class StreamUtils {
	/**
	 * Read an <code>int</code> in big endian from an InputStream
	 * @param in the stream to read from
	 * @return the int that was read
	 * @throws IOException
	 * @see java.io.InputStream
	 */
	public static int readInt(InputStream in) throws IOException {
		int n = (in.read() << 24);
		n |= (in.read() << 16);
		n |= (in.read() << 8);
		n |= in.read();
		return n;
	}

	/**
	 * Write an <code>int</code> in big endian form to an input stream
	 * @param out the stream to write to
	 * @param n the number to write
	 * @throws IOException
	 * @see java.io.OutputStream
	 */
	public static void writeInt(OutputStream out, int n) throws IOException {
		out.write((n >> 24) & 0xFF);
		out.write((n >> 16) & 0xFF);
		out.write((n >> 8) & 0xFF);
		out.write(n & 0xFF);
	}

	/**
	 * Read a String from an InputStream
	 * @param in the stream to read from
	 * @return the String that was read
	 * @throws IOException
	 * @see java.io.InputStream
	 */
	public static String readString(InputStream in) throws IOException {
		short len = (short) (in.read() << 8);
		len |= (short) in.read();

		byte[] bytes = new byte[len];
		in.read(bytes);

		return new String(bytes, "UTF8");
	}

	/**
	 * Write a String to an input stream
	 * @param out the stream to write to
	 * @param s the String to write
	 * @throws IOException
	 * @see java.io.OutputStream
	 */
	public static void writeString(OutputStream out, String s) throws IOException {
		byte[] buf = s.getBytes("UTF8");

		short len = (short) buf.length;

		out.write((len >> 8) & 0xFF);
		out.write(len & 0xFF);
		out.write(buf);
	}
}
