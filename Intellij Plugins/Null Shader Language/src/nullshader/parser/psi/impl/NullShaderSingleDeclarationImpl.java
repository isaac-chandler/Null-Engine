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

public class NullShaderSingleDeclarationImpl extends ASTWrapperPsiElement implements NullShaderSingleDeclaration {

  public NullShaderSingleDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitSingleDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NullShaderArraySpecifier getArraySpecifier() {
    return findChildByClass(NullShaderArraySpecifier.class);
  }

  @Override
  @NotNull
  public NullShaderFullySpecifiedType getFullySpecifiedType() {
    return findNotNullChildByClass(NullShaderFullySpecifiedType.class);
  }

  @Override
  @Nullable
  public NullShaderInitializer getInitializer() {
    return findChildByClass(NullShaderInitializer.class);
  }

}
