package nullshader.highlighter;

import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import nullshader.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;
import java.util.Scanner;

public class NullShaderColorSettingsPage implements ColorSettingsPage {

	private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
			new AttributesDescriptor("Block", NullShaderSyntaxHighlighter.BLOCK),
			new AttributesDescriptor("Error", NullShaderSyntaxHighlighter.BAD_CHARACTER),
			new AttributesDescriptor("Keyword", NullShaderSyntaxHighlighter.KEYWORD),
			new AttributesDescriptor("Type", NullShaderSyntaxHighlighter.TYPE),
			new AttributesDescriptor("Storage Qualifier", NullShaderSyntaxHighlighter.STORAGE_QUALIFIER),
			new AttributesDescriptor("Number", NullShaderSyntaxHighlighter.NUMBER),
			new AttributesDescriptor("Boolean", NullShaderSyntaxHighlighter.BOOLEAN),
			new AttributesDescriptor("Version", NullShaderSyntaxHighlighter.VERSION),
			new AttributesDescriptor("Preprocessor", NullShaderSyntaxHighlighter.PREPROCESSOR),
			new AttributesDescriptor("Comment", NullShaderSyntaxHighlighter.COMMENT),
	};
	/**
	 * Returns the icon for the page, shown in the dialog tab.
	 *
	 * @return the icon for the page, or null if the page does not have a custom icon.
	 */
	@Nullable
	@Override
	public Icon getIcon() {
		return Icons.NULL_SHADER_ICON;
	}

	/**
	 * Returns the syntax highlighter which is used to highlight the text shown in the preview
	 * pane of the page.
	 *
	 * @return the syntax highlighter instance.
	 */
	@NotNull
	@Override
	public SyntaxHighlighter getHighlighter() {
		return new NullShaderSyntaxHighlighter();
	}

	private String demoText;

	/**
	 * Returns the text shown in the preview pane. If some elements need to be highlighted in
	 * the preview text which are not highlighted by the syntax highlighter, they need to be
	 * surrounded by XML-like tags, for example: <code>&lt;class&gt;MyClass&lt;/class&gt;</code>.
	 * The mapping between the names of the tags and the text attribute keys used for highlighting
	 * is defined by the {@link #getAdditionalHighlightingTagToDescriptorMap()} method.
	 *
	 * @return the text to show in the preview pane.
	 */
	@NotNull
	@Override
	public String getDemoText() {
		if (demoText == null) {
			Scanner in = new Scanner(getClass().getClassLoader().getResourceAsStream("NullShaderDemoText"));
			StringBuilder text = new StringBuilder();
			while (in.hasNextLine())
				text.append(in.nextLine()).append('\n');
			demoText = text.toString();
		}
		return demoText;
	}

	/**
	 * Returns the mapping from special tag names surrounding the regions to be highlighted
	 * in the preview text (see {@link #getDemoText()}) to text attribute keys used to
	 * highlight the regions.
	 *
	 * @return the mapping from tag names to text attribute keys, or null if the demo text
	 * does not contain any additional highlighting tags.
	 */
	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	/**
	 * Returns the list of descriptors specifying the {@link TextAttributesKey} instances
	 * for which colors are specified in the page. For such attribute keys, the user can choose
	 * all highlighting attributes (font type, background color, foreground color, error stripe color and
	 * effects).
	 *
	 * @return the list of attribute descriptors.
	 */
	@NotNull
	@Override
	public AttributesDescriptor[] getAttributeDescriptors() {
		return DESCRIPTORS;
	}

	/**
	 * Returns the list of descriptors specifying the {@link ColorKey}
	 * instances for which colors are specified in the page. For such color keys, the user can
	 * choose only the background or foreground color.
	 *
	 * @return the list of color descriptors.
	 */
	@NotNull
	@Override
	public ColorDescriptor[] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	/**
	 * Returns the title of the page, shown as text in the dialog tab.
	 *
	 * @return the title of the custom page.
	 */
	@NotNull
	@Override
	public String getDisplayName() {
		return "Null Shader";
	}
}
