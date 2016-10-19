package nullshader.psi;

import com.intellij.psi.tree.IElementType;
import nullshader.NullShaderLanguage;

public class NullShaderElementType extends IElementType {
	public NullShaderElementType(String debugName) {
		super(debugName, NullShaderLanguage.INSTANCE);
	}
}
