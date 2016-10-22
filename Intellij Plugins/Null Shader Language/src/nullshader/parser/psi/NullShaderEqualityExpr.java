// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NullShaderEqualityExpr extends NullShaderExpr {

  @NotNull
  NullShaderEqualityOp getEqualityOp();

  @NotNull
  List<NullShaderExpr> getExprList();

}
