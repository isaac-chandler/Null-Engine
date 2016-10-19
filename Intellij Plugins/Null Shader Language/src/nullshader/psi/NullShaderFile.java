package nullshader.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import nullshader.NullShaderFileType;
import nullshader.NullShaderLanguage;
import org.jetbrains.annotations.NotNull;

public class NullShaderFile extends PsiFileBase {
	public NullShaderFile(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, NullShaderLanguage.INSTANCE);
	}

	/**
	 * Returns the file type for the file.
	 *
	 * @return the file type instance.
	 */
	@NotNull
	@Override
	public FileType getFileType() {
		return NullShaderFileType.INSTANCE;
	}

	@Override
	public String toString() {
		return "Null Shader File";
	}
}
