package nullshader;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import nullshader.file.NullShaderTemplatesFactory;


public class CreateNullShaderAction extends CreateFileFromTemplateAction {

	public CreateNullShaderAction() {
		super("Null Shader", "Create a new null shader", Icons.NULL_SHADER_ICON);
	}

	@Override
	protected PsiFile createFileFromTemplate(String name, FileTemplate template, PsiDirectory dir) {
		System.out.println(template.getText());
		return super.createFileFromTemplate(name, template, dir);
	}

	@Override
	protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
		builder.setTitle("Null Shader").addKind("Null Shader", Icons.NULL_SHADER_ICON, NullShaderTemplatesFactory.NULL_SHADER_TEMPLATE);
	}

	@Override
	protected String getActionName(PsiDirectory directory, String newName, String templateName) {
		return "Null Shader";
	}

	@Override
	protected PsiFile createFile(String name, String templateName, PsiDirectory dir) {
		FileTemplateManager.getInstance(dir.getProject()).getTemplate(templateName);
		return NullShaderTemplatesFactory.createFromTemplate(dir, name + ".ns", NullShaderTemplatesFactory.NULL_SHADER_TEMPLATE);
	}
}
