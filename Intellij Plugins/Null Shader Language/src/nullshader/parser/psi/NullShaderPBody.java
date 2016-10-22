// This is a generated file. Not intended for manual editing.
package nullshader.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface NullShaderPBody extends PsiElement {

  @Nullable
  NullShaderPDefine getPDefine();

  @Nullable
  NullShaderPElif getPElif();

  @Nullable
  NullShaderPElse getPElse();

  @Nullable
  NullShaderPEndif getPEndif();

  @Nullable
  NullShaderPError getPError();

  @Nullable
  NullShaderPExtension getPExtension();

  @Nullable
  NullShaderPIf getPIf();

  @Nullable
  NullShaderPIfdef getPIfdef();

  @Nullable
  NullShaderPIfndef getPIfndef();

  @Nullable
  NullShaderPInclude getPInclude();

  @Nullable
  NullShaderPLine getPLine();

  @Nullable
  NullShaderPPragma getPPragma();

  @Nullable
  NullShaderPUndef getPUndef();

  @Nullable
  NullShaderPVersion getPVersion();

}
