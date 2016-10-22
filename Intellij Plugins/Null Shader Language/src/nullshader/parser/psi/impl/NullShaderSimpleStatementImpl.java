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

public class NullShaderSimpleStatementImpl extends ASTWrapperPsiElement implements NullShaderSimpleStatement {

  public NullShaderSimpleStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitSimpleStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NullShaderCaseLabel getCaseLabel() {
    return findChildByClass(NullShaderCaseLabel.class);
  }

  @Override
  @Nullable
  public NullShaderDeclarationStatement getDeclarationStatement() {
    return findChildByClass(NullShaderDeclarationStatement.class);
  }

  @Override
  @Nullable
  public NullShaderExpressionStatement getExpressionStatement() {
    return findChildByClass(NullShaderExpressionStatement.class);
  }

  @Override
  @Nullable
  public NullShaderIterationStatement getIterationStatement() {
    return findChildByClass(NullShaderIterationStatement.class);
  }

  @Override
  @Nullable
  public NullShaderJumpStatement getJumpStatement() {
    return findChildByClass(NullShaderJumpStatement.class);
  }

  @Override
  @Nullable
  public NullShaderSelectionStatement getSelectionStatement() {
    return findChildByClass(NullShaderSelectionStatement.class);
  }

  @Override
  @Nullable
  public NullShaderSwitchStatement getSwitchStatement() {
    return findChildByClass(NullShaderSwitchStatement.class);
  }

}
