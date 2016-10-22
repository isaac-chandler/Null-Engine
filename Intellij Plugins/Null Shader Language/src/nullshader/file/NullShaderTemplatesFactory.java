package nullshader.file;

import com.intellij.ide.fileTemplates.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import nullshader.Icons;
import nullshader.file.NullShaderFileType;

public class NullShaderTemplatesFactory implements FileTemplateGroupDescriptorFactory {

	public static final String NULL_SHADER_TEMPLATE = "NullShader.ns";

	@Override
	public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
		FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("Null Shader", Icons.NULL_SHADER_ICON);
		group.addTemplate(new FileTemplateDescriptor(NULL_SHADER_TEMPLATE, Icons.NULL_SHADER_ICON));
		return group;
	}

	public static PsiFile createFromTemplate(PsiDirectory directory, String fileName, String templateName) throws IncorrectOperationException {
		final FileTemplate template = FileTemplateManager.getInstance(directory.getProject()).getInternalTemplate(templateName);

		Project project = directory.getProject();

		String text;
		try {
			text = template.getText();
		} catch (Exception e) {
			throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance(project).internalTemplateToSubject(templateName), e);
		}

		final PsiFileFactory factory = PsiFileFactory.getInstance(project);
		PsiFile file = factory.createFileFromText(fileName, NullShaderFileType.INSTANCE, text);

		file = (PsiFile) directory.add(file);

		return file;
	}
}
