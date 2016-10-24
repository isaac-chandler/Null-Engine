package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
	public static int readInt(InputStream in) throws IOException {
		int n = (in.read() << 24);
		n |= (in.read() << 16);
		n |= (in.read() << 8);
		n |= in.read();
		return n;
	}

	public static void writeInt(OutputStream out, int n) throws IOException {
		out.write((n >> 24) & 0xFF);
		out.write((n >> 16) & 0xFF);
		out.write((n >> 8) & 0xFF);
		out.write(n & 0xFF);
	}

	public static String readString(InputStream in) throws IOException {
		short len = (short) (in.read() << 8);
		len |= (short) in.read();

		byte[] bytes = new byte[len];
		in.read(bytes);

		return new String(bytes, "UTF8");
	}

	public static void writeString(OutputStream out, String s) throws IOException {
		byte[] buf = s.getBytes("UTF8");

		short len = (short) buf.length;

		out.write((len >> 8) & 0xFF);
		out.write(len & 0xFF);
		out.write(buf);
	}
}
