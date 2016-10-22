package nullshader.highlighter;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import nullshader.highlighter.NullShaderSyntaxHighlighter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NullShaderSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
	/**
	 * Override this method to provide syntax highlighting (coloring) capabilities for your language implementation.
	 * By syntax highlighting we mean highlighting of keywords, comments, braces etc. where lexing the file content is enough
	 * to identify proper highlighting attributes.
	 * <p/>
	 * Default implementation doesn't highlight anything.
	 *
	 * @param project     might be necessary to gather various project settings from.
	 * @param virtualFile might be necessary to collect file specific settings
	 * @return <code>SyntaxHighlighter</code> interface implementation for this particular language.
	 */
	@NotNull
	@Override
	public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
		return new NullShaderSyntaxHighlighter();
	}
}
