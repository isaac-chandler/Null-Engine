package nullshader.parser.psi;

import com.intellij.psi.tree.IElementType;
import nullshader.NullShaderLanguage;

public class NullShaderTokenType extends IElementType {
	public NullShaderTokenType(String debugName) {
		super(debugName, NullShaderLanguage.INSTANCE);
	}

	@Override
	public String toString() {
		return "NullShaderTokenType." + super.toString();
	}
}
