lexer grammar JavadocLexer;

@lexer::header {
import java.util.*;
}

@lexer::members {
      boolean recognizeXmlTags = true;
      boolean isJavadocTagAvailable = true;
      int insideJavadocInlineTag = 0;
      boolean insidePreTag = false;
      boolean referenceCatched = false;

      boolean insideReferenceArguments = false;

      int previousTokenType = 0;
      int previousToPreviousTokenType = 0;

      public void emit(Token token) {
            super.emit(token);
            previousToPreviousTokenType = previousTokenType;
            previousTokenType = token.getType();

            if (previousTokenType == NEWLINE) {
                  isJavadocTagAvailable = true;
            } else if (previousTokenType != WS && previousTokenType != LEADING_ASTERISK) {
                  isJavadocTagAvailable = false;
            }
      }

}

LEADING_ASTERISK : ( (' '|'\t') {_tokenStartCharPositionInLine == 0}? ) (' '|'\t')* '*'
      | '*' {_tokenStartCharPositionInLine == 0}?
      ;

HTML_COMMENT_START : '<!--' {recognizeXmlTags}?
      -> pushMode(htmlComment)
      ;
CDATA       :   '<![CDATA[' .*? ']]>' {recognizeXmlTags}?;

WS      :   (' '|'\t')+ ;

OPEN: '<' {recognizeXmlTags && (Character.isLetter(_input.LA(1)) || _input.LA(1) == '/')}?
      -> pushMode(xmlTagDefinition)
      ;

//PRE_TAG_OPEN: ('<pre>' | '<PRE>') {!insidePreTag}?
//     {insidePreTag=true; recognizeXmlTags=false;}
//      ;
//PRE_TAG_CLOSE: ('</pre>' | '</PRE>') {insidePreTag}?
//      {insidePreTag=false; recognizeXmlTags=true;}
//      ;

NEWLINE: '\n';

JAVADOC_TAG_AUTHOR : '@author' {isJavadocTagAvailable}?;
JAVADOC_TAG_DEPRECATED : '@deprecated' {isJavadocTagAvailable}?;
JAVADOC_TAG_EXCEPTION : '@exception' {isJavadocTagAvailable}? -> pushMode(exception);
JAVADOC_TAG_PARAM : '@param' {isJavadocTagAvailable}? -> pushMode(param);
JAVADOC_TAG_RETURN : '@return' {isJavadocTagAvailable}?; 
JAVADOC_TAG_SEE : '@see' {isJavadocTagAvailable}? -> pushMode(see);
JAVADOC_TAG_SERIAL : '@serial' {isJavadocTagAvailable}?;
JAVADOC_TAG_SERIAL_FIELD : '@serialField' {isJavadocTagAvailable}? -> pushMode(serialField);
JAVADOC_TAG_SERIAL_DATA : '@serialData' {isJavadocTagAvailable}?;
JAVADOC_TAG_SINCE : '@since' {isJavadocTagAvailable}?;
JAVADOC_TAG_THROWS : '@throws' {isJavadocTagAvailable}? -> pushMode(exception);
JAVADOC_TAG_VERSION : '@version' {isJavadocTagAvailable}?;

JAVADOC_INLINE_TAG_START: '{' {_input.LA(1) == '@'}? {insideJavadocInlineTag++;} -> pushMode(javadocInlineTag);

JAVADOC_INLINE_TAG_END: '}' {insideJavadocInlineTag>0}?
      {insideJavadocInlineTag--; recognizeXmlTags=true;}
      ;

JAVADOC_TAG_CUSTOM: '@' [a-zA-Z0-9]+ {isJavadocTagAvailable}?;

LITERAL_INCLUDE: 'include' {previousToPreviousTokenType==JAVADOC_TAG_SERIAL}?;
LITERAL_EXCLUDE: 'exclude' {previousToPreviousTokenType==JAVADOC_TAG_SERIAL}?;

CHAR        :   . ;

//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC TAG MODES  ///////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
mode param;
Space0: WS -> type(WS);
PARAMETER_NAME: [a-zA-Z0-9<>_-]+ -> mode(DEFAULT_MODE);
Char1: . -> type(CHAR), mode(DEFAULT_MODE);

mode see;
Space1: WS
      {
            if (referenceCatched) {
                  _mode = DEFAULT_MODE;
                  referenceCatched = false;
            }
      }
      -> type(WS);
Newline5: NEWLINE
      {
            if (referenceCatched) {
                  _mode = DEFAULT_MODE;
                  referenceCatched = false;
            }
      }
      -> type(NEWLINE);
XmlTagOpen1: '<' -> type(OPEN), pushMode(xmlTagDefinition);
STRING: '"' .*? '"' {referenceCatched = true;} -> mode(DEFAULT_MODE);
PACKAGE: [a-z] ([a-z_-] | '.')+ [a-z_-] {referenceCatched = true;};
DOT: '.';
HASH: '#' {referenceCatched = true;} -> mode(classMemeber);
CLASS: [A-Z] [a-zA-Z0-9_-]* {referenceCatched = true;};
End20: JAVADOC_INLINE_TAG_END
      {
            insideJavadocInlineTag--;
            recognizeXmlTags=true;
            referenceCatched = false;
      }
      -> type(JAVADOC_INLINE_TAG_END), mode(DEFAULT_MODE)
      ;
Char2: . 
      {
            referenceCatched = false;
      }
      -> type(CHAR), mode(DEFAULT_MODE);

mode classMemeber;
MEMBER: [a-zA-Z0-9_-]+ {!insideReferenceArguments}?;
LEFT_BRACE: '(' {insideReferenceArguments=true;};
RIGHT_BRACE: ')' {insideReferenceArguments=false;};
ARGUMENT: ([a-zA-Z0-9_-] | '.' | '[' | ']')+ {insideReferenceArguments}?;
COMMA: ',' {insideReferenceArguments}?;
Space20: WS
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
            }
      }  -> type(WS);
End2: JAVADOC_INLINE_TAG_END
      {
            insideJavadocInlineTag--;
            recognizeXmlTags=true;
            referenceCatched = false;
            insideReferenceArguments = false;
      }
      -> type(JAVADOC_INLINE_TAG_END), mode(DEFAULT_MODE)
      ;
Char20: . 
      {
            referenceCatched = false;
            insideReferenceArguments = false;
      }
      -> type(CHAR), mode(DEFAULT_MODE);

mode serialField;
Space2: WS -> type(WS);
FIELD_NAME: [a-zA-Z0-9_-]+ -> mode(serialFieldFieldType);
Char3: . -> type(CHAR), mode(DEFAULT_MODE);

mode serialFieldFieldType;
Space3: WS -> type(WS);
FIELD_TYPE: [a-zA-Z0-9_-]+ -> mode(DEFAULT_MODE);
Char4: . -> type(CHAR), mode(DEFAULT_MODE);

mode exception;
Space4: WS -> type(WS);
CLASS_NAME: ([a-zA-Z0-9_-] | '.')+ -> mode(DEFAULT_MODE);
Char5: . -> type(CHAR), mode(DEFAULT_MODE);


//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC INLINE TAG MODES  ////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
mode javadocInlineTag;
JAVADOC_INLINE_TAG_CODE : '@code' {recognizeXmlTags=false;} -> mode(code);
JAVADOC_INLINE_TAG_DOC_ROOT : '@docRoot' -> mode(DEFAULT_MODE);
JAVADOC_INLINE_TAG_INHERIT_DOC : '@inheritDoc' -> mode(DEFAULT_MODE);
JAVADOC_INLINE_TAG_LINK : '@link' -> pushMode(see);
JAVADOC_INLINE_TAG_LINKPLAIN : '@linkplain' -> pushMode(see);
JAVADOC_INLINE_TAG_LITERAL : '@literal' {recognizeXmlTags=false;} -> mode(code);
JAVADOC_INLINE_TAG_VALUE : '@value' -> pushMode(value);
JAVADOC_INLINE_TAG_CUSTOM: '@' [a-zA-Z0-9]+ -> mode(DEFAULT_MODE);
Char6: . -> type(CHAR), mode(DEFAULT_MODE);

mode code;
Space7: WS -> type(WS), mode(codeText);
Newline2: NEWLINE -> type(NEWLINE), mode(codeText);
Char7: . -> type(CHAR), mode(DEFAULT_MODE);

mode codeText;
Text: (
      '{' .*? '}'
      | ~[}]
      )+ -> type(CHAR), mode(DEFAULT_MODE);
Char8: . -> type(CHAR), mode(DEFAULT_MODE);

mode link;
Space5: ' ' -> type(WS);
Newline3: NEWLINE -> type(NEWLINE);
LeadingLEADING_ASTERISK2: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Package1: PACKAGE -> type(PACKAGE);
Dot1: DOT -> type(DOT);
Class1: CLASS -> type(CLASS);
Hash1: HASH -> type(HASH), mode(classMemeber);
Char9: . -> type(CHAR), mode(DEFAULT_MODE);

mode value;
Space6: WS -> type(WS);
Newline4: NEWLINE -> type(NEWLINE);
Package2: PACKAGE -> type(PACKAGE);
Dot2: DOT -> type(DOT);
Class2: CLASS -> type(CLASS);
Hash2: HASH -> type(HASH), mode(classMemeber);
End1: JAVADOC_INLINE_TAG_END
      {insideJavadocInlineTag--; recognizeXmlTags=true;}
      -> type(JAVADOC_INLINE_TAG_END), mode(DEFAULT_MODE)
      ;
Char10: . -> type(CHAR), mode(DEFAULT_MODE);


//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  HTML TAG MODES  //////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
mode xmlTagDefinition;

CLOSE       :   '>'                     -> mode(DEFAULT_MODE) ;
SLASH_CLOSE :   '/>'                    -> mode(DEFAULT_MODE) ;
SLASH       :   '/' ;
EQUALS      :   '=' ;

ATTR_VALUE  : '"' ~[<"]* '"'
            | '\'' ~[<']* '\''
            | DIGIT+
            ;

// with optional end tag
P_NAME: 'P' | 'p';
LI_NAME: 'li' | 'LI';
TR_NAME: 'tr' | 'TR';
TD_NAME: 'td' | 'TD';
TH_NAME: 'th' | 'TH';
BODY_NAME: 'body' | 'BODY';
COLGROUP_NAME: 'colgroup' | 'COLGROUP';
DD_NAME: 'dd' | 'DD';
DT_NAME: 'dt' | 'DT';
HEAD_NAME: 'head' | 'HEAD';
HTML_NAME: 'html' | 'HTML';
OPTION_NAME: 'option' | 'OPTION';
TBODY_NAME: 'tbody' | 'TBODY';
TFOOT_NAME: 'tfoot' | 'TFOOT';
THEAD_NAME: 'thead' | 'THEAD';

// singleton tags
AREA_NAME: 'area' | 'AREA';
BASE_NAME: 'base' | 'BASE';
BASEFRONT_NAME: 'basefront' | 'BASEFRONT';
BR_NAME: 'br' | 'BR';
COL_NAME: 'col' | 'COL';
FRAME_NAME: 'frame' | 'FRAME';
HR_NAME: 'hr' | 'HR';
IMG_NAME: 'img' | 'IMG';
INPUT_NAME: 'input' | 'INPUT';
ISINDEX_NAME: 'isindex' | 'ISINDEX';
LINK_NAME: 'link' | 'LINK';
META_NAME: 'meta' | 'META';
PARAM_NAME: 'param' | 'PARAM';

// other tags
NAME        :   NAME_START_CHAR NAME_CHAR*;

LeadingLEADING_ASTERISK1: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Newline1: NEWLINE -> type(NEWLINE);

S           :   [ \t\r\n]               -> skip ;

Char11: . -> type(CHAR), mode(DEFAULT_MODE);

fragment
HEXDIGIT    :   [a-fA-F0-9] ;

fragment
DIGIT       :   [0-9] ;

fragment
NAME_CHAR    :   NAME_START_CHAR
            |   '-' | '_' | '.' | DIGIT 
            |   '\u00B7'
            |   '\u0300'..'\u036F'
            |   '\u203F'..'\u2040'
            ;

fragment
NAME_START_CHAR
            :   [:a-zA-Z]
            |   '\u2070'..'\u218F' 
            |   '\u2C00'..'\u2FEF' 
            |   '\u3001'..'\uD7FF' 
            |   '\uF900'..'\uFDCF' 
            |   '\uFDF0'..'\uFFFD'
            ;

fragment
FragmentReference: ([a-zA-Z0-9_-] | '.')+
      | ([a-zA-Z0-9_-] | '.')* '#' [a-zA-Z0-9_-]+ ( '(' (([a-zA-Z0-9_-] | '.')+ | ',' | ' ')* ')' )?
      ;

mode htmlComment;
HTML_COMMENT_END: '-->' -> mode(DEFAULT_MODE);
LeadingAst: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Newline6: NEWLINE -> type(NEWLINE);
WhiteSpace: WS -> type(WS);
CommentChar: . -> type(CHAR);