package nullshader;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import nullshader.psi.NullShaderTypes;
import org.jetbrains.annotations.NotNull;

public class NullShaderSyntaxHighlighter extends SyntaxHighlighterBase {

	public static final TextAttributesKey BLOCK = TextAttributesKey.createTextAttributesKey("NULL_SHADER_BLOCK", DefaultLanguageHighlighterColors.METADATA);
	public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("NULL_SHADER_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

	private static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[] {BAD_CHARACTER};
	private static final TextAttributesKey[] BLOCK_KEYS = new TextAttributesKey[] {BLOCK};
	private static final TextAttributesKey[] EMPY_KEYS = new TextAttributesKey[0];

	/**
	 * Returns the lexer used for highlighting the file. The lexer is invoked incrementally when the file is changed, so it must be
	 * capable of saving/restoring state and resuming lexing from the middle of the file.
	 *
	 * @return The lexer implementation.
	 */
	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new NullShaderLexerAdapter();
	}

	/**
	 * Returns the list of text attribute keys used for highlighting the specified token type. The attributes of all attribute keys
	 * returned for the token type are successively merged to obtain the color and attributes of the token.
	 *
	 * @param tokenType The token type for which the highlighting is requested.
	 * @return The array of text attribute keys.
	 */
	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		if (tokenType.equals(TokenType.BAD_CHARACTER))
			return BAD_CHARACTER_KEYS;
		else if (tokenType.equals(NullShaderTypes.BLOCK_START) || tokenType.equals(NullShaderTypes.BLOCK_END) || tokenType.equals(NullShaderTypes.BLOCK_NAME))
			return BLOCK_KEYS;
		return EMPY_KEYS;
	}
}
