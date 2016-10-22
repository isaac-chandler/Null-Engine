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

public class NullShaderSwitchStatementListImpl extends ASTWrapperPsiElement implements NullShaderSwitchStatementList {

  public NullShaderSwitchStatementListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitSwitchStatementList(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public NullShaderStatementList getStatementList() {
    return findNotNullChildByClass(NullShaderStatementList.class);
  }

}
