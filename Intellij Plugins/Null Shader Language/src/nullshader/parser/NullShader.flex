package nullshader.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static nullshader.parser.psi.NullShaderTypes.*;
import com.intellij.psi.TokenType;

%%

%class NullShaderLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF1 = (\n|\r|\r\n)
CRLF = \\?{CRLF1}

WHITE_SPACE = [\ \t\f]


DIGIT = [0-9]
OCT_DIGIT = [0-7]
HEX_DIGIT = [0-9A-Fa-f]

INTEGER = ({DEC_CONST}|{HEX_CONST}|{OCT_CONST})
DEC_CONST = (0|([1-9]{DIGIT}*))
HEX_CONST = 0[Xx]{HEX_DIGIT}*
OCT_CONST = 0{OCT_DIGIT}*

UINT_SUFFIX = [Uu]
DOUBLE_SUFFIX = (lf|LF)
FLOAT_SUFFIX = [Ff]

FLOAT = ({FLOAT1}|{FLOAT2}|{FLOAT3})
FLOAT1 = {DIGIT}+"."{DIGIT}*{EXPONENT}?
FLOAT2 = "."{DIGIT}*{EXPONENT}?
FLOAT3 = {DIGIT}+{EXPONENT}?
EXPONENT = [Ee]["+""-"]?{DIGIT}*

NONDIGIT = [_a-zA-Z]
IDENTIFIER = {NONDIGIT} ({DIGIT}|{NONDIGIT})*

VERSIONS = (100|110|120|130|140|150|300|310|320|330|400|410|420|430|440|450)
PROFILES = (es|core|compatibility)

LINE_COMMENT = "//"[^\r\n]*
BLOCK_COMMENT = ("/*"([^"*"]|("*"+[^"*""/"]))*("*"+"/")?)

%state S_PREPROCESSOR
%state S_PREPROCESSOR_VERSION
%state S_GLSL
%state S_VERSION
%state S_BLOCK_START
%state S_BLOCK_END

%%

<S_BLOCK_START> {
	[Vv][Ee][Rr][Ss][Ii][Oo][Nn] {yybegin(S_VERSION); return BLOCK_NAME;}
	{IDENTIFIER} {yybegin(S_GLSL); return BLOCK_NAME;}
	
	{CRLF} {yybegin(YYINITIAL); return TokenType.BAD_CHARACTER;}
	. {yybegin(YYINITIAL); return TokenType.BAD_CHARACTER;}
}

<S_BLOCK_END> {
	[Ee][Nn][Dd] {yybegin(YYINITIAL); return BLOCK_NAME;}
	
	{CRLF} {yybegin(YYINITIAL); return TokenType.BAD_CHARACTER;}
    	. {yybegin(YYINITIAL); return TokenType.BAD_CHARACTER;}
}

<S_GLSL> {
	({WHITE_SPACE}|{CRLF})+ {return TokenType.WHITE_SPACE;}
}

<S_GLSL, S_VERSION> {
	\$ {yybegin(S_BLOCK_END); return BLOCK_END;}
}

<S_PREPROCESSOR> {
	\"[^\"\r\n]*\" {return PREPROCESSOR_GLOBAL_FILE;}
	\<[^\<>\r\n]*> {return PREPROCESSOR_LOCAL_FILE;}
	
	include     {return PREPROCESSOR_INCLUDE;}
	define      {return PREPROCESSOR_DEFINE;}
	undef       {return PREPROCESSOR_UNDEF;}
	if          {return PREPROCESSOR_IF;}
	ifdef       {return PREPROCESSOR_IFDEF;}
	ifndef      {return PREPROCESSOR_IFNDEF;}
	else        {return PREPROCESSOR_ELSE;}
	elif        {return PREPROCESSOR_ELIF;}
	endif       {return PREPROCESSOR_ENDIF;}
	error       {return PREPROCESSOR_ERROR;}
	pragma      {return PREPROCESSOR_PRAGMA;}
	extension   {return PREPROCESSOR_EXTENSION;}
	version     {yybegin(S_PREPROCESSOR_VERSION); return PREPROCESSOR_VERSION;}
	line        {return PREPROCESSOR_LINE;}
	defined     {return PREPROCESSOR_DEFINED;}
	##          {return PREPROCESSOR_CONCAT;}
}

<S_PREPROCESSOR, S_PREPROCESSOR_VERSION> {
	{CRLF} {yybegin(S_GLSL); return PREPROCESSOR_END;}
    {WHITE_SPACE}+ {return TokenType.WHITE_SPACE;}
}

<S_VERSION> {
	({WHITE_SPACE}|{CRLF})+ {return TokenType.WHITE_SPACE;}
}

<S_PREPROCESSOR_VERSION, S_VERSION> {
	{VERSIONS} {return GLSL_VERSION;}
    {PROFILES} {return GLSL_PROFILE;}
    . {return TokenType.BAD_CHARACTER;}
}

<YYINITIAL> {
	\$ {yybegin(S_BLOCK_START); return BLOCK_START;}
	
	({WHITE_SPACE}|{CRLF})+ {return TokenType.WHITE_SPACE;}
}

<S_GLSL, YYINITIAL> {
	# {yybegin(S_PREPROCESSOR); return PREPROCESSOR_START;}
}

<S_GLSL, S_PREPROCESSOR, YYINITIAL> {

	true  {return BOOL;}
	false {return BOOL;}
	
	void                    {return TYPE_VOID;}
	float                   {return TYPE_FLOAT;}
	double                  {return TYPE_DOUBLE;}
	int                     {return TYPE_INT;}
	uint                    {return TYPE_UINT;}
	bool                    {return TYPE_BOOL;}
	vec2                    {return TYPE_VEC2;}
	vec3                    {return TYPE_VEC3;}
	vec4                    {return TYPE_VEC4;}
	dvec2                   {return TYPE_DVEC2;}
	dvec3                   {return TYPE_DVEC3;}
	dvec4                   {return TYPE_DVEC4;}
	ivec2                   {return TYPE_IVEC2;}
	ivec3                   {return TYPE_IVEC3;}
	ivec4                   {return TYPE_IVEC4;}
	bvec2                   {return TYPE_BVEC2;}
	bvec3                   {return TYPE_BVEC3;}
	bvec4                   {return TYPE_BVEC4;}
	mat2                    {return TYPE_MAT2;}
	mat3                    {return TYPE_MAT3;}
	mat4                    {return TYPE_MAT4;}
	mat2x2                  {return TYPE_MAT2X2;}
	mat2x3                  {return TYPE_MAT2X3;}
	mat2x4                  {return TYPE_MAT2X4;}
	mat3x2                  {return TYPE_MAT3X2;}
	mat3x3                  {return TYPE_MAT3X3;}
	mat3x4                  {return TYPE_MAT3X4;}
	mat4x2                  {return TYPE_MAT4X2;}
	mat4x3                  {return TYPE_MAT4X3;}
	mat4x4                  {return TYPE_MAT4X4;}
	dmat2                   {return TYPE_DMAT2;}
	dmat3                   {return TYPE_DMAT3;}
	dmat4                   {return TYPE_DMAT4;}
	dmat2x2                 {return TYPE_DMAT2X2;}
	dmat2x3                 {return TYPE_DMAT2X3;}
	dmat2x4                 {return TYPE_DMAT2X4;}
	dmat3x2                 {return TYPE_DMAT3X2;}
	dmat3x3                 {return TYPE_DMAT3X3;}
	dmat3x4                 {return TYPE_DMAT3X4;}
	dmat4x2                 {return TYPE_DMAT4X2;}
	dmat4x3                 {return TYPE_DMAT4X3;}
	dmat4x4                 {return TYPE_DMAT4X4;}
	atomic_uint             {return TYPE_ATOMIC_UINT;}
	
	struct                  {return TYPE_STRUCT;}
	
	sampler1D               {return TYPE_SAMPLER1D;}
	sampler2D               {return TYPE_SAMPLER2D;}
	sampler3D               {return TYPE_SAMPLER3D;}
	samplerCube             {return TYPE_SAMPLER_CUBE;}
	sampler2DRect           {return TYPE_SAMPLER2D_RECT;}
	sampler1DArray          {return TYPE_SAMPLER1D_ARRAY;}
	sampler2DArray          {return TYPE_SAMPLER2D_ARRAY;}
	samplerBuffer           {return TYPE_SAMPLER_BUFFER;}
	sampler2DMS             {return TYPE_SAMPLER2D_MS;}
	sampler2DMSArray        {return TYPE_SAMPLER2D_MS_ARRAY;}
	sampler1DShadow         {return TYPE_SAMPLER1D_SHADOW;}
	sampler2DShadow         {return TYPE_SAMPLER2D_SHADOW;}
	sampler1DArrayShadow    {return TYPE_SAMPLER1D_ARRAY_SHADOW;}
	sampler2DArrayShadow    {return TYPE_SAMPLER2D_ARRAY_SHADOW;}
	samplerCubeShadow       {return TYPE_SAMPLER_CUBE_SHADOW;}
	samplerCubeArrayShadow  {return TYPE_SAMPLER_CUBE_ARRAY_SHADOW;}
	isampler1D              {return TYPE_ISAMPLER1D;}
	isampler2D              {return TYPE_ISAMPLER2D;}
	isampler3D              {return TYPE_ISAMPLER3D;}
	isamplerCube            {return TYPE_ISAMPLER_CUBE;}
	isampler2DRect          {return TYPE_ISAMPLER2D_RECT;}
	isampler1DArray         {return TYPE_ISAMPLER1D_ARRAY;}
	isampler2DArray         {return TYPE_ISAMPLER2D_ARRAY;}
	isamplerBuffer          {return TYPE_ISAMPLER_BUFFER;}
	isampler2DMS            {return TYPE_ISAMPLER2D_MS;}
	isampler2DMSArray       {return TYPE_ISAMPLER2D_MS_ARRAY;}
	usampler1D              {return TYPE_USAMPLER1D;}
	usampler2D              {return TYPE_USAMPLER2D;}
	usampler3D              {return TYPE_USAMPLER3D;}
	usamplerCube            {return TYPE_USAMPLER_CUBE;}
	usampler2DRect          {return TYPE_USAMPLER2D_RECT;}
	usampler1DArray         {return TYPE_USAMPLER1D_ARRAY;}
	usampler2DArray         {return TYPE_USAMPLER2D_ARRAY;}
	usamplerBuffer          {return TYPE_USAMPLER_BUFFER;}
	usampler2DMS            {return TYPE_USAMPLER2D_MS;}
	usampler2DMSArray       {return TYPE_USAMPLER2D_MS_ARRAY;}
	
	image1D                 {return TYPE_IMAGE1D;}
	image2D                 {return TYPE_IMAGE2D;}
	image3D                 {return TYPE_IMAGE3D;}
	imageCube               {return TYPE_IMAGE_CUBE;}
	image2DRect             {return TYPE_IMAGE2D_RECT;}
	image1DArray            {return TYPE_IMAGE1D_ARRAY;}
	image2DArray            {return TYPE_IMAGE2D_ARRAY;}
	imageBuffer             {return TYPE_IMAGE_BUFFER;}
	image2DMS               {return TYPE_IMAGE2D_MS;}
	image2DMSArray          {return TYPE_IMAGE2D_MS_ARRAY;}
	iimage1D                {return TYPE_IIMAGE1D;}
	iimage2D                {return TYPE_IIMAGE2D;}
	iimage3D                {return TYPE_IIMAGE3D;}
	iimageCube              {return TYPE_IIMAGE_CUBE;}
	iimage2DRect            {return TYPE_IIMAGE2D_RECT;}
	iimage1DArray           {return TYPE_IIMAGE1D_ARRAY;}
	iimage2DArray           {return TYPE_IIMAGE2D_ARRAY;}
	iimageBuffer            {return TYPE_IIMAGE_BUFFER;}
	iimage2DMS              {return TYPE_IIMAGE2D_MS;}
	iimage2DMSArray         {return TYPE_IIMAGE2D_MS_ARRAY;}
	uimage1D                {return TYPE_UIMAGE1D;}
	uimage2D                {return TYPE_UIMAGE2D;}
	uimage3D                {return TYPE_UIMAGE3D;}
	uimageCube              {return TYPE_UIMAGE_CUBE;}
	uimage2DRect            {return TYPE_UIMAGE2D_RECT;}
	uimage1DArray           {return TYPE_UIMAGE1D_ARRAY;}
	uimage2DArray           {return TYPE_UIMAGE2D_ARRAY;}
	uimageBuffer            {return TYPE_UIMAGE_BUFFER;}
	uimage2DMS              {return TYPE_UIMAGE2D_MS;}
	uimage2DMSArray         {return TYPE_UIMAGE2D_MS_ARRAY;}
	
	const         {return KEYWORD_CONST;}
	attribute     {return KEYWORD_ATTRIBUTE;}
	uniform       {return KEYWORD_UNIFORM;}
	varying       {return KEYWORD_VARYING;}
	centroid      {return KEYWORD_CENTROID;}
	invariant     {return KEYWORD_INVARIANT;}
	patch         {return KEYWORD_PATCH;}
	sample        {return KEYWORD_SAMPLE;}
	buffer        {return KEYWORD_BUFFER;}
	shared        {return KEYWORD_SHARED;}
	coherent      {return KEYWORD_COHERENT;}
	volatile      {return KEYWORD_VOLATILE;}
	restrict      {return KEYWORD_RESTRICT;}
	readonly      {return KEYWORD_READONLY;}
	writeonly     {return KEYWORD_WRITEONLY;}
	subroutine    {return KEYWORD_SUBROUTINE;}
	precise       {return KEYWORD_PRECISE;}
	layout        {return KEYWORD_LAYOUT;}
	smooth        {return KEYWORD_SMOOTH;}
	flat          {return KEYWORD_FLAT;}
	noperspective {return KEYWORD_NOPERSPECTIVE;}
	highp         {return KEYWORD_HIGHP;}
	mediump       {return KEYWORD_MEDIUMP;}
	lowp          {return KEYWORD_LOWP;}
	in            {return KEYWORD_IN;}
	out           {return KEYWORD_OUT;}
	inout         {return KEYWORD_INOUT;}
	
	while         {return KEYWORD_WHILE;}
	do            {return KEYWORD_DO;}
	for           {return KEYWORD_FOR;}
	break         {return KEYWORD_BREAK;}
	continue      {return KEYWORD_CONTINUE;}
	return        {return KEYWORD_RETURN;}
	discard       {return KEYWORD_DISCARD;}
	if            {return KEYWORD_IF;}
	else          {return KEYWORD_ELSE;}
	switch        {return KEYWORD_SWITCH;}
	case          {return KEYWORD_CASE;}
	default       {return KEYWORD_DEFAULT;}
	
	precision     {return KEYWORD_PRECISION;}
	
	common        {return RESERVED;}
	partition     {return RESERVED;}
	active        {return RESERVED;}
	asm           {return RESERVED;}
	class         {return RESERVED;}
	union         {return RESERVED;}
	enum          {return RESERVED;}
	typedef       {return RESERVED;}
	template      {return RESERVED;}
	this          {return RESERVED;}
	resource      {return RESERVED;}
	goto          {return RESERVED;}
	inline        {return RESERVED;}
	noinline      {return RESERVED;}
	public        {return RESERVED;}
	static        {return RESERVED;}
	extern        {return RESERVED;}
	external      {return RESERVED;}
	interface     {return RESERVED;}
	long          {return RESERVED;}
	short         {return RESERVED;}
	half          {return RESERVED;}
	fixed         {return RESERVED;}
	unsigned      {return RESERVED;}
	superp        {return RESERVED;}
	input         {return RESERVED;}
	output        {return RESERVED;}
	[hf]vec[234]  {return RESERVED;}
	sampler3DRect {return RESERVED;}
	filter        {return RESERVED;}
	sizeof        {return RESERVED;}
	cast          {return RESERVED;}
	namespace     {return RESERVED;}
	using         {return RESERVED;}
	
	"{" {return LCURLY;}
	"}" {return RCURLY;}
	"[" {return LSQUARE;}
	"]" {return RSQUARE;}
	"(" {return LPAREN;}
	")" {return RPAREN;}
	
	"="   {return OP_ASSIGN;}
	"+="  {return OP_ADD_ASSIGN;}
	"-="  {return OP_SUB_ASSIGN;}
	"*="  {return OP_MUL_ASSIGN;}
	"/="  {return OP_DIV_ASSIGN;}
	"%="  {return OP_MOD_ASSIGN;}
	"<<=" {return OP_LSHIFT_ASSIGN;}
	">>=" {return OP_RSHIFT_ASSIGN;}
	"&="  {return OP_AND_ASSIGN;}
	"^="  {return OP_XOR_ASSIGN;}
	"|="  {return OP_OR_ASSIGN;}
	
	"++"  {return OP_INC;}
	"--"  {return OP_DEC;}
	
	"+"   {return OP_ADD;}
	"-"   {return OP_SUB;}
	"*"   {return OP_MUL;}
	"/"   {return OP_DIV;}
	"%"   {return OP_MOD;}
	"<<"  {return OP_LSHIFT;}
	">>"  {return OP_RSHIFT;}
	"&"   {return OP_AND;}
	"^"   {return OP_XOR;}
	"|"   {return OP_OR;}
	
	"<"   {return OP_LESS;}
	">"   {return OP_GREATER;}
	"<="   {return OP_LESS_EQUAL;}
	">="   {return OP_GREATER_EQUAL;}
	
	"!"   {return OP_LOGIC_NOT;}
	"~"   {return OP_NOT;}
	
	"&&"  {return OP_LOGIC_AND;}
	"||"  {return OP_LOGIC_OR;}
	"^^"  {return OP_LOGIC_XOR;}
	
	"=="  {return OP_EQUAL;}
	"!="  {return OP_NOT_EQUAL;}
	
	"."   {return DOT;}
	":"   {return COLON;}
	","   {return COMMA;}
	"?"   {return QUESTION;}
	";"   {return SEMICOLON;}
	
	{IDENTIFIER} {return IDENTIFIER;}
	{INTEGER}{UINT_SUFFIX} {return UINT;}
	{INTEGER} {return INT;}
	{FLOAT}{DOUBLE_SUFFIX} {return DOUBLE;}
	{FLOAT}{FLOAT_SUFFIX}? {return FLOAT;}
	{LINE_COMMENT}|{BLOCK_COMMENT} {return COMMENT;}
	
	. {return TokenType.BAD_CHARACTER;}
}
