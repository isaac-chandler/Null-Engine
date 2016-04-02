package nullEngine.util.logs;

import java.io.PrintStream;

public class ErrorPrintStream extends PrintStream {

	public ErrorPrintStream() {
		super(System.err);
	}

	@Override
	public void println(boolean x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(char x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(int x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(long x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(float x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(double x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(char[] x) {
		println(String.valueOf(x));
	}

	@Override
	public void println(String x) {
		if (Logs.warnIfUseSystemOut) {
			String message = "Warning please use Logs.e at\n";
			StackTraceElement[] stackTrace = new Exception().getStackTrace();
			for (int i = 1; i < stackTrace.length; i++) {
				StackTraceElement e = stackTrace[i];
				message += "    at " + e.getClassName() + "." + e.getMethodName() + "(";
				if (e.isNativeMethod())
					message += "Native Method)\n";
				else
					message += e.getFileName() + ":" + e.getLineNumber() + ")\n";
			}
			Logs.d(message);
		}
		Logs.e2(x);
	}

	@Override
	public void println(Object x) {
		println(String.valueOf(x));
	}
}
