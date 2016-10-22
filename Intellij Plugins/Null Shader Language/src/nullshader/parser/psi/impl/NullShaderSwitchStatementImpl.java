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

public class NullShaderSwitchStatementImpl extends ASTWrapperPsiElement implements NullShaderSwitchStatement {

  public NullShaderSwitchStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitSwitchStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public NullShaderExpression getExpression() {
    return findNotNullChildByClass(NullShaderExpression.class);
  }

  @Override
  @NotNull
  public NullShaderSwitchStatementList getSwitchStatementList() {
    return findNotNullChildByClass(NullShaderSwitchStatementList.class);
  }

}
