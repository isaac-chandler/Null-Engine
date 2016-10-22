package nullshader.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import nullshader.parser.NullShaderLexerAdapter;
import nullshader.parser.psi.NullShaderTypes;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static nullshader.parser.psi.NullShaderTypes.*;

public class NullShaderSyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributesKey BLOCK = TextAttributesKey.createTextAttributesKey("NULL_SHADER_BLOCK");
	public static final TextAttributesKey PREPROCESSOR = TextAttributesKey.createTextAttributesKey("NULL_SHADER_PREPROCESSOR", DefaultLanguageHighlighterColors.METADATA);
	public static final TextAttributesKey VERSION = TextAttributesKey.createTextAttributesKey("NULL_SHADER_VERSION", DefaultLanguageHighlighterColors.NUMBER);
	public static final TextAttributesKey TYPE = TextAttributesKey.createTextAttributesKey("NULL_SHADER_TYPE");
	public static final TextAttributesKey STORAGE_QUALIFIER = TextAttributesKey.createTextAttributesKey("NULL_SHADER_STORAGE_QUALIFIER");
	public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("NULL_SHADER_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
	public static final TextAttributesKey BOOLEAN = TextAttributesKey.createTextAttributesKey("NULL_SHADER_BOOLEAN", DefaultLanguageHighlighterColors.KEYWORD);
	public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("NULL_SHADER_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
	public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("NULL_SHADER_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
	public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey("NULL_SHADER_BAD_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

	private static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[] {BAD_CHARACTER};
	private static final TextAttributesKey[] BLOCK_KEYS = new TextAttributesKey[] {BLOCK};
	private static final TextAttributesKey[] TYPE_KEYS = new TextAttributesKey[] {TYPE};
	private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[] {KEYWORD};
	private static final TextAttributesKey[] STORAGE_QUALIFIER_KEYS = new TextAttributesKey[] {STORAGE_QUALIFIER};
	private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[] {NUMBER};
	private static final TextAttributesKey[] BOOLEAN_KEYS = new TextAttributesKey[] {BOOLEAN};
	private static final TextAttributesKey[] PREPROCESSOR_KEYS = new TextAttributesKey[] {PREPROCESSOR};
	private static final TextAttributesKey[] VERSION_KEYS = new TextAttributesKey[] {VERSION};
	private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] {COMMENT};
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
		if (tokenType.equals(TokenType.BAD_CHARACTER) || tokenType.equals(RESERVED))
			return BAD_CHARACTER_KEYS;
		else if (tokenType.equals(BLOCK_START) || tokenType.equals(BLOCK_END) || tokenType.equals(BLOCK_NAME))
			return BLOCK_KEYS;
		else if (SyntaxTokens.KEYWORDS.contains(tokenType))
			return KEYWORD_KEYS;
		else if (SyntaxTokens.TYPES.contains(tokenType))
			return TYPE_KEYS;
		else if (SyntaxTokens.NUMBERS.contains(tokenType))
			return NUMBER_KEYS;
		else if (SyntaxTokens.STORAGE_QUALIFERS.contains(tokenType))
			return STORAGE_QUALIFIER_KEYS;
		else if (tokenType.equals(BOOL))
			return BOOLEAN_KEYS;
		else if (SyntaxTokens.VERSIONS.contains(tokenType))
			return VERSION_KEYS;
		else if (SyntaxTokens.PREPROCESSORS.contains(tokenType))
			return PREPROCESSOR_KEYS;
		else if (tokenType.equals(NullShaderTypes.COMMENT))
			return COMMENT_KEYS;
		return EMPY_KEYS;
	}

	public static class SyntaxTokens {
		public static final Set<IElementType> NUMBERS = new HashSet<IElementType>();
		public static final Set<IElementType> KEYWORDS = new HashSet<IElementType>();
		public static final Set<IElementType> TYPES = new HashSet<IElementType>();
		public static final Set<IElementType> STORAGE_QUALIFERS = new HashSet<IElementType>();
		public static final Set<IElementType> VERSIONS = new HashSet<IElementType>();
		public static final Set<IElementType> PREPROCESSORS = new HashSet<IElementType>();

		static {
			TYPES.add(TYPE_VOID);
			TYPES.add(TYPE_FLOAT);
			TYPES.add(TYPE_DOUBLE);
			TYPES.add(TYPE_INT);
			TYPES.add(TYPE_UINT);
			TYPES.add(TYPE_BOOL);
			TYPES.add(TYPE_VEC2);
			TYPES.add(TYPE_VEC3);
			TYPES.add(TYPE_VEC4);
			TYPES.add(TYPE_DVEC2);
			TYPES.add(TYPE_DVEC3);
			TYPES.add(TYPE_DVEC4);
			TYPES.add(TYPE_IVEC2);
			TYPES.add(TYPE_IVEC3);
			TYPES.add(TYPE_IVEC4);
			TYPES.add(TYPE_BVEC2);
			TYPES.add(TYPE_BVEC3);
			TYPES.add(TYPE_BVEC4);
			TYPES.add(TYPE_MAT2);
			TYPES.add(TYPE_MAT3);
			TYPES.add(TYPE_MAT4);
			TYPES.add(TYPE_MAT2X2);
			TYPES.add(TYPE_MAT2X3);
			TYPES.add(TYPE_MAT2X4);
			TYPES.add(TYPE_MAT3X2);
			TYPES.add(TYPE_MAT3X3);
			TYPES.add(TYPE_MAT3X4);
			TYPES.add(TYPE_MAT4X2);
			TYPES.add(TYPE_MAT4X3);
			TYPES.add(TYPE_MAT4X4);
			TYPES.add(TYPE_DMAT2);
			TYPES.add(TYPE_DMAT3);
			TYPES.add(TYPE_DMAT4);
			TYPES.add(TYPE_DMAT2X2);
			TYPES.add(TYPE_DMAT2X3);
			TYPES.add(TYPE_DMAT2X4);
			TYPES.add(TYPE_DMAT3X2);
			TYPES.add(TYPE_DMAT3X3);
			TYPES.add(TYPE_DMAT3X4);
			TYPES.add(TYPE_DMAT4X2);
			TYPES.add(TYPE_DMAT4X3);
			TYPES.add(TYPE_DMAT4X4);
			TYPES.add(TYPE_ATOMIC_UINT);
			TYPES.add(TYPE_STRUCT);
			TYPES.add(TYPE_SAMPLER1D);
			TYPES.add(TYPE_SAMPLER2D);
			TYPES.add(TYPE_SAMPLER3D);
			TYPES.add(TYPE_SAMPLER_CUBE);
			TYPES.add(TYPE_SAMPLER2D_RECT);
			TYPES.add(TYPE_SAMPLER1D_ARRAY);
			TYPES.add(TYPE_SAMPLER2D_ARRAY);
			TYPES.add(TYPE_SAMPLER_BUFFER);
			TYPES.add(TYPE_SAMPLER2D_MS);
			TYPES.add(TYPE_SAMPLER2D_MS_ARRAY);
			TYPES.add(TYPE_SAMPLER1D_SHADOW);
			TYPES.add(TYPE_SAMPLER2D_SHADOW);
			TYPES.add(TYPE_SAMPLER1D_ARRAY_SHADOW);
			TYPES.add(TYPE_SAMPLER2D_ARRAY_SHADOW);
			TYPES.add(TYPE_SAMPLER_CUBE_SHADOW);
			TYPES.add(TYPE_SAMPLER_CUBE_ARRAY_SHADOW);
			TYPES.add(TYPE_ISAMPLER1D);
			TYPES.add(TYPE_ISAMPLER2D);
			TYPES.add(TYPE_ISAMPLER3D);
			TYPES.add(TYPE_ISAMPLER_CUBE);
			TYPES.add(TYPE_ISAMPLER2D_RECT);
			TYPES.add(TYPE_ISAMPLER1D_ARRAY);
			TYPES.add(TYPE_ISAMPLER2D_ARRAY);
			TYPES.add(TYPE_ISAMPLER_BUFFER);
			TYPES.add(TYPE_ISAMPLER2D_MS);
			TYPES.add(TYPE_ISAMPLER2D_MS_ARRAY);
			TYPES.add(TYPE_USAMPLER1D);
			TYPES.add(TYPE_USAMPLER2D);
			TYPES.add(TYPE_USAMPLER3D);
			TYPES.add(TYPE_USAMPLER_CUBE);
			TYPES.add(TYPE_USAMPLER2D_RECT);
			TYPES.add(TYPE_USAMPLER1D_ARRAY);
			TYPES.add(TYPE_USAMPLER2D_ARRAY);
			TYPES.add(TYPE_USAMPLER_BUFFER);
			TYPES.add(TYPE_USAMPLER2D_MS);
			TYPES.add(TYPE_USAMPLER2D_MS_ARRAY);
			TYPES.add(TYPE_IMAGE1D);
			TYPES.add(TYPE_IMAGE2D);
			TYPES.add(TYPE_IMAGE3D);
			TYPES.add(TYPE_IMAGE_CUBE);
			TYPES.add(TYPE_IMAGE2D_RECT);
			TYPES.add(TYPE_IMAGE1D_ARRAY);
			TYPES.add(TYPE_IMAGE2D_ARRAY);
			TYPES.add(TYPE_IMAGE_BUFFER);
			TYPES.add(TYPE_IMAGE2D_MS);
			TYPES.add(TYPE_IMAGE2D_MS_ARRAY);
			TYPES.add(TYPE_IIMAGE1D);
			TYPES.add(TYPE_IIMAGE2D);
			TYPES.add(TYPE_IIMAGE3D);
			TYPES.add(TYPE_IIMAGE_CUBE);
			TYPES.add(TYPE_IIMAGE2D_RECT);
			TYPES.add(TYPE_IIMAGE1D_ARRAY);
			TYPES.add(TYPE_IIMAGE2D_ARRAY);
			TYPES.add(TYPE_IIMAGE_BUFFER);
			TYPES.add(TYPE_IIMAGE2D_MS);
			TYPES.add(TYPE_IIMAGE2D_MS_ARRAY);
			TYPES.add(TYPE_UIMAGE1D);
			TYPES.add(TYPE_UIMAGE2D);
			TYPES.add(TYPE_UIMAGE3D);
			TYPES.add(TYPE_UIMAGE_CUBE);
			TYPES.add(TYPE_UIMAGE2D_RECT);
			TYPES.add(TYPE_UIMAGE1D_ARRAY);
			TYPES.add(TYPE_UIMAGE2D_ARRAY);
			TYPES.add(TYPE_UIMAGE_BUFFER);
			TYPES.add(TYPE_UIMAGE2D_MS);
			TYPES.add(TYPE_UIMAGE2D_MS_ARRAY);
			TYPES.add(KEYWORD_SUBROUTINE);

			STORAGE_QUALIFERS.add(KEYWORD_CONST);
			STORAGE_QUALIFERS.add(KEYWORD_ATTRIBUTE);
			STORAGE_QUALIFERS.add(KEYWORD_UNIFORM);
			STORAGE_QUALIFERS.add(KEYWORD_VARYING);
			STORAGE_QUALIFERS.add(KEYWORD_CENTROID);
			STORAGE_QUALIFERS.add(KEYWORD_INVARIANT);
			STORAGE_QUALIFERS.add(KEYWORD_PATCH);
			STORAGE_QUALIFERS.add(KEYWORD_SAMPLE);
			STORAGE_QUALIFERS.add(KEYWORD_BUFFER);
			STORAGE_QUALIFERS.add(KEYWORD_SHARED);
			STORAGE_QUALIFERS.add(KEYWORD_COHERENT);
			STORAGE_QUALIFERS.add(KEYWORD_VOLATILE);
			STORAGE_QUALIFERS.add(KEYWORD_RESTRICT);
			STORAGE_QUALIFERS.add(KEYWORD_READONLY);
			STORAGE_QUALIFERS.add(KEYWORD_WRITEONLY);
			STORAGE_QUALIFERS.add(KEYWORD_PRECISE);
			STORAGE_QUALIFERS.add(KEYWORD_LAYOUT);
			STORAGE_QUALIFERS.add(KEYWORD_SMOOTH);
			STORAGE_QUALIFERS.add(KEYWORD_FLAT);
			STORAGE_QUALIFERS.add(KEYWORD_NOPERSPECTIVE);
			STORAGE_QUALIFERS.add(KEYWORD_HIGHP);
			STORAGE_QUALIFERS.add(KEYWORD_MEDIUMP);
			STORAGE_QUALIFERS.add(KEYWORD_LOWP);
			STORAGE_QUALIFERS.add(KEYWORD_IN);
			STORAGE_QUALIFERS.add(KEYWORD_OUT);
			STORAGE_QUALIFERS.add(KEYWORD_INOUT);

			KEYWORDS.add(KEYWORD_WHILE);
			KEYWORDS.add(KEYWORD_DO);
			KEYWORDS.add(KEYWORD_FOR);
			KEYWORDS.add(KEYWORD_BREAK);
			KEYWORDS.add(KEYWORD_CONTINUE);
			KEYWORDS.add(KEYWORD_RETURN);
			KEYWORDS.add(KEYWORD_DISCARD);
			KEYWORDS.add(KEYWORD_IF);
			KEYWORDS.add(KEYWORD_ELSE);
			KEYWORDS.add(KEYWORD_SWITCH);
			KEYWORDS.add(KEYWORD_CASE);
			KEYWORDS.add(KEYWORD_DEFAULT);
			KEYWORDS.add(PREPROCESSOR_DEFINED);

			NUMBERS.add(INT);
			NUMBERS.add(UINT);
			NUMBERS.add(FLOAT);
			NUMBERS.add(DOUBLE);

			VERSIONS.add(GLSL_PROFILE);
			VERSIONS.add(GLSL_VERSION);

			PREPROCESSORS.add(PREPROCESSOR_INCLUDE);
			PREPROCESSORS.add(PREPROCESSOR_DEFINE);
			PREPROCESSORS.add(PREPROCESSOR_UNDEF);
			PREPROCESSORS.add(PREPROCESSOR_IF);
			PREPROCESSORS.add(PREPROCESSOR_IFDEF);
			PREPROCESSORS.add(PREPROCESSOR_IFNDEF);
			PREPROCESSORS.add(PREPROCESSOR_ELSE);
			PREPROCESSORS.add(PREPROCESSOR_ELIF);
			PREPROCESSORS.add(PREPROCESSOR_ENDIF);
			PREPROCESSORS.add(PREPROCESSOR_ERROR);
			PREPROCESSORS.add(PREPROCESSOR_PRAGMA);
			PREPROCESSORS.add(PREPROCESSOR_EXTENSION);
			PREPROCESSORS.add(PREPROCESSOR_VERSION);
			PREPROCESSORS.add(PREPROCESSOR_LINE);
			PREPROCESSORS.add(PREPROCESSOR_CONCAT);
			PREPROCESSORS.add(PREPROCESSOR_START);
		}
	}
}
