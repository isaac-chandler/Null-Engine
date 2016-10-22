package nullshader;

import com.intellij.lang.ASTNode;
import nullshader.parser.psi.NullShaderTypes;
import nullshader.parser.psi.NullShaderVersionInfo;

public class VersionUtil {
	public static int getVersion(NullShaderVersionInfo element) {
		ASTNode versionNode = element.getNode().findChildByType(NullShaderTypes.GLSL_VERSION);

		if (versionNode != null) {
			return Integer.parseInt(versionNode.getText());
		} else {
			return 100;
		}
	}

	public static String getProfile(NullShaderVersionInfo element) {
		ASTNode profileNode = element.getNode().findChildByType(NullShaderTypes.GLSL_PROFILE);
		if (profileNode != null) {
			return profileNode.getText();
		} else {
			return null;
		}
	}


	public static boolean profilesSupported(int version) {
		return version >= 150;
	}

	public static boolean esVersion(int version) {
		return version == 300 || version == 310;
	}

	public static boolean versionToLowError(int version, String profile) {
		return !profilesSupported(version) && profile != null;
	}

	public static boolean esNoProfileError(int version, String profile) {
		return esVersion(version) && profile == null;
	}

	public static boolean esWrongProfileError(int version, String profile) {
		return esVersion(version) && (profile != null && !profile.equals("es"));
	}

	public static boolean esNotAllowedError(int version, String profile) {
		return profile != null && profile.equals("es") && profilesSupported(version) && !esVersion(version);
	}

	public static boolean noProfileWarning(int version, String profile) {
		return profile == null && profilesSupported(version) && !esVersion(version);
	}
}
