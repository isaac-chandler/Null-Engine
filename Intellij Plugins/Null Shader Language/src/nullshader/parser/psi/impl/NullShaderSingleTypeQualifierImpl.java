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

public class NullShaderSingleTypeQualifierImpl extends ASTWrapperPsiElement implements NullShaderSingleTypeQualifier {

  public NullShaderSingleTypeQualifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitSingleTypeQualifier(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NullShaderInterpolationQualifier getInterpolationQualifier() {
    return findChildByClass(NullShaderInterpolationQualifier.class);
  }

  @Override
  @Nullable
  public NullShaderInvariantQualifier getInvariantQualifier() {
    return findChildByClass(NullShaderInvariantQualifier.class);
  }

  @Override
  @Nullable
  public NullShaderLayoutQualifer getLayoutQualifer() {
    return findChildByClass(NullShaderLayoutQualifer.class);
  }

  @Override
  @Nullable
  public NullShaderPreciseQualifier getPreciseQualifier() {
    return findChildByClass(NullShaderPreciseQualifier.class);
  }

  @Override
  @Nullable
  public NullShaderPrecisionQualifier getPrecisionQualifier() {
    return findChildByClass(NullShaderPrecisionQualifier.class);
  }

  @Override
  @Nullable
  public NullShaderStorageQualifier getStorageQualifier() {
    return findChildByClass(NullShaderStorageQualifier.class);
  }

}
