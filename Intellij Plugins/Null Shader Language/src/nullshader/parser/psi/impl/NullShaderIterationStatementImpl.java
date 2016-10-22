// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static nullshader.parser.psi.NullShaderTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import nullshader.parser.psi.*;

public class NullShaderIterationStatementImpl extends ASTWrapperPsiElement implements NullShaderIterationStatement {

  public NullShaderIterationStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitIterationStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NullShaderCondition getCondition() {
    return findChildByClass(NullShaderCondition.class);
  }

  @Override
  @Nullable
  public NullShaderExpression getExpression() {
    return findChildByClass(NullShaderExpression.class);
  }

  @Override
  @Nullable
  public NullShaderForInitStatement getForInitStatement() {
    return findChildByClass(NullShaderForInitStatement.class);
  }

  @Override
  @Nullable
  public NullShaderForRestStatement getForRestStatement() {
    return findChildByClass(NullShaderForRestStatement.class);
  }

  @Override
  @Nullable
  public NullShaderStatement getStatement() {
    return findChildByClass(NullShaderStatement.class);
  }

  @Override
  @Nullable
  public NullShaderStatementNoNewScope getStatementNoNewScope() {
    return findChildByClass(NullShaderStatementNoNewScope.class);
  }

}
