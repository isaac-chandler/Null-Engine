package nullEngine.util.logs;

import nullEngine.control.Application;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for logging to the console and log files
 *
 * @author Minecraft Warlock
 * @version 1.0
 */
public class Logs {

	private static Application application;

	private static PrintStream oldSystemOut;
	private static PrintStream oldSystemErr;
	private static File logFolder;
	private static String recent;
	private static String log;
	private static PrintStream out;
	private static String logFormat;
	private static long startTime;
	private static boolean initialized = false;
	private static boolean debug = false;
	static boolean warnIfUseSystemOut;

	private static SimpleDateFormat fileNameFormat;
	private static SimpleDateFormat logTimeFormat;

	private static Profiler builtinProfiler = new Profiler("BUILTIN");

	static {
		oldSystemOut = System.out;
		oldSystemErr = System.err;
		setLogTimeFormat("HH:mm:ss");
		setLogFileFormat("yyyy-MM-dd-HH-mm-ss");
		setLogFolder("logs");
		setRecentFile("logs-recent.txt");
		setLogFormat("[%t][%l][%s] %m\r\n");
		setWarnIfUseSystemOut(false);
	}

	public static void setApplication(Application application) {
		Logs.application = application;
	}

	/**
	 * Initialize the logging system
	 */
	public static void init() {
		//get starting time
		startTime = System.currentTimeMillis();

		//create log folder
		if (!logFolder.isDirectory()) {
			if (logFolder.isFile())
				logFolder.delete();
			logFolder.mkdir();
		}

		//set log file
		log = logFolder.getAbsolutePath() + "/log-" + fileNameFormat.format(startTime) + ".txt";
		File logFile = new File(log);
		try {
			if (!logFile.exists())
				logFile.createNewFile();
			//set up PrintStream
			out = new PrintStream(log);
			initialized = true;
		} catch (IOException e) {
			System.err.println("An error occured while initializing the logs");
			e.printStackTrace(oldSystemErr);
		}

		System.setOut(new OutPrintStream());
		System.setErr(new ErrorPrintStream());
		//print debug message
		Logs.d("Successfully initalized logs");
	}

	private static String formatMessage(String level, Object message) {
		StackTraceElement[] stack = new Exception().getStackTrace();
		String stackString = stack[2].toString();
		return logFormat.replace("%l", level).
				replace("%t", logTimeFormat.format(new Date(System.currentTimeMillis() - startTime))).
				replace("%m", String.valueOf(message)).
				replace("%s", stackString);
	}

	/**
	 * Print a message to the standard output and the log file
	 *
	 * @param message The message that will be printed (the toString method will be called)
	 */
	public static void print(Object message) {
		oldSystemOut.print(message);
		if (initialized) out.print(message);
	}

	/**
	 * Print a message to the standard error and the log file
	 *
	 * @param message The message that will be printed (the toString method will be called)
	 */
	public static void err(Object message) {
		oldSystemErr.print(message);
		if (initialized)
			out.print(message);
	}

	/**
	 * Print a message to the DEBUG layer if debug mode is active
	 *
	 * @param message The message that will be printed
	 */
	public static void debug(Object message) {
		dPrint(formatMessage("DEBUG", message));
	}

	private static void dPrint(String message) {
		if (debug)
			oldSystemOut.print(message);
		if (initialized)
			out.print(message);
	}

	/**
	 * Print a message to the DEBUG layer if debug mode is active
	 *
	 * @param message The message that will be printed
	 */
	public static void d(Object message) {
		dPrint(formatMessage("DEBUG", message));
	}

	/**
	 * Print a message to the INFO layer
	 *
	 * @param message The message that will be printed
	 */
	public static void info(Object message) {
		print(formatMessage("INFO", message));
	}

	/**
	 * Print a message to the INFO layer
	 *
	 * @param message The message that will be printed
	 */
	public static void i(Object message) {
		print(formatMessage("INFO", message));
	}

	static void i2(Object message) {
		print(formatMessage("INFO", message));
	}

	/**
	 * Print a message to the WARNING layer
	 *
	 * @param message The message that will be printed
	 */
	public static void warn(Object message) {
		print(formatMessage("WARNING", message));
	}

	/**
	 * Print a message to the WARNING layer
	 *
	 * @param message The message that will be printed
	 */
	public static void w(Object message) {
		print(formatMessage("WARNING", message));
	}

	/**
	 * Print a message and an exception to the EXCEPTION layer
	 *
	 * @param message The message that will be printed
	 * @param e       The exception that will be printed
	 */
	public static void exception(Object message, Exception e) {
		err(formatMessage("EXCEPTION", message));
		exception(e);
	}

	/**
	 * Print a message and an exception to the EXCEPTION layer
	 *
	 * @param message The message that will be printed
	 * @param e       The exception that will be printed
	 */
	public static void e(Object message, Exception e) {
		err(formatMessage("EXCEPTION", message));
		exception(e);
	}

	/**
	 * Print an exception to the EXCEPTION layer
	 *
	 * @param e The exception that will be printed
	 */
	public static void exception(Exception e) {
		e.printStackTrace(oldSystemErr);
		if (initialized) e.printStackTrace(out);
	}

	/**
	 * Print an exception to the EXCEPTION layer
	 *
	 * @param e The exception that will be printed
	 */
	public static void e(Exception e) {
		e.printStackTrace(oldSystemErr);
		if (initialized) e.printStackTrace(out);
	}

	/**
	 * Print an message to the ERROR layer
	 *
	 * @param s The message that will be printed
	 */
	public static void error(Object s) {
		err(formatMessage("ERROR", s));
	}

	/**
	 * Print an message to the ERROR layer
	 *
	 * @param s The message that will be printed
	 */
	public static void e(Object s) {
		err(formatMessage("ERROR", s));
	}

	static void e2(Object s) {
		err(formatMessage("ERROR", s));
	}

	/**
	 * Print a message and an exception to the FATAL layer and exit with exit code 1
	 *
	 * @param message The message that will be printed
	 * @param e       The exception that will be printed
	 */
	public static void fatal(Object message, Exception e) {
		err(formatMessage("FATAL", message));
		fatal(e);
	}

	/**
	 * Print a message and an exception to the FATAL layer and exit with exit code 1
	 *
	 * @param message The message that will be printed
	 * @param e       The exception that will be printed
	 */
	public static void f(Object message, Exception e) {
		err(formatMessage("FATAL", message));
		fatal(e);
	}

	/**
	 * Print an exception to the FATAL layer and exit with exit code 1
	 *
	 * @param e The exception that will be printed
	 */
	public static void fatal(Throwable e) {
		e.printStackTrace(oldSystemErr);
		if (initialized) e.printStackTrace(out);
		if (application != null)
			application.stop(e);
	}

	/**
	 * Print an exception to the FATAL layer and exit with exit code 1
	 *
	 * @param e The exception that will be printed
	 */
	public static void f(Throwable e) {
		e.printStackTrace(oldSystemErr);
		if (initialized) e.printStackTrace(out);
		if (application != null)
			application.stop(e);
	}

	/**
	 * Set the format of all logged messages
	 *
	 * @param logFormat The format to use
	 *                  <br>%c will be replaced with the calling class
	 *                  <br>%t will be replaced with the time since init was called
	 *                  <br>%l will be replace with the log level
	 *                  <br>%m will be replace with the message
	 *                  <br>the default is [%t][%c][%l] %m\n
	 */
	public static void setLogFormat(String logFormat) {
		Logs.logFormat = logFormat;
	}

	/**
	 * Set wether the debug layer is active, default inactive
	 *
	 * @param debug true for active false for inactive
	 */
	public static void setDebug(boolean debug) {
		Logs.debug = debug;
	}

	/**
	 * Flush the data to the log file close the streams and copy to the recent file
	 */
	public static void finish() {
		if (initialized) {
			File recent = new File(logFolder.getAbsolutePath() + "/" + Logs.recent);
			Logs.d("Finishing log");
			initialized = false;
			out.flush();
			out.close();
			if (recent.exists())
				recent.delete();
			try {
				recent.createNewFile();
				FileChannel source = new FileInputStream(log).getChannel();
				FileChannel destination = new FileOutputStream(recent).getChannel();
				destination.transferFrom(source, 0, source.size());
				source.close();
				destination.close();
			} catch (IOException e) {
				e.printStackTrace(oldSystemErr);
			}

		}
	}

	public static void profileStart() {
		builtinProfiler.start();
	}

	public static void profilePrint() {
		builtinProfiler.print();
	}

	public static void setBuiltinProfiler(Profiler profiler) {
		builtinProfiler = profiler;
	}

	public static void setBuiltinProfilerName(String name) {
		builtinProfiler.setName(name);
	}

	public static String getBuiltinProfilerName() {
		return builtinProfiler.getName();
	}

	public static long getBuiltinProfilerStart() {
		return builtinProfiler.getStart();
	}

	/**
	 * Set the folder to save the logs
	 *
	 * @param logFolder The folder to save, default logs
	 */
	public static void setLogFolder(String logFolder) {
		Logs.logFolder = new File(logFolder);
	}

	/**
	 * Set the name of the recent log file
	 *
	 * @param recentFile The name of the recent file, default logs-recent.txt
	 */
	public static void setRecentFile(String recentFile) {
		recent = recentFile;
	}

	/**
	 * Set the date and time format of the log file name
	 *
	 * @param format the date and time format using Java's SimpleDateFormat format, default yyyy-MM-dd-HH-mm-ss
	 */
	public static void setLogFileFormat(String format) {
		fileNameFormat = new SimpleDateFormat(format);
	}

	/**
	 * Set the time format of the logs
	 *
	 * @param format the time format using Java's SimpleDateFormat format, default HH:mm:ss
	 */
	public static void setLogTimeFormat(String format) {
		logTimeFormat = new SimpleDateFormat(format);
		logTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	}

	public static void setWarnIfUseSystemOut(boolean warnIfUseSystemOut) {
		Logs.warnIfUseSystemOut = warnIfUseSystemOut;
	}

	/**
	 * Dummy method to call static initializer
	 */
	public static void dummy() {
	}

	public static void getStackTrace() {
		new Exception().printStackTrace();
	}

	public static PrintStream getSystemOut() {
		return oldSystemOut;
	}

	public static class Profiler {
		private long start;
		private String name;

		public Profiler(String name) {
			this.name = name;
		}

		public void start() {
			start = System.nanoTime();
		}

		public void print() {
			if (Logs.debug)
				Logs.print(formatMessage("PROFILER", name + " " + ((System.nanoTime() - start) / 1e9)));
		}

		public long getStart() {
			return start;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
