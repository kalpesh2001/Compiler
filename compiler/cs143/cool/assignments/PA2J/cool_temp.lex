/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%line
%state STRING
whitespace = [ \n\f\r\t\v]
TYPE_ID = [A-Z][A-Za-z0-9_]*
OBJECT_ID = [a-z][A-Za-z0-9_]*
INTEGER = [0-9]+

%%
<YYINITIAL>
"--"[^\n]*[\n]?			{System.out.println("Matched comment"+ yytext());}
<YYINITIAL> 
	"=>"			{
                                  return new Symbol(TokenConstants.DARROW); }
<YYINITIAL> 
	":"			{ return new Symbol(TokenConstants.COLON);}
<YYINITIAL>
	"<-"			{return new Symbol(TokenConstants.ASSIGN);}
<YYINITIAL> 
	"@"			{
                                  return new Symbol(TokenConstants.AT); }
<YYINITIAL>
	")"			{return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>
	"("			{return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>
	"}"			{return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>
	"{"			{return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>
	"."			{return new Symbol(TokenConstants.DOT); }
<YYINITIAL>
	";"			{return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>
	","			{return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>
	"*"			{return new Symbol(TokenConstants.MULT); }
<YYINITIAL>
	"/"			{return new Symbol(TokenConstants.DIV); }
<YYINITIAL>
	"+"			{return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>
	"-"			{return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>
	"="			{return new Symbol(TokenConstants.EQ); }
<YYINITIAL>
	"<"			{return new Symbol(TokenConstants.LT); }
<YYINITIAL>
	"<="			{return new Symbol(TokenConstants.LE); }
<YYINITIAL>
	"~"			{return new Symbol(TokenConstants.NEG); }
<YYINITIAL>
	[cC][lL][aA][sS][sS]	{return new Symbol(TokenConstants.CLASS); }
<YYINITIAL>
	[eE][lL][sS][eE]	{return new Symbol(TokenConstants.ELSE);}
<YYINITIAL>
	[f][aA][lL][sS][eE]	{return new Symbol(TokenConstants.BOOL_CONST,new Boolean(false));}
<YYINITIAL>
	[fF][iI]		{return new Symbol(TokenConstants.FI);}
<YYINITIAL>
	[Ii][fF]		{return new Symbol(TokenConstants.IF);}
<YYINITIAL>
	[Ii][nN][hH][eE][rR][iI][tT][sS]		{return new Symbol(TokenConstants.INHERITS);}
<YYINITIAL>
	[Ii][sS][vV][oO][iI][dD]		{return new Symbol(TokenConstants.ISVOID);}
<YYINITIAL>
	[lL][oO][oO][pP]		{return new Symbol(TokenConstants.LOOP);}
<YYINITIAL>
	[pP][oO][oO][lL]		{return new Symbol(TokenConstants.POOL);}
<YYINITIAL>
	[tT][hH][eE][nN]			{return new Symbol(TokenConstants.THEN);}
<YYINITIAL>
	[wW][hH][iI][lL][eE]			{return new Symbol(TokenConstants.WHILE);}
<YYINITIAL>
	[cC][aA][sS][eE]			{return new Symbol(TokenConstants.CASE);}
<YYINITIAL>
	[eE][sS][aA][cC]			{return new Symbol(TokenConstants.ESAC);}
<YYINITIAL>
	[nN][eE][wW]			{return new Symbol(TokenConstants.NEW);}
<YYINITIAL>
	[oO][fF]			{return new Symbol(TokenConstants.OF);}
<YYINITIAL>
	[nN][oO][tT]			{return new Symbol(TokenConstants.NOT);}
<YYINITIAL>
	[lL][eE][tT]			{return new Symbol(TokenConstants.LET_STMT);}
<YYINITIAL>
	[t][rR][uU][eE]			{return new Symbol(TokenConstants.BOOL_CONST,new Boolean(true));}
<YYINITIAL>
	{whitespace}			{}			

<YYINITIAL>
	{INTEGER}			{	System.out.println("Integer const: "+ yytext());
						return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext()));}
<YYINITIAL>
	{TYPE_ID}			{	System.out.println("new type : "+ yytext());
						return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
<YYINITIAL>
	{OBJECT_ID}			{return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
<YYINITIAL>
	\"				{string_buf.setLength(0);yybegin(STRING);}
/*
<STRING>
	"\"[^]				{string_buf.append(yycharat(1);}
<STRING>
	[^\n\b\t\f\"]+			{string_buf.append(yytext());}	
<STRING>
	\"				{yybegin(YYINITIAL); }
*/
[^]                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
