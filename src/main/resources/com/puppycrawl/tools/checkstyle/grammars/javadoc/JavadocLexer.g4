lexer grammar JavadocLexer;

@lexer::header {
package com.puppycrawl.tools.checkstyle.grammars.javadoc;

import java.util.*;
}

@lexer::members {
      boolean recognizeXmlTags = true;
      boolean isJavadocTagAvailable = true;
      int insideJavadocInlineTag = 0;
      boolean insidePreTag = false;
      boolean referenceCatched = false;

      boolean insideReferenceArguments = false;

      boolean attributeCatched = false;

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

      public void skipCurrentTokenConsuming() {
            _input.seek(_input.index() - 1);
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

JAVADOC_TAG_AUTHOR_LITERAL : '@author' {isJavadocTagAvailable}?;
JAVADOC_TAG_DEPRECATED_LITERAL : '@deprecated' {isJavadocTagAvailable}?;
JAVADOC_TAG_EXCEPTION_LITERAL : '@exception' {isJavadocTagAvailable}? -> pushMode(exception);
JAVADOC_TAG_PARAM_LITERAL : '@param' {isJavadocTagAvailable}? -> pushMode(param);
JAVADOC_TAG_RETURN_LITERAL : '@return' {isJavadocTagAvailable}?; 
JAVADOC_TAG_SEE_LITERAL : '@see' {isJavadocTagAvailable}? -> pushMode(seeLink);
JAVADOC_TAG_SERIAL_LITERAL : '@serial' {isJavadocTagAvailable}?;
JAVADOC_TAG_SERIAL_FIELD_LITERAL : '@serialField' {isJavadocTagAvailable}? -> pushMode(serialField);
JAVADOC_TAG_SERIAL_DATA_LITERAL : '@serialData' {isJavadocTagAvailable}?;
JAVADOC_TAG_SINCE_LITERAL : '@since' {isJavadocTagAvailable}?;
JAVADOC_TAG_THROWS_LITERAL : '@throws' {isJavadocTagAvailable}? -> pushMode(exception);
JAVADOC_TAG_VERSION_LITERAL : '@version' {isJavadocTagAvailable}?;

JAVADOC_INLINE_TAG_START: '{' {_input.LA(1) == '@'}? {insideJavadocInlineTag++;} -> pushMode(javadocInlineTag);

JAVADOC_INLINE_TAG_END: '}' {insideJavadocInlineTag>0}?
      {insideJavadocInlineTag--; recognizeXmlTags=true;}
      ;

JAVADOC_TAG_CUSTOM_LITERAL: '@' [a-zA-Z0-9]+ {isJavadocTagAvailable}?;

LITERAL_INCLUDE: 'include' {previousToPreviousTokenType==JAVADOC_TAG_SERIAL_LITERAL}?;
LITERAL_EXCLUDE: 'exclude' {previousToPreviousTokenType==JAVADOC_TAG_SERIAL_LITERAL}?;

CHAR        :   . ;

//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC TAG MODES  ///////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
mode param;
Space0: WS -> type(WS);
PARAMETER_NAME: [a-zA-Z0-9<>_-]+ -> mode(DEFAULT_MODE);
Char1: . 
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);
//////////////////////////////////////////////////////////////////////////////////////
mode seeLink;
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
Leading_asterisk3: LEADING_ASTERISK -> type(LEADING_ASTERISK);
XmlTagOpen1: '<' -> type(OPEN), pushMode(xmlTagDefinition);
STRING: '"' .*? '"' {referenceCatched = false;} -> mode(DEFAULT_MODE);
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
// exit from 'seeLink' mode without consuming current character
Char2: . 
      {
            skipCurrentTokenConsuming();
            referenceCatched = false;
      } -> skip, mode(DEFAULT_MODE);

//////////////////////////////////////////////////////////////////////////////////////
mode classMemeber;
MEMBER: [a-zA-Z0-9_-]+ {!insideReferenceArguments}?;
LEFT_BRACE: '(' {insideReferenceArguments=true;};
RIGHT_BRACE: ')' {insideReferenceArguments=false;};
ARGUMENT: ([a-zA-Z0-9_-] | '.' | '[' | ']')+ {insideReferenceArguments}?;
COMMA: ',' {insideReferenceArguments}?;
Leading_asterisk6: LEADING_ASTERISK
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
                  referenceCatched = false;
            }
      } -> type(LEADING_ASTERISK);
Newline7: NEWLINE
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
                  referenceCatched = false;
            }
      } -> type(NEWLINE);
Space20: WS
      {
            if (!insideReferenceArguments) {
                  _mode = DEFAULT_MODE;
                  insideReferenceArguments = false;
                  referenceCatched = false;
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
            skipCurrentTokenConsuming();
            referenceCatched = false;
            insideReferenceArguments = false;
      } -> skip, mode(DEFAULT_MODE);
//////////////////////////////////////////////////////////////////////////////////////
mode serialField;
Space2: WS -> type(WS);
FIELD_NAME: [a-zA-Z0-9_-]+ -> mode(serialFieldFieldType);
Char3: . 
      {
            skipCurrentTokenConsuming();
            referenceCatched = false;

      } -> skip, mode(DEFAULT_MODE);
//////////////////////////////////////////////////////////////////////////////////////
mode serialFieldFieldType;
Space3: WS -> type(WS);
FIELD_TYPE: [a-zA-Z0-9_-]+ -> mode(DEFAULT_MODE);
Char4: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);
//////////////////////////////////////////////////////////////////////////////////////
mode exception;
Space4: WS -> type(WS);
CLASS_NAME: ([a-zA-Z0-9_-] | '.')+ -> mode(DEFAULT_MODE);
Char5: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);



//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC INLINE TAG MODES  ////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
mode javadocInlineTag;
JAVADOC_INLINE_TAG_CODE_LITERAL : '@code' {recognizeXmlTags=false;} -> mode(code);
JAVADOC_INLINE_TAG_DOC_ROOT_LITERAL : '@docRoot' -> mode(DEFAULT_MODE);
JAVADOC_INLINE_TAG_INHERIT_DOC_LITERAL : '@inheritDoc' -> mode(DEFAULT_MODE);
JAVADOC_INLINE_TAG_LINK_LITERAL : '@link' -> pushMode(seeLink);
JAVADOC_INLINE_TAG_LINKPLAIN_LITERAL : '@linkplain' -> pushMode(seeLink);
JAVADOC_INLINE_TAG_LITERAL_LITERAL : '@literal' {recognizeXmlTags=false;} -> mode(code);
JAVADOC_INLINE_TAG_VALUE_LITERAL : '@value' -> pushMode(value);
JAVADOC_INLINE_TAG_CUSTOM_LITERAL: '@' [a-zA-Z0-9]+ -> mode(DEFAULT_MODE);
Char6: . -> type(CHAR), mode(DEFAULT_MODE);
//////////////////////////////////////////////////////////////////////////////////////
mode code;
Space7: WS -> type(WS), mode(codeText);
Newline2: NEWLINE -> type(NEWLINE), mode(codeText);
Leading_asterisk4: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Char7: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);

//////////////////////////////////////////////////////////////////////////////////////
mode codeText;
Leading_asterisk5: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Skobki: '{' (~[}] | Skobki)* '}' -> type(CHAR);
Text: ~[}] -> type(CHAR);
Char8: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);

//////////////////////////////////////////////////////////////////////////////////////
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
Char10: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);



//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  HTML TAG MODES  //////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
mode xmlTagDefinition;

CLOSE       :   '>'                     -> mode(DEFAULT_MODE) ;
SLASH_CLOSE :   '/>'                    -> mode(DEFAULT_MODE) ;
SLASH       :   '/' ;
EQUALS      :   '=' -> mode(htmlAttr);

// with optional end tag
P_HTML_TAG_NAME: 'P' | 'p';
LI_HTML_TAG_NAME: 'li' | 'LI';
TR_HTML_TAG_NAME: 'tr' | 'TR';
TD_HTML_TAG_NAME: 'td' | 'TD';
TH_HTML_TAG_NAME: 'th' | 'TH';
BODY_HTML_TAG_NAME: 'body' | 'BODY';
COLGROUP_HTML_TAG_NAME: 'colgroup' | 'COLGROUP';
DD_HTML_TAG_NAME: 'dd' | 'DD';
DT_HTML_TAG_NAME: 'dt' | 'DT';
HEAD_HTML_TAG_NAME: 'head' | 'HEAD';
HTML_HTML_TAG_NAME: 'html' | 'HTML';
OPTION_HTML_TAG_NAME: 'option' | 'OPTION';
TBODY_HTML_TAG_NAME: 'tbody' | 'TBODY';
TFOOT_HTML_TAG_NAME: 'tfoot' | 'TFOOT';
THEAD_HTML_TAG_NAME: 'thead' | 'THEAD';

// singleton tags
AREA_HTML_TAG_NAME: 'area' | 'AREA';
BASE_HTML_TAG_NAME: 'base' | 'BASE';
BASEFRONT_HTML_TAG_NAME: 'basefront' | 'BASEFRONT';
BR_HTML_TAG_NAME: 'br' | 'BR';
COL_HTML_TAG_NAME: 'col' | 'COL';
FRAME_HTML_TAG_NAME: 'frame' | 'FRAME';
HR_HTML_TAG_NAME: 'hr' | 'HR';
IMG_HTML_TAG_NAME: 'img' | 'IMG';
INPUT_HTML_TAG_NAME: 'input' | 'INPUT';
ISINDEX_HTML_TAG_NAME: 'isindex' | 'ISINDEX';
LINK_HTML_TAG_NAME: 'link' | 'LINK';
META_HTML_TAG_NAME: 'meta' | 'META';
PARAM_HTML_TAG_NAME: 'param' | 'PARAM';

// other tag names and attribute names
HTML_TAG_IDENT        :   NAME_START_CHAR NAME_CHAR*;

LeadingLEADING_ASTERISK1: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Newline1: NEWLINE -> type(NEWLINE);

S           :   [ \t\r\n]               -> skip ;

Char11: .
      {
            skipCurrentTokenConsuming();
      } -> skip, mode(DEFAULT_MODE);


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
//////////////////////////////////////////////////////////////////////////////////////
mode htmlAttr;
ATTR_VALUE  : '"' ~[<"]* '"'        {attributeCatched=true;}
            | '\'' ~[<']* '\''      {attributeCatched=true;}
            | ( '-' | '+' | DIGIT)+ {attributeCatched=true;}
            | ~[> \t\n]+            {attributeCatched=true;}
            ;
Char12: . {attributeCatched}?
      {
            skipCurrentTokenConsuming();
            attributeCatched = false;
      } -> skip, mode(xmlTagDefinition);

//////////////////////////////////////////////////////////////////////////////////////
mode htmlComment;
HTML_COMMENT_END: '-->' -> mode(DEFAULT_MODE);
LeadingAst: LEADING_ASTERISK -> type(LEADING_ASTERISK);
Newline6: NEWLINE -> type(NEWLINE);
WhiteSpace: WS -> type(WS);
CommentChar: . -> type(CHAR);