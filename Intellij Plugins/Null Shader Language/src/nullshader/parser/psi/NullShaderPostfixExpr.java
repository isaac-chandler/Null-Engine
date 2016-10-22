// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface NullShaderPostfixExpr extends NullShaderExpr {

  @NotNull
  List<NullShaderExpr> getExprList();

  @Nullable
  NullShaderFieldSelection getFieldSelection();

  @Nullable
  NullShaderFunctionCall getFunctionCall();

}
