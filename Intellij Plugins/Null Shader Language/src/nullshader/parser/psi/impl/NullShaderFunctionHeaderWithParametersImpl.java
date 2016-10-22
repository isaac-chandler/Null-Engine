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

public class NullShaderFunctionHeaderWithParametersImpl extends ASTWrapperPsiElement implements NullShaderFunctionHeaderWithParameters {

  public NullShaderFunctionHeaderWithParametersImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitFunctionHeaderWithParameters(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public NullShaderFunctionHeader getFunctionHeader() {
    return findNotNullChildByClass(NullShaderFunctionHeader.class);
  }

  @Override
  @NotNull
  public List<NullShaderParameterDeclaration> getParameterDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, NullShaderParameterDeclaration.class);
  }

}
