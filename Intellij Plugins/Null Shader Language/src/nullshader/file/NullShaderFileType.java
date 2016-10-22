package nullshader.file;

import com.intellij.openapi.fileTypes.LanguageFileType;
import nullshader.Icons;
import nullshader.NullShaderLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NullShaderFileType extends LanguageFileType {

	public static final NullShaderFileType INSTANCE = new NullShaderFileType();

	protected NullShaderFileType() {
		super(NullShaderLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "Null Shader";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Null Engine shader";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "ns";
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return Icons.NULL_SHADER_ICON;
	}
}
