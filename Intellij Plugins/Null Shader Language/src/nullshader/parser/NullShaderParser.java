// This is a generated file. Not intended for manual editing.
package nullshader.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class NullShaderParser implements PsiParser, LightPsiParser {


	@Override
	public void parseLight(IElementType root, PsiBuilder builder) {
		PsiBuilder.Marker rootMarker = builder.mark();
		if (!builder.eof()) {
			final NullShaderParsing parser = new NullShaderParsing(builder);
			parser.parseBlocks();
			while (!builder.eof())
				builder.advanceLexer();
		}

		rootMarker.done(root);
	}

	/**
	 * Parses the contents of the specified PSI builder and returns an AST tree with the
	 * specified type of root element. The PSI builder contents is the entire file
	 * or (if chameleon tokens are used) the text of a chameleon token which needs to
	 * be reparsed.
	 *
	 * @param root    the type of the root element in the AST tree.
	 * @param builder the builder which is used to retrieve the original file tokens and build the AST tree.
	 * @return the root of the resulting AST tree.
	 */
	@NotNull
	@Override
	public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
		parseLight(root, builder);
		return builder.getTreeBuilt();
	}
}
