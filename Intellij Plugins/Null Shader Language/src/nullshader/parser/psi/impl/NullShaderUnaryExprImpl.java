// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static nullshader.parser.psi.NullShaderTypes.*;
import nullshader.parser.psi.*;

public class NullShaderUnaryExprImpl extends NullShaderExprImpl implements NullShaderUnaryExpr {

  public NullShaderUnaryExprImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitUnaryExpr(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NullShaderExpr getExpr() {
    return findChildByClass(NullShaderExpr.class);
  }

  @Override
  @NotNull
  public NullShaderUnaryOperator getUnaryOperator() {
    return findNotNullChildByClass(NullShaderUnaryOperator.class);
  }

}
