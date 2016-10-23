package sandbox;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	private static String getFuncInfo(String name, String url) throws Exception {
		System.out.println("Getting function info for " + name);
		return readURL(url);
	}

	private static String readURL(String urlToRead) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Referer", "google.com");
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(true);

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}

	private static void init() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}};

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	public static void main(String[] args) {
		try {
			init();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return;
		}

		Scanner scanner = new Scanner(Test.class.getClassLoader().getResourceAsStream("sandbox/indexflat.html"));

		final Pattern urlP = Pattern.compile(".*<a href=\"(https://www\\.opengl\\.org/sdk/docs/man4/html/.+?\\.xhtml)\" target=\"pagedisplay\">(.+?)</a>.*");
		final Pattern defP = Pattern.compile(".*<code class=\"funcdef\">(.+?) <strong class=\"fsfunc\">.+?</strong>\\(</code>.*");
		final Pattern paramP = Pattern.compile(".*<td>([^\\[]+?) <var class=\"pdparam\">.+?</var>.*");
		final Pattern paramOptionalP = Pattern.compile(".*<td>\\[(.+?) <var class=\"pdparam\">.+?</var>].*");
		StringBuilder out = new StringBuilder();

		while (scanner.hasNextLine()) {
			String src = scanner.nextLine();
			Matcher urlM = urlP.matcher(src);
			if (urlM.matches()) {
				String url = urlM.group(1);
				String name = urlM.group(2);
				if (!name.startsWith("gl")) {
					try {
						String infoS = getFuncInfo(name, url);
						infoS = infoS.replaceAll("  +", "\n");
						Scanner info = new Scanner(infoS);
						String currentDef = null;
						String optional = null;
						String ret = null;

						int count = 0;
						while (info.hasNextLine()) {
							String line = info.nextLine();
							Matcher defM = defP.matcher(line);
							Matcher paramM = paramP.matcher(line);
							Matcher paramOptionalM = paramOptionalP.matcher(line);
							if (defM.matches()) {
								count++;
								if (currentDef != null) {
									out.append(currentDef).append('=').append(ret).append("|\n");
									if (optional != null) {
										out.append(currentDef).append('|').append(optional).append('=').append(ret).append("|\n");
									}
								}
								ret = defM.group(1);
								currentDef = name;
								optional = null;
							} else if (paramM.matches()) {
								String param = paramM.group(1);
								if (param.lastIndexOf(' ') != -1) {
									param = param.substring(param.lastIndexOf(' ') + 1);
								}
								currentDef += "|" + param;
							} else if (paramOptionalM.matches()) {
								optional = paramOptionalM.group(1);
								if (optional.lastIndexOf(' ') != -1) {
									optional = optional.substring(optional.lastIndexOf(' ') + 1);
								}
							}

						}
						if (currentDef != null) {
							out.append(currentDef).append('=').append(ret).append("|\n");
							if (optional != null) {
								out.append(currentDef).append('|').append(optional).append('=').append(ret).append("|\n");
							}
						}
						System.out.println("Found " + count + " overloads for " + name);
					} catch (Exception e) {
						System.err.println("Failed to get function info for " + name);
						e.printStackTrace();
					}
				}
			}
		}

		try {
			FileOutputStream f = new FileOutputStream("./builtinFuncs.properties");
			f.write(out.toString().getBytes("UTF8"));
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
