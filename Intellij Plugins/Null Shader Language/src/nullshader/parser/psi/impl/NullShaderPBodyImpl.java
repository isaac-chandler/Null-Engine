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

public class NullShaderPBodyImpl extends ASTWrapperPsiElement implements NullShaderPBody {

  public NullShaderPBodyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NullShaderVisitor visitor) {
    visitor.visitPBody(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NullShaderVisitor) accept((NullShaderVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public NullShaderPDefine getPDefine() {
    return findChildByClass(NullShaderPDefine.class);
  }

  @Override
  @Nullable
  public NullShaderPElif getPElif() {
    return findChildByClass(NullShaderPElif.class);
  }

  @Override
  @Nullable
  public NullShaderPElse getPElse() {
    return findChildByClass(NullShaderPElse.class);
  }

  @Override
  @Nullable
  public NullShaderPEndif getPEndif() {
    return findChildByClass(NullShaderPEndif.class);
  }

  @Override
  @Nullable
  public NullShaderPError getPError() {
    return findChildByClass(NullShaderPError.class);
  }

  @Override
  @Nullable
  public NullShaderPExtension getPExtension() {
    return findChildByClass(NullShaderPExtension.class);
  }

  @Override
  @Nullable
  public NullShaderPIf getPIf() {
    return findChildByClass(NullShaderPIf.class);
  }

  @Override
  @Nullable
  public NullShaderPIfdef getPIfdef() {
    return findChildByClass(NullShaderPIfdef.class);
  }

  @Override
  @Nullable
  public NullShaderPIfndef getPIfndef() {
    return findChildByClass(NullShaderPIfndef.class);
  }

  @Override
  @Nullable
  public NullShaderPInclude getPInclude() {
    return findChildByClass(NullShaderPInclude.class);
  }

  @Override
  @Nullable
  public NullShaderPLine getPLine() {
    return findChildByClass(NullShaderPLine.class);
  }

  @Override
  @Nullable
  public NullShaderPPragma getPPragma() {
    return findChildByClass(NullShaderPPragma.class);
  }

  @Override
  @Nullable
  public NullShaderPUndef getPUndef() {
    return findChildByClass(NullShaderPUndef.class);
  }

  @Override
  @Nullable
  public NullShaderPVersion getPVersion() {
    return findChildByClass(NullShaderPVersion.class);
  }

}
