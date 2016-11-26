// This is a generated file. Not intended for manual editing.
package nullshader.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static nullshader.parser.psi.NullShaderTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class NullShaderParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ADD_OP) {
      r = add_op(b, 0);
    }
    else if (t == ARRAY_SPECIFIER) {
      r = array_specifier(b, 0);
    }
    else if (t == ASSIGNMENT_OP) {
      r = assignment_op(b, 0);
    }
    else if (t == CASE_LABEL) {
      r = case_label(b, 0);
    }
    else if (t == COMPOUND_STATEMENT) {
      r = compound_statement(b, 0);
    }
    else if (t == COMPOUND_STATEMENT_NO_NEW_SCOPE) {
      r = compound_statement_no_new_scope(b, 0);
    }
    else if (t == CONDITION) {
      r = condition(b, 0);
    }
    else if (t == CONDITIONOPT) {
      r = conditionopt(b, 0);
    }
    else if (t == DECLARATION) {
      r = declaration(b, 0);
    }
    else if (t == DECLARATION_STATEMENT) {
      r = declaration_statement(b, 0);
    }
    else if (t == EQUALITY_OP) {
      r = equality_op(b, 0);
    }
    else if (t == EXPR) {
      r = expr(b, 0, -1);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0);
    }
    else if (t == EXPRESSION_STATEMENT) {
      r = expression_statement(b, 0);
    }
    else if (t == EXTERNAL_DECLARATION) {
      r = external_declaration(b, 0);
    }
    else if (t == FIELD_SELECTION) {
      r = field_selection(b, 0);
    }
    else if (t == FOR_INIT_STATEMENT) {
      r = for_init_statement(b, 0);
    }
    else if (t == FOR_REST_STATEMENT) {
      r = for_rest_statement(b, 0);
    }
    else if (t == FULLY_SPECIFIED_TYPE) {
      r = fully_specified_type(b, 0);
    }
    else if (t == FUNCTION_CALL) {
      r = function_call(b, 0);
    }
    else if (t == FUNCTION_CALL_GENERIC) {
      r = function_call_generic(b, 0);
    }
    else if (t == FUNCTION_CALL_HEADER) {
      r = function_call_header(b, 0);
    }
    else if (t == FUNCTION_CALL_HEADER_NO_PARAMETERS) {
      r = function_call_header_no_parameters(b, 0);
    }
    else if (t == FUNCTION_CALL_HEADER_WITH_PARAMETERS) {
      r = function_call_header_with_parameters(b, 0);
    }
    else if (t == FUNCTION_CALL_OR_METHOD) {
      r = function_call_or_method(b, 0);
    }
    else if (t == FUNCTION_DECLARATOR) {
      r = function_declarator(b, 0);
    }
    else if (t == FUNCTION_DEFINITION) {
      r = function_definition(b, 0);
    }
    else if (t == FUNCTION_HEADER) {
      r = function_header(b, 0);
    }
    else if (t == FUNCTION_HEADER_WITH_PARAMETERS) {
      r = function_header_with_parameters(b, 0);
    }
    else if (t == FUNCTION_PROTOTYPE) {
      r = function_prototype(b, 0);
    }
    else if (t == GLSL_BLOCK) {
      r = glsl_block(b, 0);
    }
    else if (t == IDENTIFIER_LIST) {
      r = identifier_list(b, 0);
    }
    else if (t == INIT_DECLARATOR_LIST) {
      r = init_declarator_list(b, 0);
    }
    else if (t == INITIALIZER) {
      r = initializer(b, 0);
    }
    else if (t == INITIALIZER_LIST) {
      r = initializer_list(b, 0);
    }
    else if (t == INTERPOLATION_QUALIFIER) {
      r = interpolation_qualifier(b, 0);
    }
    else if (t == INVARIANT_QUALIFIER) {
      r = invariant_qualifier(b, 0);
    }
    else if (t == ITERATION_STATEMENT) {
      r = iteration_statement(b, 0);
    }
    else if (t == JUMP_STATEMENT) {
      r = jump_statement(b, 0);
    }
    else if (t == LAYOUT_QUALIFER) {
      r = layout_qualifer(b, 0);
    }
    else if (t == LAYOUT_QUALIFER_ID) {
      r = layout_qualifer_id(b, 0);
    }
    else if (t == LAYOUT_QUALIFER_ID_LIST) {
      r = layout_qualifer_id_list(b, 0);
    }
    else if (t == MUL_OP) {
      r = mul_op(b, 0);
    }
    else if (t == P_BODY) {
      r = p_body(b, 0);
    }
    else if (t == P_DEFINE) {
      r = p_define(b, 0);
    }
    else if (t == P_ELIF) {
      r = p_elif(b, 0);
    }
    else if (t == P_ELSE) {
      r = p_else(b, 0);
    }
    else if (t == P_ENDIF) {
      r = p_endif(b, 0);
    }
    else if (t == P_ERROR) {
      r = p_error(b, 0);
    }
    else if (t == P_EXTENSION) {
      r = p_extension(b, 0);
    }
    else if (t == P_IF) {
      r = p_if(b, 0);
    }
    else if (t == P_IFDEF) {
      r = p_ifdef(b, 0);
    }
    else if (t == P_IFNDEF) {
      r = p_ifndef(b, 0);
    }
    else if (t == P_INCLUDE) {
      r = p_include(b, 0);
    }
    else if (t == P_LINE) {
      r = p_line(b, 0);
    }
    else if (t == P_PRAGMA) {
      r = p_pragma(b, 0);
    }
    else if (t == P_UNDEF) {
      r = p_undef(b, 0);
    }
    else if (t == P_VERSION) {
      r = p_version(b, 0);
    }
    else if (t == PARAMETER_DECLARATION) {
      r = parameter_declaration(b, 0);
    }
    else if (t == PARAMETER_DECLARATOR) {
      r = parameter_declarator(b, 0);
    }
    else if (t == PARAMETER_TYPE_SPECIFIER) {
      r = parameter_type_specifier(b, 0);
    }
    else if (t == PRECISE_QUALIFIER) {
      r = precise_qualifier(b, 0);
    }
    else if (t == PRECISION_QUALIFIER) {
      r = precision_qualifier(b, 0);
    }
    else if (t == PREPROCESSOR) {
      r = preprocessor(b, 0);
    }
    else if (t == RELATIONAL_OP) {
      r = relational_op(b, 0);
    }
    else if (t == SELECTION_REST_STATEMENT) {
      r = selection_rest_statement(b, 0);
    }
    else if (t == SELECTION_STATEMENT) {
      r = selection_statement(b, 0);
    }
    else if (t == SHIFT_OP) {
      r = shift_op(b, 0);
    }
    else if (t == SIMPLE_STATEMENT) {
      r = simple_statement(b, 0);
    }
    else if (t == SINGLE_DECLARATION) {
      r = single_declaration(b, 0);
    }
    else if (t == SINGLE_TYPE_QUALIFIER) {
      r = single_type_qualifier(b, 0);
    }
    else if (t == STATEMENT) {
      r = statement(b, 0);
    }
    else if (t == STATEMENT_LIST) {
      r = statement_list(b, 0);
    }
    else if (t == STATEMENT_NO_NEW_SCOPE) {
      r = statement_no_new_scope(b, 0);
    }
    else if (t == STORAGE_QUALIFIER) {
      r = storage_qualifier(b, 0);
    }
    else if (t == STRUCT_DECLARATION) {
      r = struct_declaration(b, 0);
    }
    else if (t == STRUCT_DECLARATION_LIST) {
      r = struct_declaration_list(b, 0);
    }
    else if (t == STRUCT_DECLARATOR) {
      r = struct_declarator(b, 0);
    }
    else if (t == STRUCT_DECLARATOR_LIST) {
      r = struct_declarator_list(b, 0);
    }
    else if (t == STRUCT_SPECIFIER) {
      r = struct_specifier(b, 0);
    }
    else if (t == SWITCH_STATEMENT) {
      r = switch_statement(b, 0);
    }
    else if (t == SWITCH_STATEMENT_LIST) {
      r = switch_statement_list(b, 0);
    }
    else if (t == TRANSLATION_UNIT) {
      r = translation_unit(b, 0);
    }
    else if (t == TYPE_NAME_LIST) {
      r = type_name_list(b, 0);
    }
    else if (t == TYPE_QUALIFIER) {
      r = type_qualifier(b, 0);
    }
    else if (t == TYPE_SPECIFIER) {
      r = type_specifier(b, 0);
    }
    else if (t == TYPE_SPECIFIER_NON_ARRAY) {
      r = type_specifier_non_array(b, 0);
    }
    else if (t == UNARY_OPERATOR) {
      r = unary_operator(b, 0);
    }
    else if (t == UNUSED_TOKENS__) {
      r = unused_tokens__(b, 0);
    }
    else if (t == VERSION_BLOCK) {
      r = version_block(b, 0);
    }
    else if (t == VERSION_INFO) {
      r = version_info(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return nullShaderFile(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ADD_EXPR, AND_EXPR, ASSIGNMENT_EXPR, CONDITIONAL_EXPR,
      EQUALITY_EXPR, EXPR, LOGICAL_XOR_EXPR, LOGIC_AND_EXPR,
      LOGIC_OR_EXPR, MUL_EXPR, OR_EXPR, POSTFIX_1_EXPR,
      POSTFIX_2_EXPR, POSTFIX_3_EXPR, POSTFIX_4_EXPR, POSTFIX_5_EXPR,
      POSTFIX_6_EXPR, PRIMARY_1_EXPR, PRIMARY_2_EXPR, PRIMARY_3_EXPR,
      PRIMARY_4_EXPR, PRIMARY_5_EXPR, PRIMARY_6_EXPR, PRIMARY_7_EXPR,
      RELATIONAL_EXPR, SHIFT_EXPR, UNARY_EXPR, XOR_EXPR),
  };

  /* ********************************************************** */
  // OP_ADD|OP_SUB
  public static boolean add_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_op")) return false;
    if (!nextTokenIs(b, "<add op>", OP_ADD, OP_SUB)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADD_OP, "<add op>");
    r = consumeToken(b, OP_ADD);
    if (!r) r = consumeToken(b, OP_SUB);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (LSQUARE expr? RSQUARE)+
  public static boolean array_specifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_specifier")) return false;
    if (!nextTokenIs(b, LSQUARE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_specifier_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!array_specifier_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "array_specifier", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, ARRAY_SPECIFIER, r);
    return r;
  }

  // LSQUARE expr? RSQUARE
  private static boolean array_specifier_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_specifier_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LSQUARE);
    r = r && array_specifier_0_1(b, l + 1);
    r = r && consumeToken(b, RSQUARE);
    exit_section_(b, m, null, r);
    return r;
  }

  // expr?
  private static boolean array_specifier_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_specifier_0_1")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // OP_ASSIGN|OP_ADD_ASSIGN|OP_SUB_ASSIGN|OP_MUL_ASSIGN|OP_DIV_ASSIGN|OP_MOD_ASSIGN|OP_LSHIFT_ASSIGN|OP_RSHIFT_ASSIGN|OP_AND_ASSIGN|OP_XOR_ASSIGN|OP_OR_ASSIGN
  public static boolean assignment_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assignment_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ASSIGNMENT_OP, "<assignment op>");
    r = consumeToken(b, OP_ASSIGN);
    if (!r) r = consumeToken(b, OP_ADD_ASSIGN);
    if (!r) r = consumeToken(b, OP_SUB_ASSIGN);
    if (!r) r = consumeToken(b, OP_MUL_ASSIGN);
    if (!r) r = consumeToken(b, OP_DIV_ASSIGN);
    if (!r) r = consumeToken(b, OP_MOD_ASSIGN);
    if (!r) r = consumeToken(b, OP_LSHIFT_ASSIGN);
    if (!r) r = consumeToken(b, OP_RSHIFT_ASSIGN);
    if (!r) r = consumeToken(b, OP_AND_ASSIGN);
    if (!r) r = consumeToken(b, OP_XOR_ASSIGN);
    if (!r) r = consumeToken(b, OP_OR_ASSIGN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // version_block|glsl_block
  static boolean block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = version_block(b, l + 1);
    if (!r) r = glsl_block(b, l + 1);
    exit_section_(b, l, m, r, false, block_recover_parser_);
    return r;
  }

  /* ********************************************************** */
  // !(BLOCK_START)
  static boolean block_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BLOCK_START);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (KEYWORD_CASE expression COLON)|(KEYWORD_DEFAULT COLON)
  public static boolean case_label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_label")) return false;
    if (!nextTokenIs(b, "<case label>", KEYWORD_CASE, KEYWORD_DEFAULT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CASE_LABEL, "<case label>");
    r = case_label_0(b, l + 1);
    if (!r) r = case_label_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // KEYWORD_CASE expression COLON
  private static boolean case_label_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_label_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_CASE);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEYWORD_DEFAULT COLON
  private static boolean case_label_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "case_label_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_DEFAULT, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LCURLY statement_list? RCURLY
  public static boolean compound_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_statement")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCURLY);
    r = r && compound_statement_1(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, COMPOUND_STATEMENT, r);
    return r;
  }

  // statement_list?
  private static boolean compound_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_statement_1")) return false;
    statement_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // LCURLY statement_list? RCURLY
  public static boolean compound_statement_no_new_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_statement_no_new_scope")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCURLY);
    r = r && compound_statement_no_new_scope_1(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, COMPOUND_STATEMENT_NO_NEW_SCOPE, r);
    return r;
  }

  // statement_list?
  private static boolean compound_statement_no_new_scope_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compound_statement_no_new_scope_1")) return false;
    statement_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // expression|(fully_specified_type IDENTIFIER OP_ASSIGN initializer)
  public static boolean condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONDITION, "<condition>");
    r = expression(b, l + 1);
    if (!r) r = condition_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // fully_specified_type IDENTIFIER OP_ASSIGN initializer
  private static boolean condition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = fully_specified_type(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, OP_ASSIGN);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // condition
  public static boolean conditionopt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditionopt")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONDITIONOPT, "<conditionopt>");
    r = condition(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (function_prototype SEMICOLON)|(init_declarator_list SEMICOLON)|(KEYWORD_PRECISION precision_qualifier type_specifier SEMICOLON)|(type_qualifier IDENTIFIER LCURLY struct_declaration_list RCURLY (IDENTIFIER|array_specifier)? SEMICOLON)|(type_qualifier IDENTIFIER? SEMICOLON)|(type_qualifier IDENTIFIER identifier_list? SEMICOLON)
  public static boolean declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARATION, "<declaration>");
    r = declaration_0(b, l + 1);
    if (!r) r = declaration_1(b, l + 1);
    if (!r) r = declaration_2(b, l + 1);
    if (!r) r = declaration_3(b, l + 1);
    if (!r) r = declaration_4(b, l + 1);
    if (!r) r = declaration_5(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // function_prototype SEMICOLON
  private static boolean declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_prototype(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // init_declarator_list SEMICOLON
  private static boolean declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = init_declarator_list(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEYWORD_PRECISION precision_qualifier type_specifier SEMICOLON
  private static boolean declaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_PRECISION);
    r = r && precision_qualifier(b, l + 1);
    r = r && type_specifier(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // type_qualifier IDENTIFIER LCURLY struct_declaration_list RCURLY (IDENTIFIER|array_specifier)? SEMICOLON
  private static boolean declaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_qualifier(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, LCURLY);
    r = r && struct_declaration_list(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    r = r && declaration_3_5(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // (IDENTIFIER|array_specifier)?
  private static boolean declaration_3_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_3_5")) return false;
    declaration_3_5_0(b, l + 1);
    return true;
  }

  // IDENTIFIER|array_specifier
  private static boolean declaration_3_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_3_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = array_specifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // type_qualifier IDENTIFIER? SEMICOLON
  private static boolean declaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_qualifier(b, l + 1);
    r = r && declaration_4_1(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // IDENTIFIER?
  private static boolean declaration_4_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_4_1")) return false;
    consumeToken(b, IDENTIFIER);
    return true;
  }

  // type_qualifier IDENTIFIER identifier_list? SEMICOLON
  private static boolean declaration_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = type_qualifier(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    r = r && declaration_5_2(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // identifier_list?
  private static boolean declaration_5_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_5_2")) return false;
    identifier_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // declaration
  public static boolean declaration_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "declaration_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DECLARATION_STATEMENT, "<declaration statement>");
    r = declaration(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // OP_EQUAL|OP_NOT_EQUAL
  public static boolean equality_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equality_op")) return false;
    if (!nextTokenIs(b, "<equality op>", OP_EQUAL, OP_NOT_EQUAL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EQUALITY_OP, "<equality op>");
    r = consumeToken(b, OP_EQUAL);
    if (!r) r = consumeToken(b, OP_NOT_EQUAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !(LPAREN|unary_operator|type_specifier)
  static boolean expr_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !expr_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LPAREN|unary_operator|type_specifier
  private static boolean expr_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    if (!r) r = unary_operator(b, l + 1);
    if (!r) r = type_specifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expr (COMMA expr)*
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = expr(b, l + 1, -1);
    r = r && expression_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA expr)*
  private static boolean expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!expression_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expression_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA expr
  private static boolean expression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression? SEMICOLON
  public static boolean expression_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION_STATEMENT, "<expression statement>");
    r = expression_statement_0(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // expression?
  private static boolean expression_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_statement_0")) return false;
    expression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // function_definition|declaration|preprocessor
  public static boolean external_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "external_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXTERNAL_DECLARATION, "<external declaration>");
    r = function_definition(b, l + 1);
    if (!r) r = declaration(b, l + 1);
    if (!r) r = preprocessor(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean field_selection(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_selection")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, FIELD_SELECTION, r);
    return r;
  }

  /* ********************************************************** */
  // expression_statement|declaration_statement
  public static boolean for_init_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_init_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FOR_INIT_STATEMENT, "<for init statement>");
    r = expression_statement(b, l + 1);
    if (!r) r = declaration_statement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // conditionopt SEMICOLON expression?
  public static boolean for_rest_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_rest_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FOR_REST_STATEMENT, "<for rest statement>");
    r = conditionopt(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    r = r && for_rest_statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // expression?
  private static boolean for_rest_statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_rest_statement_2")) return false;
    expression(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // type_qualifier? type_specifier
  public static boolean fully_specified_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fully_specified_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FULLY_SPECIFIED_TYPE, "<fully specified type>");
    r = fully_specified_type_0(b, l + 1);
    r = r && type_specifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type_qualifier?
  private static boolean fully_specified_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fully_specified_type_0")) return false;
    type_qualifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // function_call_or_method
  public static boolean function_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_or_method(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL, r);
    return r;
  }

  /* ********************************************************** */
  // (function_call_header_with_parameters RPAREN)|(function_call_header_no_parameters RPAREN)
  public static boolean function_call_generic(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_generic")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_generic_0(b, l + 1);
    if (!r) r = function_call_generic_1(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_GENERIC, r);
    return r;
  }

  // function_call_header_with_parameters RPAREN
  private static boolean function_call_generic_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_generic_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_header_with_parameters(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // function_call_header_no_parameters RPAREN
  private static boolean function_call_generic_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_generic_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_header_no_parameters(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LPAREN
  public static boolean function_call_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_header")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    exit_section_(b, m, FUNCTION_CALL_HEADER, r);
    return r;
  }

  /* ********************************************************** */
  // (function_call_header TYPE_VOID)|function_call_header
  public static boolean function_call_header_no_parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_header_no_parameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_header_no_parameters_0(b, l + 1);
    if (!r) r = function_call_header(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_HEADER_NO_PARAMETERS, r);
    return r;
  }

  // function_call_header TYPE_VOID
  private static boolean function_call_header_no_parameters_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_header_no_parameters_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_header(b, l + 1);
    r = r && consumeToken(b, TYPE_VOID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // function_call_header expr (COMMA expr)*
  public static boolean function_call_header_with_parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_header_with_parameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_header(b, l + 1);
    r = r && expr(b, l + 1, -1);
    r = r && function_call_header_with_parameters_2(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_HEADER_WITH_PARAMETERS, r);
    return r;
  }

  // (COMMA expr)*
  private static boolean function_call_header_with_parameters_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_header_with_parameters_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!function_call_header_with_parameters_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_call_header_with_parameters_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA expr
  private static boolean function_call_header_with_parameters_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_header_with_parameters_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // function_call_generic
  public static boolean function_call_or_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_or_method")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_call_generic(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_OR_METHOD, r);
    return r;
  }

  /* ********************************************************** */
  // function_header|function_header_with_parameters
  public static boolean function_declarator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_declarator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_DECLARATOR, "<function declarator>");
    r = function_header(b, l + 1);
    if (!r) r = function_header_with_parameters(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // function_prototype compound_statement_no_new_scope
  public static boolean function_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_definition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_DEFINITION, "<function definition>");
    r = function_prototype(b, l + 1);
    r = r && compound_statement_no_new_scope(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // fully_specified_type IDENTIFIER LPAREN
  public static boolean function_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_header")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_HEADER, "<function header>");
    r = fully_specified_type(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, LPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // function_header parameter_declaration (COMMA parameter_declaration)*
  public static boolean function_header_with_parameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_header_with_parameters")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_HEADER_WITH_PARAMETERS, "<function header with parameters>");
    r = function_header(b, l + 1);
    r = r && parameter_declaration(b, l + 1);
    r = r && function_header_with_parameters_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA parameter_declaration)*
  private static boolean function_header_with_parameters_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_header_with_parameters_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!function_header_with_parameters_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_header_with_parameters_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA parameter_declaration
  private static boolean function_header_with_parameters_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_header_with_parameters_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && parameter_declaration(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // function_declarator RPAREN
  public static boolean function_prototype(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_prototype")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_PROTOTYPE, "<function prototype>");
    r = function_declarator(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BLOCK_START BLOCK_NAME translation_unit? BLOCK_END BLOCK_NAME
  public static boolean glsl_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "glsl_block")) return false;
    if (!nextTokenIs(b, BLOCK_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, BLOCK_START, BLOCK_NAME);
    r = r && glsl_block_2(b, l + 1);
    r = r && consumeTokens(b, 0, BLOCK_END, BLOCK_NAME);
    exit_section_(b, m, GLSL_BLOCK, r);
    return r;
  }

  // translation_unit?
  private static boolean glsl_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "glsl_block_2")) return false;
    translation_unit(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (COMMA IDENTIFIER)+
  public static boolean identifier_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_list")) return false;
    if (!nextTokenIs(b, COMMA)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = identifier_list_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!identifier_list_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "identifier_list", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, IDENTIFIER_LIST, r);
    return r;
  }

  // COMMA IDENTIFIER
  private static boolean identifier_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier_list_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // single_declaration (COMMA IDENTIFIER array_specifier? (OP_ASSIGN initializer)?)*
  public static boolean init_declarator_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_declarator_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INIT_DECLARATOR_LIST, "<init declarator list>");
    r = single_declaration(b, l + 1);
    r = r && init_declarator_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA IDENTIFIER array_specifier? (OP_ASSIGN initializer)?)*
  private static boolean init_declarator_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_declarator_list_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!init_declarator_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "init_declarator_list_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA IDENTIFIER array_specifier? (OP_ASSIGN initializer)?
  private static boolean init_declarator_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_declarator_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, IDENTIFIER);
    r = r && init_declarator_list_1_0_2(b, l + 1);
    r = r && init_declarator_list_1_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // array_specifier?
  private static boolean init_declarator_list_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_declarator_list_1_0_2")) return false;
    array_specifier(b, l + 1);
    return true;
  }

  // (OP_ASSIGN initializer)?
  private static boolean init_declarator_list_1_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_declarator_list_1_0_3")) return false;
    init_declarator_list_1_0_3_0(b, l + 1);
    return true;
  }

  // OP_ASSIGN initializer
  private static boolean init_declarator_list_1_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_declarator_list_1_0_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_ASSIGN);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expr|(LCURLY initializer_list RCURLY)
  public static boolean initializer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INITIALIZER, "<initializer>");
    r = expr(b, l + 1, -1);
    if (!r) r = initializer_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LCURLY initializer_list RCURLY
  private static boolean initializer_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LCURLY);
    r = r && initializer_list(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // initializer (COMMA initializer)*
  public static boolean initializer_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INITIALIZER_LIST, "<initializer list>");
    r = initializer(b, l + 1);
    r = r && initializer_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA initializer)*
  private static boolean initializer_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer_list_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!initializer_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "initializer_list_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA initializer
  private static boolean initializer_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "initializer_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_SMOOTH|KEYWORD_FLAT|KEYWORD_NOPERSPECTIVE
  public static boolean interpolation_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "interpolation_qualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INTERPOLATION_QUALIFIER, "<interpolation qualifier>");
    r = consumeToken(b, KEYWORD_SMOOTH);
    if (!r) r = consumeToken(b, KEYWORD_FLAT);
    if (!r) r = consumeToken(b, KEYWORD_NOPERSPECTIVE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_INVARIANT
  public static boolean invariant_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invariant_qualifier")) return false;
    if (!nextTokenIs(b, KEYWORD_INVARIANT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_INVARIANT);
    exit_section_(b, m, INVARIANT_QUALIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // (KEYWORD_WHILE LPAREN condition RPAREN statement_no_new_scope)|(KEYWORD_DO statement KEYWORD_WHILE LPAREN expression RPAREN SEMICOLON)|(KEYWORD_FOR LPAREN for_init_statement for_rest_statement RPAREN statement_no_new_scope)
  public static boolean iteration_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iteration_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ITERATION_STATEMENT, "<iteration statement>");
    r = iteration_statement_0(b, l + 1);
    if (!r) r = iteration_statement_1(b, l + 1);
    if (!r) r = iteration_statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // KEYWORD_WHILE LPAREN condition RPAREN statement_no_new_scope
  private static boolean iteration_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iteration_statement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_WHILE, LPAREN);
    r = r && condition(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && statement_no_new_scope(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEYWORD_DO statement KEYWORD_WHILE LPAREN expression RPAREN SEMICOLON
  private static boolean iteration_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iteration_statement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_DO);
    r = r && statement(b, l + 1);
    r = r && consumeTokens(b, 0, KEYWORD_WHILE, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeTokens(b, 0, RPAREN, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEYWORD_FOR LPAREN for_init_statement for_rest_statement RPAREN statement_no_new_scope
  private static boolean iteration_statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iteration_statement_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_FOR, LPAREN);
    r = r && for_init_statement(b, l + 1);
    r = r && for_rest_statement(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && statement_no_new_scope(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ((KEYWORD_CONTINUE|KEYWORD_BREAK|KEYWORD_RETURN|KEYWORD_DISCARD) SEMICOLON)|(KEYWORD_RETURN expression SEMICOLON)
  public static boolean jump_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jump_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, JUMP_STATEMENT, "<jump statement>");
    r = jump_statement_0(b, l + 1);
    if (!r) r = jump_statement_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (KEYWORD_CONTINUE|KEYWORD_BREAK|KEYWORD_RETURN|KEYWORD_DISCARD) SEMICOLON
  private static boolean jump_statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jump_statement_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = jump_statement_0_0(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEYWORD_CONTINUE|KEYWORD_BREAK|KEYWORD_RETURN|KEYWORD_DISCARD
  private static boolean jump_statement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jump_statement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_CONTINUE);
    if (!r) r = consumeToken(b, KEYWORD_BREAK);
    if (!r) r = consumeToken(b, KEYWORD_RETURN);
    if (!r) r = consumeToken(b, KEYWORD_DISCARD);
    exit_section_(b, m, null, r);
    return r;
  }

  // KEYWORD_RETURN expression SEMICOLON
  private static boolean jump_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jump_statement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_RETURN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_LAYOUT LPAREN layout_qualifer_id_list RPAREN
  public static boolean layout_qualifer(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "layout_qualifer")) return false;
    if (!nextTokenIs(b, KEYWORD_LAYOUT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_LAYOUT, LPAREN);
    r = r && layout_qualifer_id_list(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, LAYOUT_QUALIFER, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER|(IDENTIFIER OP_ASSIGN expr)|KEYWORD_SHARED
  public static boolean layout_qualifer_id(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "layout_qualifer_id")) return false;
    if (!nextTokenIs(b, "<layout qualifer id>", IDENTIFIER, KEYWORD_SHARED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAYOUT_QUALIFER_ID, "<layout qualifer id>");
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = layout_qualifer_id_1(b, l + 1);
    if (!r) r = consumeToken(b, KEYWORD_SHARED);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // IDENTIFIER OP_ASSIGN expr
  private static boolean layout_qualifer_id_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "layout_qualifer_id_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, OP_ASSIGN);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // layout_qualifer_id (COMMA layout_qualifer_id)*
  public static boolean layout_qualifer_id_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "layout_qualifer_id_list")) return false;
    if (!nextTokenIs(b, "<layout qualifer id list>", IDENTIFIER, KEYWORD_SHARED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAYOUT_QUALIFER_ID_LIST, "<layout qualifer id list>");
    r = layout_qualifer_id(b, l + 1);
    r = r && layout_qualifer_id_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA layout_qualifer_id)*
  private static boolean layout_qualifer_id_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "layout_qualifer_id_list_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!layout_qualifer_id_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "layout_qualifer_id_list_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA layout_qualifer_id
  private static boolean layout_qualifer_id_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "layout_qualifer_id_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && layout_qualifer_id(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OP_MUL|OP_DIV|OP_MOD
  public static boolean mul_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MUL_OP, "<mul op>");
    r = consumeToken(b, OP_MUL);
    if (!r) r = consumeToken(b, OP_DIV);
    if (!r) r = consumeToken(b, OP_MOD);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // translation_unit|block+
  static boolean nullShaderFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nullShaderFile")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = translation_unit(b, l + 1);
    if (!r) r = nullShaderFile_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // block+
  private static boolean nullShaderFile_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nullShaderFile_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = block(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "nullShaderFile_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // p_include|p_define|p_undef|p_if|p_ifdef|p_ifndef|p_else|p_elif|p_endif|p_error|p_pragma|p_extension|p_version|p_line
  public static boolean p_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_body")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, P_BODY, "<p body>");
    r = p_include(b, l + 1);
    if (!r) r = p_define(b, l + 1);
    if (!r) r = p_undef(b, l + 1);
    if (!r) r = p_if(b, l + 1);
    if (!r) r = p_ifdef(b, l + 1);
    if (!r) r = p_ifndef(b, l + 1);
    if (!r) r = p_else(b, l + 1);
    if (!r) r = p_elif(b, l + 1);
    if (!r) r = p_endif(b, l + 1);
    if (!r) r = p_error(b, l + 1);
    if (!r) r = p_pragma(b, l + 1);
    if (!r) r = p_extension(b, l + 1);
    if (!r) r = p_version(b, l + 1);
    if (!r) r = p_line(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_DEFINE IDENTIFIER (!PREPROCESSOR_END)+
  public static boolean p_define(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_define")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_DEFINE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREPROCESSOR_DEFINE, IDENTIFIER);
    r = r && p_define_2(b, l + 1);
    exit_section_(b, m, P_DEFINE, r);
    return r;
  }

  // (!PREPROCESSOR_END)+
  private static boolean p_define_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_define_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_define_2_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!p_define_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "p_define_2", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // !PREPROCESSOR_END
  private static boolean p_define_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_define_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, PREPROCESSOR_END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_ELIF (!PREPROCESSOR_END)+
  public static boolean p_elif(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_elif")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_ELIF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_ELIF);
    r = r && p_elif_1(b, l + 1);
    exit_section_(b, m, P_ELIF, r);
    return r;
  }

  // (!PREPROCESSOR_END)+
  private static boolean p_elif_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_elif_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_elif_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!p_elif_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "p_elif_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // !PREPROCESSOR_END
  private static boolean p_elif_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_elif_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, PREPROCESSOR_END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_ELSE
  public static boolean p_else(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_else")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_ELSE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_ELSE);
    exit_section_(b, m, P_ELSE, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_ENDIF
  public static boolean p_endif(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_endif")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_ENDIF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_ENDIF);
    exit_section_(b, m, P_ENDIF, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_ERROR (!PREPROCESSOR_END)+
  public static boolean p_error(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_error")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_ERROR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_ERROR);
    r = r && p_error_1(b, l + 1);
    exit_section_(b, m, P_ERROR, r);
    return r;
  }

  // (!PREPROCESSOR_END)+
  private static boolean p_error_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_error_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_error_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!p_error_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "p_error_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // !PREPROCESSOR_END
  private static boolean p_error_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_error_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, PREPROCESSOR_END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_EXTENSION IDENTIFIER COLON IDENTIFIER
  public static boolean p_extension(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_extension")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_EXTENSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREPROCESSOR_EXTENSION, IDENTIFIER, COLON, IDENTIFIER);
    exit_section_(b, m, P_EXTENSION, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_IF (!PREPROCESSOR_END)+
  public static boolean p_if(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_if")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_IF);
    r = r && p_if_1(b, l + 1);
    exit_section_(b, m, P_IF, r);
    return r;
  }

  // (!PREPROCESSOR_END)+
  private static boolean p_if_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_if_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_if_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!p_if_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "p_if_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // !PREPROCESSOR_END
  private static boolean p_if_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_if_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, PREPROCESSOR_END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_IFDEF IDENTIFIER
  public static boolean p_ifdef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_ifdef")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_IFDEF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREPROCESSOR_IFDEF, IDENTIFIER);
    exit_section_(b, m, P_IFDEF, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_IFNDEF IDENTIFIER
  public static boolean p_ifndef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_ifndef")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_IFNDEF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREPROCESSOR_IFNDEF, IDENTIFIER);
    exit_section_(b, m, P_IFNDEF, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_INCLUDE (PREPROCESSOR_LOCAL_FILE|PREPROCESSOR_GLOBAL_FILE)
  public static boolean p_include(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_include")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_INCLUDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_INCLUDE);
    r = r && p_include_1(b, l + 1);
    exit_section_(b, m, P_INCLUDE, r);
    return r;
  }

  // PREPROCESSOR_LOCAL_FILE|PREPROCESSOR_GLOBAL_FILE
  private static boolean p_include_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_include_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_LOCAL_FILE);
    if (!r) r = consumeToken(b, PREPROCESSOR_GLOBAL_FILE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_LINE INT INT?
  public static boolean p_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_line")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_LINE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREPROCESSOR_LINE, INT);
    r = r && p_line_2(b, l + 1);
    exit_section_(b, m, P_LINE, r);
    return r;
  }

  // INT?
  private static boolean p_line_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_line_2")) return false;
    consumeToken(b, INT);
    return true;
  }

  /* ********************************************************** */
  // PREPROCESSOR_PRAGMA (!PREPROCESSOR_END)+
  public static boolean p_pragma(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_pragma")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_PRAGMA)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_PRAGMA);
    r = r && p_pragma_1(b, l + 1);
    exit_section_(b, m, P_PRAGMA, r);
    return r;
  }

  // (!PREPROCESSOR_END)+
  private static boolean p_pragma_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_pragma_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = p_pragma_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!p_pragma_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "p_pragma_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // !PREPROCESSOR_END
  private static boolean p_pragma_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_pragma_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, PREPROCESSOR_END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_UNDEF IDENTIFIER
  public static boolean p_undef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_undef")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_UNDEF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PREPROCESSOR_UNDEF, IDENTIFIER);
    exit_section_(b, m, P_UNDEF, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_VERSION version_info
  public static boolean p_version(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_version")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_VERSION);
    r = r && version_info(b, l + 1);
    exit_section_(b, m, P_VERSION, r);
    return r;
  }

  /* ********************************************************** */
  // type_qualifier? (parameter_declarator|parameter_type_specifier)
  public static boolean parameter_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER_DECLARATION, "<parameter declaration>");
    r = parameter_declaration_0(b, l + 1);
    r = r && parameter_declaration_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type_qualifier?
  private static boolean parameter_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_declaration_0")) return false;
    type_qualifier(b, l + 1);
    return true;
  }

  // parameter_declarator|parameter_type_specifier
  private static boolean parameter_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_declaration_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameter_declarator(b, l + 1);
    if (!r) r = parameter_type_specifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type_specifier IDENTIFIER array_specifier?
  public static boolean parameter_declarator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_declarator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER_DECLARATOR, "<parameter declarator>");
    r = type_specifier(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    r = r && parameter_declarator_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // array_specifier?
  private static boolean parameter_declarator_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_declarator_2")) return false;
    array_specifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // type_specifier
  public static boolean parameter_type_specifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter_type_specifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER_TYPE_SPECIFIER, "<parameter type specifier>");
    r = type_specifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_PRECISE
  public static boolean precise_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precise_qualifier")) return false;
    if (!nextTokenIs(b, KEYWORD_PRECISE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_PRECISE);
    exit_section_(b, m, PRECISE_QUALIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_HIGHP|KEYWORD_MEDIUMP|KEYWORD_LOWP
  public static boolean precision_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "precision_qualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRECISION_QUALIFIER, "<precision qualifier>");
    r = consumeToken(b, KEYWORD_HIGHP);
    if (!r) r = consumeToken(b, KEYWORD_MEDIUMP);
    if (!r) r = consumeToken(b, KEYWORD_LOWP);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_START p_body PREPROCESSOR_END
  public static boolean preprocessor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor")) return false;
    if (!nextTokenIs(b, PREPROCESSOR_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_START);
    r = r && p_body(b, l + 1);
    r = r && consumeToken(b, PREPROCESSOR_END);
    exit_section_(b, m, PREPROCESSOR, r);
    return r;
  }

  /* ********************************************************** */
  // OP_LESS|OP_LESS_EQUAL|OP_GREATER|OP_GREATER_EQUAL
  public static boolean relational_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relational_op")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RELATIONAL_OP, "<relational op>");
    r = consumeToken(b, OP_LESS);
    if (!r) r = consumeToken(b, OP_LESS_EQUAL);
    if (!r) r = consumeToken(b, OP_GREATER);
    if (!r) r = consumeToken(b, OP_GREATER_EQUAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // statement (KEYWORD_ELSE statement)?
  public static boolean selection_rest_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_rest_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SELECTION_REST_STATEMENT, "<selection rest statement>");
    r = statement(b, l + 1);
    r = r && selection_rest_statement_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (KEYWORD_ELSE statement)?
  private static boolean selection_rest_statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_rest_statement_1")) return false;
    selection_rest_statement_1_0(b, l + 1);
    return true;
  }

  // KEYWORD_ELSE statement
  private static boolean selection_rest_statement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_rest_statement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, KEYWORD_ELSE);
    r = r && statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_IF LPAREN expression RPAREN selection_rest_statement
  public static boolean selection_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "selection_statement")) return false;
    if (!nextTokenIs(b, KEYWORD_IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_IF, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && selection_rest_statement(b, l + 1);
    exit_section_(b, m, SELECTION_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // OP_LSHIFT|OP_RSHIFT
  public static boolean shift_op(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_op")) return false;
    if (!nextTokenIs(b, "<shift op>", OP_LSHIFT, OP_RSHIFT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SHIFT_OP, "<shift op>");
    r = consumeToken(b, OP_LSHIFT);
    if (!r) r = consumeToken(b, OP_RSHIFT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // declaration_statement|expression_statement|selection_statement|switch_statement|case_label|iteration_statement|jump_statement
  public static boolean simple_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SIMPLE_STATEMENT, "<simple statement>");
    r = declaration_statement(b, l + 1);
    if (!r) r = expression_statement(b, l + 1);
    if (!r) r = selection_statement(b, l + 1);
    if (!r) r = switch_statement(b, l + 1);
    if (!r) r = case_label(b, l + 1);
    if (!r) r = iteration_statement(b, l + 1);
    if (!r) r = jump_statement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // fully_specified_type (IDENTIFIER array_specifier? (OP_ASSIGN initializer)?)?
  public static boolean single_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SINGLE_DECLARATION, "<single declaration>");
    r = fully_specified_type(b, l + 1);
    r = r && single_declaration_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (IDENTIFIER array_specifier? (OP_ASSIGN initializer)?)?
  private static boolean single_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_declaration_1")) return false;
    single_declaration_1_0(b, l + 1);
    return true;
  }

  // IDENTIFIER array_specifier? (OP_ASSIGN initializer)?
  private static boolean single_declaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_declaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && single_declaration_1_0_1(b, l + 1);
    r = r && single_declaration_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // array_specifier?
  private static boolean single_declaration_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_declaration_1_0_1")) return false;
    array_specifier(b, l + 1);
    return true;
  }

  // (OP_ASSIGN initializer)?
  private static boolean single_declaration_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_declaration_1_0_2")) return false;
    single_declaration_1_0_2_0(b, l + 1);
    return true;
  }

  // OP_ASSIGN initializer
  private static boolean single_declaration_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_declaration_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OP_ASSIGN);
    r = r && initializer(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // storage_qualifier|layout_qualifer|precision_qualifier|interpolation_qualifier|invariant_qualifier|precise_qualifier
  public static boolean single_type_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_type_qualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SINGLE_TYPE_QUALIFIER, "<single type qualifier>");
    r = storage_qualifier(b, l + 1);
    if (!r) r = layout_qualifer(b, l + 1);
    if (!r) r = precision_qualifier(b, l + 1);
    if (!r) r = interpolation_qualifier(b, l + 1);
    if (!r) r = invariant_qualifier(b, l + 1);
    if (!r) r = precise_qualifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // compound_statement|simple_statement
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = compound_statement(b, l + 1);
    if (!r) r = simple_statement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // statement+
  public static boolean statement_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT_LIST, "<statement list>");
    r = statement(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statement_list", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // compound_statement_no_new_scope|simple_statement
  public static boolean statement_no_new_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_no_new_scope")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT_NO_NEW_SCOPE, "<statement no new scope>");
    r = compound_statement_no_new_scope(b, l + 1);
    if (!r) r = simple_statement(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // KEYWORD_ATTRIBUTE|KEYWORD_VARYING|KEYWORD_CONST|KEYWORD_INOUT|KEYWORD_IN|KEYWORD_OUT|KEYWORD_CENTROID|KEYWORD_PATCH|KEYWORD_SAMPLE|KEYWORD_UNIFORM|KEYWORD_BUFFER|KEYWORD_SHARED|KEYWORD_COHERENT|KEYWORD_VOLATILE|KEYWORD_RESTRICT|KEYWORD_READONLY|KEYWORD_WRITEONLY|KEYWORD_SUBROUTINE|(KEYWORD_SUBROUTINE LPAREN type_name_list RPAREN)
  public static boolean storage_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "storage_qualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STORAGE_QUALIFIER, "<storage qualifier>");
    r = consumeToken(b, KEYWORD_ATTRIBUTE);
    if (!r) r = consumeToken(b, KEYWORD_VARYING);
    if (!r) r = consumeToken(b, KEYWORD_CONST);
    if (!r) r = consumeToken(b, KEYWORD_INOUT);
    if (!r) r = consumeToken(b, KEYWORD_IN);
    if (!r) r = consumeToken(b, KEYWORD_OUT);
    if (!r) r = consumeToken(b, KEYWORD_CENTROID);
    if (!r) r = consumeToken(b, KEYWORD_PATCH);
    if (!r) r = consumeToken(b, KEYWORD_SAMPLE);
    if (!r) r = consumeToken(b, KEYWORD_UNIFORM);
    if (!r) r = consumeToken(b, KEYWORD_BUFFER);
    if (!r) r = consumeToken(b, KEYWORD_SHARED);
    if (!r) r = consumeToken(b, KEYWORD_COHERENT);
    if (!r) r = consumeToken(b, KEYWORD_VOLATILE);
    if (!r) r = consumeToken(b, KEYWORD_RESTRICT);
    if (!r) r = consumeToken(b, KEYWORD_READONLY);
    if (!r) r = consumeToken(b, KEYWORD_WRITEONLY);
    if (!r) r = consumeToken(b, KEYWORD_SUBROUTINE);
    if (!r) r = storage_qualifier_18(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // KEYWORD_SUBROUTINE LPAREN type_name_list RPAREN
  private static boolean storage_qualifier_18(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "storage_qualifier_18")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_SUBROUTINE, LPAREN);
    r = r && type_name_list(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // type_qualifier? type_specifier LCURLY struct_declarator_list RCURLY
  public static boolean struct_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCT_DECLARATION, "<struct declaration>");
    r = struct_declaration_0(b, l + 1);
    r = r && type_specifier(b, l + 1);
    r = r && consumeToken(b, LCURLY);
    r = r && struct_declarator_list(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // type_qualifier?
  private static boolean struct_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declaration_0")) return false;
    type_qualifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // struct_declaration+
  public static boolean struct_declaration_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declaration_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCT_DECLARATION_LIST, "<struct declaration list>");
    r = struct_declaration(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!struct_declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "struct_declaration_list", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER array_specifier?
  public static boolean struct_declarator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declarator")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && struct_declarator_1(b, l + 1);
    exit_section_(b, m, STRUCT_DECLARATOR, r);
    return r;
  }

  // array_specifier?
  private static boolean struct_declarator_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declarator_1")) return false;
    array_specifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // struct_declarator (COMMA struct_declarator)*
  public static boolean struct_declarator_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declarator_list")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = struct_declarator(b, l + 1);
    r = r && struct_declarator_list_1(b, l + 1);
    exit_section_(b, m, STRUCT_DECLARATOR_LIST, r);
    return r;
  }

  // (COMMA struct_declarator)*
  private static boolean struct_declarator_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declarator_list_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!struct_declarator_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "struct_declarator_list_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA struct_declarator
  private static boolean struct_declarator_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_declarator_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && struct_declarator(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TYPE_STRUCT IDENTIFIER? LCURLY struct_declaration_list RCURLY
  public static boolean struct_specifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_specifier")) return false;
    if (!nextTokenIs(b, TYPE_STRUCT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPE_STRUCT);
    r = r && struct_specifier_1(b, l + 1);
    r = r && consumeToken(b, LCURLY);
    r = r && struct_declaration_list(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, STRUCT_SPECIFIER, r);
    return r;
  }

  // IDENTIFIER?
  private static boolean struct_specifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_specifier_1")) return false;
    consumeToken(b, IDENTIFIER);
    return true;
  }

  /* ********************************************************** */
  // KEYWORD_SWITCH LPAREN expression RPAREN LCURLY switch_statement_list RCURLY
  public static boolean switch_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_statement")) return false;
    if (!nextTokenIs(b, KEYWORD_SWITCH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, KEYWORD_SWITCH, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeTokens(b, 0, RPAREN, LCURLY);
    r = r && switch_statement_list(b, l + 1);
    r = r && consumeToken(b, RCURLY);
    exit_section_(b, m, SWITCH_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // statement_list
  public static boolean switch_statement_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "switch_statement_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SWITCH_STATEMENT_LIST, "<switch statement list>");
    r = statement_list(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // external_declaration+
  public static boolean translation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "translation_unit")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TRANSLATION_UNIT, "<translation unit>");
    r = external_declaration(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!external_declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "translation_unit", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER (COMMA IDENTIFIER)*
  public static boolean type_name_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_name_list")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && type_name_list_1(b, l + 1);
    exit_section_(b, m, TYPE_NAME_LIST, r);
    return r;
  }

  // (COMMA IDENTIFIER)*
  private static boolean type_name_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_name_list_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!type_name_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_name_list_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA IDENTIFIER
  private static boolean type_name_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_name_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // single_type_qualifier+
  public static boolean type_qualifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_qualifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_QUALIFIER, "<type qualifier>");
    r = single_type_qualifier(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!single_type_qualifier(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_qualifier", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type_specifier_non_array array_specifier?
  public static boolean type_specifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_specifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_SPECIFIER, "<type specifier>");
    r = type_specifier_non_array(b, l + 1);
    r = r && type_specifier_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // array_specifier?
  private static boolean type_specifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_specifier_1")) return false;
    array_specifier(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // struct_specifier|TYPE_VOID|TYPE_FLOAT|TYPE_DOUBLE|TYPE_INT|TYPE_UINT|TYPE_BOOL|TYPE_VEC2|TYPE_VEC3|TYPE_VEC4|TYPE_DVEC2|TYPE_DVEC3|TYPE_DVEC4|TYPE_IVEC2|TYPE_IVEC3|TYPE_IVEC4|TYPE_BVEC2|TYPE_BVEC3|TYPE_BVEC4|TYPE_MAT2|TYPE_MAT3|TYPE_MAT4|TYPE_MAT2X2|TYPE_MAT2X3|TYPE_MAT2X4|TYPE_MAT3X2|TYPE_MAT3X3|TYPE_MAT3X4|TYPE_MAT4X2|TYPE_MAT4X3|TYPE_MAT4X4|TYPE_DMAT2|TYPE_DMAT3|TYPE_DMAT4|TYPE_DMAT2X2|TYPE_DMAT2X3|TYPE_DMAT2X4|TYPE_DMAT3X2|TYPE_DMAT3X3|TYPE_DMAT3X4|TYPE_DMAT4X2|TYPE_DMAT4X3|TYPE_DMAT4X4|TYPE_ATOMIC_UINT|TYPE_IMAGE1D|TYPE_IMAGE2D|TYPE_IMAGE3D|TYPE_IMAGE_CUBE|TYPE_IMAGE2D_RECT|TYPE_IMAGE1D_ARRAY|TYPE_IMAGE2D_ARRAY|TYPE_IMAGE_BUFFER|TYPE_IMAGE2D_MS|TYPE_IMAGE2D_MS_ARRAY|TYPE_IIMAGE1D|TYPE_IIMAGE2D|TYPE_IIMAGE3D|TYPE_IIMAGE_CUBE|TYPE_IIMAGE2D_RECT|TYPE_IIMAGE1D_ARRAY|TYPE_IIMAGE2D_ARRAY|TYPE_IIMAGE_BUFFER|TYPE_IIMAGE2D_MS|TYPE_IIMAGE2D_MS_ARRAY|TYPE_UIMAGE1D|TYPE_UIMAGE2D|TYPE_UIMAGE3D|TYPE_UIMAGE_CUBE|TYPE_UIMAGE2D_RECT|TYPE_UIMAGE1D_ARRAY|TYPE_UIMAGE2D_ARRAY|TYPE_UIMAGE_BUFFER|TYPE_UIMAGE2D_MS|TYPE_UIMAGE2D_MS_ARRAY|TYPE_SAMPLER1D|TYPE_SAMPLER2D|TYPE_SAMPLER3D|TYPE_SAMPLER_CUBE|TYPE_SAMPLER2D_RECT|TYPE_SAMPLER1D_ARRAY|TYPE_SAMPLER2D_ARRAY|TYPE_SAMPLER_BUFFER|TYPE_SAMPLER2D_MS|TYPE_SAMPLER2D_MS_ARRAY|TYPE_SAMPLER1D_SHADOW|TYPE_SAMPLER2D_SHADOW|TYPE_SAMPLER1D_ARRAY_SHADOW|TYPE_SAMPLER2D_ARRAY_SHADOW|TYPE_SAMPLER_CUBE_SHADOW|TYPE_SAMPLER_CUBE_ARRAY_SHADOW|TYPE_ISAMPLER1D|TYPE_ISAMPLER2D|TYPE_ISAMPLER3D|TYPE_ISAMPLER_CUBE|TYPE_ISAMPLER2D_RECT|TYPE_ISAMPLER1D_ARRAY|TYPE_ISAMPLER2D_ARRAY|TYPE_ISAMPLER_BUFFER|TYPE_ISAMPLER2D_MS|TYPE_ISAMPLER2D_MS_ARRAY|TYPE_USAMPLER1D|TYPE_USAMPLER2D|TYPE_USAMPLER3D|TYPE_USAMPLER_CUBE|TYPE_USAMPLER2D_RECT|TYPE_USAMPLER1D_ARRAY|TYPE_USAMPLER2D_ARRAY|TYPE_USAMPLER_BUFFER|TYPE_USAMPLER2D_MS|TYPE_USAMPLER2D_MS_ARRAY
  public static boolean type_specifier_non_array(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_specifier_non_array")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_SPECIFIER_NON_ARRAY, "<type specifier non array>");
    r = struct_specifier(b, l + 1);
    if (!r) r = consumeToken(b, TYPE_VOID);
    if (!r) r = consumeToken(b, TYPE_FLOAT);
    if (!r) r = consumeToken(b, TYPE_DOUBLE);
    if (!r) r = consumeToken(b, TYPE_INT);
    if (!r) r = consumeToken(b, TYPE_UINT);
    if (!r) r = consumeToken(b, TYPE_BOOL);
    if (!r) r = consumeToken(b, TYPE_VEC2);
    if (!r) r = consumeToken(b, TYPE_VEC3);
    if (!r) r = consumeToken(b, TYPE_VEC4);
    if (!r) r = consumeToken(b, TYPE_DVEC2);
    if (!r) r = consumeToken(b, TYPE_DVEC3);
    if (!r) r = consumeToken(b, TYPE_DVEC4);
    if (!r) r = consumeToken(b, TYPE_IVEC2);
    if (!r) r = consumeToken(b, TYPE_IVEC3);
    if (!r) r = consumeToken(b, TYPE_IVEC4);
    if (!r) r = consumeToken(b, TYPE_BVEC2);
    if (!r) r = consumeToken(b, TYPE_BVEC3);
    if (!r) r = consumeToken(b, TYPE_BVEC4);
    if (!r) r = consumeToken(b, TYPE_MAT2);
    if (!r) r = consumeToken(b, TYPE_MAT3);
    if (!r) r = consumeToken(b, TYPE_MAT4);
    if (!r) r = consumeToken(b, TYPE_MAT2X2);
    if (!r) r = consumeToken(b, TYPE_MAT2X3);
    if (!r) r = consumeToken(b, TYPE_MAT2X4);
    if (!r) r = consumeToken(b, TYPE_MAT3X2);
    if (!r) r = consumeToken(b, TYPE_MAT3X3);
    if (!r) r = consumeToken(b, TYPE_MAT3X4);
    if (!r) r = consumeToken(b, TYPE_MAT4X2);
    if (!r) r = consumeToken(b, TYPE_MAT4X3);
    if (!r) r = consumeToken(b, TYPE_MAT4X4);
    if (!r) r = consumeToken(b, TYPE_DMAT2);
    if (!r) r = consumeToken(b, TYPE_DMAT3);
    if (!r) r = consumeToken(b, TYPE_DMAT4);
    if (!r) r = consumeToken(b, TYPE_DMAT2X2);
    if (!r) r = consumeToken(b, TYPE_DMAT2X3);
    if (!r) r = consumeToken(b, TYPE_DMAT2X4);
    if (!r) r = consumeToken(b, TYPE_DMAT3X2);
    if (!r) r = consumeToken(b, TYPE_DMAT3X3);
    if (!r) r = consumeToken(b, TYPE_DMAT3X4);
    if (!r) r = consumeToken(b, TYPE_DMAT4X2);
    if (!r) r = consumeToken(b, TYPE_DMAT4X3);
    if (!r) r = consumeToken(b, TYPE_DMAT4X4);
    if (!r) r = consumeToken(b, TYPE_ATOMIC_UINT);
    if (!r) r = consumeToken(b, TYPE_IMAGE1D);
    if (!r) r = consumeToken(b, TYPE_IMAGE2D);
    if (!r) r = consumeToken(b, TYPE_IMAGE3D);
    if (!r) r = consumeToken(b, TYPE_IMAGE_CUBE);
    if (!r) r = consumeToken(b, TYPE_IMAGE2D_RECT);
    if (!r) r = consumeToken(b, TYPE_IMAGE1D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_IMAGE2D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_IMAGE_BUFFER);
    if (!r) r = consumeToken(b, TYPE_IMAGE2D_MS);
    if (!r) r = consumeToken(b, TYPE_IMAGE2D_MS_ARRAY);
    if (!r) r = consumeToken(b, TYPE_IIMAGE1D);
    if (!r) r = consumeToken(b, TYPE_IIMAGE2D);
    if (!r) r = consumeToken(b, TYPE_IIMAGE3D);
    if (!r) r = consumeToken(b, TYPE_IIMAGE_CUBE);
    if (!r) r = consumeToken(b, TYPE_IIMAGE2D_RECT);
    if (!r) r = consumeToken(b, TYPE_IIMAGE1D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_IIMAGE2D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_IIMAGE_BUFFER);
    if (!r) r = consumeToken(b, TYPE_IIMAGE2D_MS);
    if (!r) r = consumeToken(b, TYPE_IIMAGE2D_MS_ARRAY);
    if (!r) r = consumeToken(b, TYPE_UIMAGE1D);
    if (!r) r = consumeToken(b, TYPE_UIMAGE2D);
    if (!r) r = consumeToken(b, TYPE_UIMAGE3D);
    if (!r) r = consumeToken(b, TYPE_UIMAGE_CUBE);
    if (!r) r = consumeToken(b, TYPE_UIMAGE2D_RECT);
    if (!r) r = consumeToken(b, TYPE_UIMAGE1D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_UIMAGE2D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_UIMAGE_BUFFER);
    if (!r) r = consumeToken(b, TYPE_UIMAGE2D_MS);
    if (!r) r = consumeToken(b, TYPE_UIMAGE2D_MS_ARRAY);
    if (!r) r = consumeToken(b, TYPE_SAMPLER1D);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D);
    if (!r) r = consumeToken(b, TYPE_SAMPLER3D);
    if (!r) r = consumeToken(b, TYPE_SAMPLER_CUBE);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D_RECT);
    if (!r) r = consumeToken(b, TYPE_SAMPLER1D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_SAMPLER_BUFFER);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D_MS);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D_MS_ARRAY);
    if (!r) r = consumeToken(b, TYPE_SAMPLER1D_SHADOW);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D_SHADOW);
    if (!r) r = consumeToken(b, TYPE_SAMPLER1D_ARRAY_SHADOW);
    if (!r) r = consumeToken(b, TYPE_SAMPLER2D_ARRAY_SHADOW);
    if (!r) r = consumeToken(b, TYPE_SAMPLER_CUBE_SHADOW);
    if (!r) r = consumeToken(b, TYPE_SAMPLER_CUBE_ARRAY_SHADOW);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER1D);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER2D);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER3D);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER_CUBE);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER2D_RECT);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER1D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER2D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER_BUFFER);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER2D_MS);
    if (!r) r = consumeToken(b, TYPE_ISAMPLER2D_MS_ARRAY);
    if (!r) r = consumeToken(b, TYPE_USAMPLER1D);
    if (!r) r = consumeToken(b, TYPE_USAMPLER2D);
    if (!r) r = consumeToken(b, TYPE_USAMPLER3D);
    if (!r) r = consumeToken(b, TYPE_USAMPLER_CUBE);
    if (!r) r = consumeToken(b, TYPE_USAMPLER2D_RECT);
    if (!r) r = consumeToken(b, TYPE_USAMPLER1D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_USAMPLER2D_ARRAY);
    if (!r) r = consumeToken(b, TYPE_USAMPLER_BUFFER);
    if (!r) r = consumeToken(b, TYPE_USAMPLER2D_MS);
    if (!r) r = consumeToken(b, TYPE_USAMPLER2D_MS_ARRAY);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // OP_ADD|OP_SUB|OP_LOGIC_NOT|OP_NOT|OP_INC|OP_DEC
  public static boolean unary_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_operator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, UNARY_OPERATOR, "<unary operator>");
    r = consumeToken(b, OP_ADD);
    if (!r) r = consumeToken(b, OP_SUB);
    if (!r) r = consumeToken(b, OP_LOGIC_NOT);
    if (!r) r = consumeToken(b, OP_NOT);
    if (!r) r = consumeToken(b, OP_INC);
    if (!r) r = consumeToken(b, OP_DEC);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_CONCAT|RESERVED|PREPROCESSOR_DEFINED|COMMENT
  public static boolean unused_tokens__(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unused_tokens__")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, UNUSED_TOKENS__, "<unused tokens>");
    r = consumeToken(b, PREPROCESSOR_CONCAT);
    if (!r) r = consumeToken(b, RESERVED);
    if (!r) r = consumeToken(b, PREPROCESSOR_DEFINED);
    if (!r) r = consumeToken(b, COMMENT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BLOCK_START BLOCK_NAME version_info BLOCK_END BLOCK_NAME
  public static boolean version_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "version_block")) return false;
    if (!nextTokenIs(b, BLOCK_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, BLOCK_START, BLOCK_NAME);
    r = r && version_info(b, l + 1);
    r = r && consumeTokens(b, 0, BLOCK_END, BLOCK_NAME);
    exit_section_(b, m, VERSION_BLOCK, r);
    return r;
  }

  /* ********************************************************** */
  // GLSL_VERSION GLSL_PROFILE?
  public static boolean version_info(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "version_info")) return false;
    if (!nextTokenIs(b, GLSL_VERSION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, GLSL_VERSION);
    r = r && version_info_1(b, l + 1);
    exit_section_(b, m, VERSION_INFO, r);
    return r;
  }

  // GLSL_PROFILE?
  private static boolean version_info_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "version_info_1")) return false;
    consumeToken(b, GLSL_PROFILE);
    return true;
  }

  /* ********************************************************** */
  // Expression root: expr
  // Operator priority table:
  // 0: BINARY(assignment_expr)
  // 1: BINARY(conditional_expr)
  // 2: BINARY(logic_or_expr)
  // 3: BINARY(logical_xor_expr)
  // 4: BINARY(logic_and_expr)
  // 5: BINARY(or_expr)
  // 6: BINARY(xor_expr)
  // 7: BINARY(and_expr)
  // 8: BINARY(equality_expr)
  // 9: BINARY(relational_expr)
  // 10: BINARY(shift_expr)
  // 11: BINARY(add_expr)
  // 12: BINARY(mul_expr)
  // 13: PREFIX(unary_expr)
  // 14: BINARY(postfix1_expr) ATOM(postfix2_expr) POSTFIX(postfix3_expr) POSTFIX(postfix4_expr)
  //    POSTFIX(postfix5_expr) POSTFIX(postfix6_expr)
  // 15: ATOM(primary1_expr) ATOM(primary2_expr) ATOM(primary3_expr) ATOM(primary4_expr)
  //    ATOM(primary5_expr) ATOM(primary6_expr) PREFIX(primary7_expr)
  public static boolean expr(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr")) return false;
    addVariant(b, "<expr>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expr>");
    r = unary_expr(b, l + 1);
    if (!r) r = postfix2_expr(b, l + 1);
    if (!r) r = primary1_expr(b, l + 1);
    if (!r) r = primary2_expr(b, l + 1);
    if (!r) r = primary3_expr(b, l + 1);
    if (!r) r = primary4_expr(b, l + 1);
    if (!r) r = primary5_expr(b, l + 1);
    if (!r) r = primary6_expr(b, l + 1);
    if (!r) r = primary7_expr(b, l + 1);
    p = r;
    r = r && expr_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expr_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && assignment_op(b, l + 1)) {
        r = expr(b, l, 0);
        exit_section_(b, l, m, ASSIGNMENT_EXPR, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, QUESTION)) {
        r = report_error_(b, expr(b, l, 1));
        r = conditional_expr_1(b, l + 1) && r;
        exit_section_(b, l, m, CONDITIONAL_EXPR, r, true, null);
      }
      else if (g < 2 && consumeTokenSmart(b, OP_LOGIC_OR)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, LOGIC_OR_EXPR, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, OP_LOGIC_XOR)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, LOGICAL_XOR_EXPR, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, OP_LOGIC_AND)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, LOGIC_AND_EXPR, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, OP_OR)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, OR_EXPR, r, true, null);
      }
      else if (g < 6 && consumeTokenSmart(b, OP_XOR)) {
        r = expr(b, l, 6);
        exit_section_(b, l, m, XOR_EXPR, r, true, null);
      }
      else if (g < 7 && consumeTokenSmart(b, OP_AND)) {
        r = expr(b, l, 7);
        exit_section_(b, l, m, AND_EXPR, r, true, null);
      }
      else if (g < 8 && equality_op(b, l + 1)) {
        r = expr(b, l, 8);
        exit_section_(b, l, m, EQUALITY_EXPR, r, true, null);
      }
      else if (g < 9 && relational_op(b, l + 1)) {
        r = expr(b, l, 9);
        exit_section_(b, l, m, RELATIONAL_EXPR, r, true, null);
      }
      else if (g < 10 && shift_op(b, l + 1)) {
        r = expr(b, l, 10);
        exit_section_(b, l, m, SHIFT_EXPR, r, true, null);
      }
      else if (g < 11 && add_op(b, l + 1)) {
        r = expr(b, l, 11);
        exit_section_(b, l, m, ADD_EXPR, r, true, null);
      }
      else if (g < 12 && mul_op(b, l + 1)) {
        r = expr(b, l, 12);
        exit_section_(b, l, m, MUL_EXPR, r, true, null);
      }
      else if (g < 14 && consumeTokenSmart(b, LSQUARE)) {
        r = report_error_(b, expr(b, l, 14));
        r = consumeToken(b, RSQUARE) && r;
        exit_section_(b, l, m, POSTFIX_1_EXPR, r, true, null);
      }
      else if (g < 14 && postfix3_expr_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, POSTFIX_3_EXPR, r, true, null);
      }
      else if (g < 14 && consumeTokenSmart(b, OP_INC)) {
        r = true;
        exit_section_(b, l, m, POSTFIX_4_EXPR, r, true, null);
      }
      else if (g < 14 && consumeTokenSmart(b, OP_DEC)) {
        r = true;
        exit_section_(b, l, m, POSTFIX_5_EXPR, r, true, null);
      }
      else if (g < 14 && function_call(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, POSTFIX_6_EXPR, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // COLON expr
  private static boolean conditional_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional_expr_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = unary_operator(b, l + 1);
    p = r;
    r = p && expr(b, l, 13);
    exit_section_(b, l, m, UNARY_EXPR, r, p, null);
    return r || p;
  }

  // type_specifier function_call
  public static boolean postfix2_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postfix2_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, POSTFIX_2_EXPR, "<postfix 2 expr>");
    r = type_specifier(b, l + 1);
    r = r && function_call(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // DOT field_selection
  private static boolean postfix3_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "postfix3_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, DOT);
    r = r && field_selection(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // IDENTIFIER
  public static boolean primary1_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary1_expr")) return false;
    if (!nextTokenIsSmart(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, IDENTIFIER);
    exit_section_(b, m, PRIMARY_1_EXPR, r);
    return r;
  }

  // INT
  public static boolean primary2_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary2_expr")) return false;
    if (!nextTokenIsSmart(b, INT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, INT);
    exit_section_(b, m, PRIMARY_2_EXPR, r);
    return r;
  }

  // UINT
  public static boolean primary3_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary3_expr")) return false;
    if (!nextTokenIsSmart(b, UINT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, UINT);
    exit_section_(b, m, PRIMARY_3_EXPR, r);
    return r;
  }

  // FLOAT
  public static boolean primary4_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary4_expr")) return false;
    if (!nextTokenIsSmart(b, FLOAT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, FLOAT);
    exit_section_(b, m, PRIMARY_4_EXPR, r);
    return r;
  }

  // BOOL
  public static boolean primary5_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary5_expr")) return false;
    if (!nextTokenIsSmart(b, BOOL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, BOOL);
    exit_section_(b, m, PRIMARY_5_EXPR, r);
    return r;
  }

  // DOUBLE
  public static boolean primary6_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary6_expr")) return false;
    if (!nextTokenIsSmart(b, DOUBLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, DOUBLE);
    exit_section_(b, m, PRIMARY_6_EXPR, r);
    return r;
  }

  public static boolean primary7_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary7_expr")) return false;
    if (!nextTokenIsSmart(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r;
    r = p && expr(b, l, -1);
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    exit_section_(b, l, m, PRIMARY_7_EXPR, r, p, null);
    return r || p;
  }

  final static Parser block_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return block_recover(b, l + 1);
    }
  };
}
