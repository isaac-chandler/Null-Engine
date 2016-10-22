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

public class NullShaderDeclarationImpl extends ASTWrapperPsiElement implements NullShaderDeclaration {

  public NullShaderDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitDeclaration(this);
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
  @Nullable
  public NullShaderFunctionPrototype getFunctionPrototype() {
    return findChildByClass(NullShaderFunctionPrototype.class);
  }

  @Override
  @Nullable
  public NullShaderIdentifierList getIdentifierList() {
    return findChildByClass(NullShaderIdentifierList.class);
  }

  @Override
  @Nullable
  public NullShaderInitDeclaratorList getInitDeclaratorList() {
    return findChildByClass(NullShaderInitDeclaratorList.class);
  }

  @Override
  @Nullable
  public NullShaderPrecisionQualifier getPrecisionQualifier() {
    return findChildByClass(NullShaderPrecisionQualifier.class);
  }

  @Override
  @Nullable
  public NullShaderStructDeclarationList getStructDeclarationList() {
    return findChildByClass(NullShaderStructDeclarationList.class);
  }

  @Override
  @Nullable
  public NullShaderTypeQualifier getTypeQualifier() {
    return findChildByClass(NullShaderTypeQualifier.class);
  }

  @Override
  @Nullable
  public NullShaderTypeSpecifier getTypeSpecifier() {
    return findChildByClass(NullShaderTypeSpecifier.class);
  }

}
