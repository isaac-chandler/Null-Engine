// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NullShaderSingleDeclaration extends PsiElement {

  @Nullable
  NullShaderArraySpecifier getArraySpecifier();

  @NotNull
  NullShaderFullySpecifiedType getFullySpecifiedType();

  @Nullable
  NullShaderInitializer getInitializer();

}
