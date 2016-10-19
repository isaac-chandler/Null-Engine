package nullshader;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

public class NullShaderFileTypeFactory extends FileTypeFactory {

	@Override
	public void createFileTypes(FileTypeConsumer fileTypeConsumer) {
		fileTypeConsumer.consume(NullShaderFileType.INSTANCE, "");
	}
}
