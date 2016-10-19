package nullshader;

import com.intellij.lang.Language;

public class NullShaderLanguage extends Language {

	public static final NullShaderLanguage INSTANCE = new NullShaderLanguage();

	protected NullShaderLanguage() {
		super("Null Shader");
	}
}
