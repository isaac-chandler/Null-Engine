package nullshader;

import com.intellij.lexer.FlexAdapter;

public class NullShaderLexerAdapter extends FlexAdapter {
	public NullShaderLexerAdapter() {
		super(new NullShaderLexer(null));
	}
}
