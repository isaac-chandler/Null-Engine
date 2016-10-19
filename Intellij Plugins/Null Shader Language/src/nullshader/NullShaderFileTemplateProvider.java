package nullshader;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

public class NullShaderFileTemplateProvider  implements DefaultLiveTemplatesProvider {
	@Override
	public String[] getDefaultLiveTemplateFiles() {
		return new String[0];
	}

	/**
	 * @return paths to resources, without .xml extension (e.g. /templates/foo)
	 */
	@Nullable
	@Override
	public String[] getHiddenLiveTemplateFiles() {
		return new String[0];
	}
}
