// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NullShaderSimpleStatement extends PsiElement {

  @Nullable
  NullShaderCaseLabel getCaseLabel();

  @Nullable
  NullShaderDeclarationStatement getDeclarationStatement();

  @Nullable
  NullShaderExpressionStatement getExpressionStatement();

  @Nullable
  NullShaderIterationStatement getIterationStatement();

  @Nullable
  NullShaderJumpStatement getJumpStatement();

  @Nullable
  NullShaderSelectionStatement getSelectionStatement();

  @Nullable
  NullShaderSwitchStatement getSwitchStatement();

}
