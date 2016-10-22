// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NullShaderIterationStatement extends PsiElement {

  @Nullable
  NullShaderCondition getCondition();

  @Nullable
  NullShaderExpression getExpression();

  @Nullable
  NullShaderForInitStatement getForInitStatement();

  @Nullable
  NullShaderForRestStatement getForRestStatement();

  @Nullable
  NullShaderStatement getStatement();

  @Nullable
  NullShaderStatementNoNewScope getStatementNoNewScope();

}
