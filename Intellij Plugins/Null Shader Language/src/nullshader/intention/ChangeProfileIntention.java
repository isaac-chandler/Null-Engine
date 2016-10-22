package nullshader.intention;

import com.intellij.codeInsight.intention.BaseElementAtCaretIntentionAction;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import nullshader.parser.psi.NullShaderTypes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class ChangeProfileIntention extends BaseElementAtCaretIntentionAction {

	private PsiElement info;
	private String newProfile;


	public ChangeProfileIntention(PsiElement info, String newProfile) {
		this.info = info;
		this.newProfile = newProfile;
	}

	/**
	 * Checks whether this intention is available at a caret offset in file.
	 * If this method returns true, a light bulb for this intention is shown.
	 *
	 * @param project the project in which the availability is checked.
	 * @param editor  the editor in which the intention will be invoked.
	 * @param element the element under caret.
	 * @return true if the intention is available, false otherwise.
	 */
	@Override
	public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
		return true;
	}

	/**
	 * Invokes intention action for the element under cursor.
	 *
	 * @param project the project in which the file is opened.
	 * @param editor  the editor for the file.
	 * @param element the element under cursor.
	 * @throws IncorrectOperationException
	 */
	@Override
	public void invoke(@NotNull final Project project, final Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
		new WriteCommandAction(project, element.getContainingFile()){
			@Override
			protected void run(@NotNull Result result) throws Throwable {
				ASTNode profileNode = info.getNode().findChildByType(NullShaderTypes.GLSL_PROFILE);
				editor.getDocument().replaceString(profileNode.getTextRange().getStartOffset(), profileNode.getTextRange().getEndOffset(), newProfile);
			}
		}.execute();
	}

	/**
	 * Returns the name of the family of intentions. It is used to externalize
	 * "auto-show" state of intentions. When user clicks on a lightbulb in intention list,
	 * all intentions with the same family name get enabled/disabled.
	 * The name is also shown in settings tree.
	 *
	 * @return the intention family name.
	 */
	@Nls
	@NotNull
	@Override
	public String getFamilyName() {
		return IntentionFamilies.VERSION_INTENTION_FAMILY;
	}

	@NotNull
	@Override
	public String getText() {
		return "Change profile to " + newProfile;
	}


}
