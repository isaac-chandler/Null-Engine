package nullshader;

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import nullshader.intention.AddProfileIntention;
import nullshader.intention.ChangeProfileIntention;
import nullshader.intention.RemoveProfileIntention;
import nullshader.parser.psi.NullShaderTypes;
import nullshader.parser.psi.NullShaderVersionInfo;
import org.jetbrains.annotations.NotNull;

public class NullShaderAnnotator implements Annotator {
	/**
	 * Annotates the specified PSI element.
	 * It is guaranteed to be executed in non-reentrant fashion.
	 * I.e there will be no call of this method for this instance before previous call get completed.
	 * Multiple instances of the annotator might exist simultaneously, though.
	 *
	 * @param element to annotate.
	 * @param holder  the container which receives annotations created by the plugin.
	 */
	@Override
	public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
		if (element instanceof NullShaderVersionInfo) {
			ASTNode node = element.getNode();
			int version = VersionUtil.getVersion((NullShaderVersionInfo) element);
			String profile = VersionUtil.getProfile((NullShaderVersionInfo) element);
			if (VersionUtil.versionToLowError(version, profile)) {
				holder.createErrorAnnotation(node, "Profiles are not supported until version 150").registerFix(new RemoveProfileIntention(element));
			} else if (VersionUtil.esNoProfileError(version, profile)) {
				holder.createErrorAnnotation(node, "A profile must be specified in version " + version).registerFix(new AddProfileIntention(element, "es"));
			} else if (VersionUtil.esWrongProfileError(version, profile)) {
				holder.createErrorAnnotation(node, "The profile must be es in version " + version).registerFix(new ChangeProfileIntention(element, "es"));
			} else if (VersionUtil.esNotAllowedError(version, profile)) {
				Annotation ann = holder.createErrorAnnotation(node, "The profile cannot be es in version " + version);
				ann.registerFix(new ChangeProfileIntention(element, "core"));
				ann.registerFix(new ChangeProfileIntention(element, "compatibility"));
				ann.registerFix(new RemoveProfileIntention(element));
			} else if (VersionUtil.noProfileWarning(version, profile)) {
				Annotation ann = holder.createWarningAnnotation(node, "No profile specifed");
				ann.registerFix(new AddProfileIntention(element, "core"));
				ann.registerFix(new AddProfileIntention(element, "compatibility"));
			}
		} else if (element.getNode().getElementType().equals(NullShaderTypes.RESERVED)) {
			holder.createErrorAnnotation(element, element.getText() + " is a reserved name");
		}
	}
}
