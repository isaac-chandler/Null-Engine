package nullshader.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.impl.PsiBuilderAdapter;
import static nullshader.psi.NullShaderTypes.*;

public class NullShaderParsing {

	private final class NullShaderPsiBuilder extends PsiBuilderAdapter {
		public NullShaderPsiBuilder(PsiBuilder delegate) {
			super(delegate);
		}

		@Override
		public void advanceLexer() {
			advanceLexer(true);
		}

		private void advanceLexer(boolean preprocessing) {
			super.advanceLexer();

			if (preprocessing)
				while (getTokenType() == PREPROCESSOR_START)
					parsePreprocessor();
		}
	}

	public NullShaderParsing(PsiBuilder builder) {

	}

	public void parseBlocks() {

	}

	private void parsePreprocessor() {

	}
}
