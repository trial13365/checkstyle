parser grammar JavadocParser;

options { tokenVocab=JavadocLexer; }

@parser::members {
	boolean isNextJavadocTag() {
		int token1 = _input.LA(2);
		int token2 = _input.LA(3);
		return isJavadocTag(token1)
			|| (token1 == WS && isJavadocTag(token2));
	}

	boolean isJavadocTag(int type) {
		switch(type) {
			case JAVADOC_TAG_AUTHOR_LITERAL:
			case JAVADOC_TAG_DEPRECATED_LITERAL:
			case JAVADOC_TAG_EXCEPTION_LITERAL: 
			case JAVADOC_TAG_PARAM_LITERAL:
			case JAVADOC_TAG_RETURN_LITERAL:
			case JAVADOC_TAG_SEE_LITERAL:
			case JAVADOC_TAG_SERIAL_LITERAL:
			case JAVADOC_TAG_SERIAL_FIELD_LITERAL:
			case JAVADOC_TAG_SERIAL_DATA_LITERAL:
			case JAVADOC_TAG_SINCE_LITERAL:
			case JAVADOC_TAG_THROWS_LITERAL:
			case JAVADOC_TAG_VERSION_LITERAL:
			case JAVADOC_TAG_CUSTOM_LITERAL:
				return true;
			default:
				return false;
		}
	}
}

javadoc:   (htmlElement | misc)* javadocTagSection? EOF;

htmlElement: htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            ;

htmlElementOpen:  OPEN HTML_TAG_IDENT (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
htmlElementClose: OPEN SLASH HTML_TAG_IDENT CLOSE;
attribute:    HTML_TAG_IDENT (NEWLINE | LEADING_ASTERISK)* EQUALS (NEWLINE | LEADING_ASTERISK)* attributeValue ;
attributeValue: (ATTR_VALUE | text | HTML_TAG_IDENT);

htmlTag: htmlElementOpen (htmlElement | misc)* htmlElementClose
            | htmlElementOpen (htmlElement | misc)* {notifyErrorListeners("javadoc.missed.html.close");}
            ;

//////////////////////////////////////////////////////////////////////////////////////
////////////////////  HTML TAGS WITH OPTIONAL END TAG ////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
pTagOpen: OPEN P_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
pTagClose: OPEN SLASH P_HTML_TAG_NAME CLOSE;
paragraph: pTagOpen
		(htmlTag
		| singletonTag
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
		pTagClose
		;

liTagOpen: OPEN LI_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
liTagClose: OPEN SLASH LI_HTML_TAG_NAME CLOSE;
li: liTagOpen
	(htmlTag
		| singletonTag
		| paragraph
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
		| misc)*
	liTagClose
	;

trTagOpen: OPEN TR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
trTagClose: OPEN SLASH TR_HTML_TAG_NAME CLOSE;
tr: trTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	trTagClose
	;

tdTagOpen: OPEN TD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
tdTagClose: OPEN SLASH TD_HTML_TAG_NAME CLOSE;
td: tdTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	tdTagClose
	;

thTagOpen: OPEN TH_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
thTagClose: OPEN SLASH TH_HTML_TAG_NAME CLOSE;
th: thTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	thTagClose
	;

bodyTagOpen: OPEN BODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
bodyTagClose: OPEN SLASH BODY_HTML_TAG_NAME CLOSE;
body: bodyTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	bodyTagClose
	;

colgroupTagOpen: OPEN COLGROUP_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
colgroupTagClose: OPEN SLASH COLGROUP_HTML_TAG_NAME CLOSE;
colgroup: colgroupTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	colgroupTagClose
	;

ddTagOpen: OPEN DD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
ddTagClose: OPEN SLASH DD_HTML_TAG_NAME CLOSE;
dd: ddTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	ddTagClose
	;

dtTagOpen: OPEN DT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
dtTagClose: OPEN SLASH DT_HTML_TAG_NAME CLOSE;
dt: dtTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | head
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	dtTagClose
	;

headTagOpen: OPEN HEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
headTagClose: OPEN SLASH HEAD_HTML_TAG_NAME CLOSE;
head: headTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | html
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	headTagClose
	;

htmlTagOpen: OPEN HTML_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
htmlTagClose: OPEN SLASH HTML_HTML_TAG_NAME CLOSE;
html: htmlTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | option
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	htmlTagClose
	;

optionTagOpen: OPEN OPTION_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
optionTagClose: OPEN SLASH OPTION_HTML_TAG_NAME CLOSE;
option: optionTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | tbody
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	optionTagClose
	;

tbodyTagOpen: OPEN TBODY_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
tbodyTagClose: OPEN SLASH TBODY_HTML_TAG_NAME CLOSE;
tbody: tbodyTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | thead
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | theadTagOpen
            | tfootTagOpen
            | misc)*
	tbodyTagClose
	;

tfootTagOpen: OPEN TFOOT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
tfootTagClose: OPEN SLASH TFOOT_HTML_TAG_NAME CLOSE;
tfoot: tfootTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | thead
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | theadTagOpen
            | misc)*
	tfootTagClose
	;

theadTagOpen: OPEN THEAD_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE;
theadTagClose: OPEN SLASH THEAD_HTML_TAG_NAME CLOSE;
thead: theadTagOpen
	(htmlTag
		| singletonTag
		| paragraph
		| li
            | tr
            | td
            | th
            | body
            | colgroup
            | dd
            | dt
            | head
            | html
            | option
            | tbody
            | tfoot
            | pTagOpen
            | liTagOpen
            | trTagOpen
            | tdTagOpen
            | thTagOpen
            | bodyTagOpen
            | colgroupTagOpen
            | ddTagOpen
            | dtTagOpen
            | headTagOpen
            | htmlTagOpen
            | optionTagOpen
            | tbodyTagOpen
            | tfootTagOpen
            | misc)*
	theadTagClose
	;

//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  SINLETON HTML TAGS  //////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
singletonTag: customSingletonTag
			| areaTag
			| baseTag
			| basefrontTag
			| brTag
			| colTag
			| frameTag
			| hrTag
			| imgTag
			| inputTag
			| isindexTag
			| linkTag
			| metaTag
			| paramTag
			;

customSingletonTag: OPEN HTML_TAG_IDENT (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE;

areaTag: OPEN AREA_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN AREA_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
baseTag: OPEN BASE_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN BASE_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
basefrontTag: OPEN BASEFRONT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN BASEFRONT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
brTag: OPEN BR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN BR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
colTag: OPEN COL_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN COL_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
frameTag: OPEN FRAME_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN FRAME_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
hrTag: OPEN HR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN HR_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
imgTag: OPEN IMG_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN IMG_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
inputTag: OPEN INPUT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN INPUT_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
isindexTag: OPEN ISINDEX_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN ISINDEX_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
linkTag: OPEN LINK_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN LINK_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
metaTag: OPEN META_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN META_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;
paramTag: OPEN PARAM_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* SLASH_CLOSE
	| OPEN PARAM_HTML_TAG_NAME (attribute | NEWLINE | LEADING_ASTERISK)* CLOSE
	;



//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC TAGS  ////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
nameText: (misc | htmlElement)+;
javadocTagAuthor: JAVADOC_TAG_AUTHOR_LITERAL (WS | NEWLINE)* nameText?;

deprecatedText: (misc | htmlElement)+;
javadocTagDeprecated: JAVADOC_TAG_DEPRECATED_LITERAL (WS | NEWLINE)* deprecatedText?;

description: (misc | htmlElement)+;
javadocTagException: JAVADOC_TAG_EXCEPTION_LITERAL (WS | NEWLINE)* CLASS_NAME? (WS | NEWLINE)* description?;

javadocTagParam: JAVADOC_TAG_PARAM_LITERAL (WS | NEWLINE)* PARAMETER_NAME? (WS | NEWLINE)* description?;

javadocTagReturn: JAVADOC_TAG_RETURN_LITERAL (WS | NEWLINE)* description?;

reference:
      (
            PACKAGE (DOT | CLASS)* HASH? MEMBER? parameters?
            | (DOT | CLASS)+ HASH? MEMBER? parameters?
            | HASH? MEMBER parameters?
      )
      ;
parameters: LEFT_BRACE (ARGUMENT | COMMA | WS)* RIGHT_BRACE;
javadocTagSee: JAVADOC_TAG_SEE_LITERAL (WS | NEWLINE)*
				reference? (STRING | htmlElement)* (WS | NEWLINE)*
				description?;

fieldDescription: (misc | htmlElement)+;
javadocTagSerial: JAVADOC_TAG_SERIAL_LITERAL (WS | NEWLINE)* (fieldDescription | (LITERAL_INCLUDE | LITERAL_EXCLUDE) misc*)?;

javadocTagSerialField: JAVADOC_TAG_SERIAL_FIELD_LITERAL (WS | NEWLINE)* FIELD_NAME? (WS | NEWLINE)* FIELD_TYPE? (WS | NEWLINE)* fieldDescription?;

dataDescription: (misc | htmlElement)+;
javadocTagSerialData: JAVADOC_TAG_SERIAL_DATA_LITERAL (WS | NEWLINE)* dataDescription?;

sinceText: (misc | htmlElement | paragraph)+;
javadocTagSince: JAVADOC_TAG_SINCE_LITERAL (WS | NEWLINE)* sinceText?;

javadocTagThrows: JAVADOC_TAG_THROWS_LITERAL (WS | NEWLINE)* CLASS_NAME? (WS | NEWLINE)* description?;

versionText: (misc | htmlElement)+;
javadocTagVersion: JAVADOC_TAG_VERSION_LITERAL (WS | NEWLINE)* versionText?;

customArgument: (misc | htmlElement)+;
javadocTagCustom: JAVADOC_TAG_CUSTOM_LITERAL (WS | NEWLINE)* customArgument?;

javadocTagSection:
	(
		LEADING_ASTERISK? WS? 
		( javadocTagAuthor
		| javadocTagDeprecated
	  	| javadocTagException
	  	| javadocTagParam
	  	| javadocTagReturn
	  	| javadocTagSee
	  	| javadocTagSerial
	  	| javadocTagSerialData
	  	| javadocTagSerialField
	  	| javadocTagSince
	  	| javadocTagThrows
	  	| javadocTagVersion
	  	| javadocTagCustom
	  	)
	)+
	;
//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  JAVADOC INLINE TAGS  /////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
javadocInlineTagCode:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_CODE_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	text?
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagDocRoot:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_DOC_ROOT_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagInheritDoc:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_INHERIT_DOC_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagLink:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_LINK_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	reference
	(misc | htmlElement)*
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagLinkplain:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_LINKPLAIN_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	reference
	(misc | htmlElement)*
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagLiteral:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_LITERAL_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	text?
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagValue:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_VALUE_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)*
	reference
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTagCustom:
	JAVADOC_INLINE_TAG_START
	JAVADOC_INLINE_TAG_CUSTOM_LITERAL
	(WS | NEWLINE | LEADING_ASTERISK)+
	(misc | htmlElement)*
	JAVADOC_INLINE_TAG_END
	;

javadocInlineTag: javadocInlineTagCode
	| javadocInlineTagDocRoot
	| javadocInlineTagInheritDoc
	| javadocInlineTagLink
	| javadocInlineTagLinkplain
	| javadocInlineTagLiteral
	| javadocInlineTagValue
	| javadocInlineTagCustom
	;

htmlComment: HTML_COMMENT_START (text | NEWLINE | LEADING_ASTERISK)* HTML_COMMENT_END;

text : (CHAR | WS)+ ;
misc :
	(
		({!isNextJavadocTag()}? LEADING_ASTERISK)
		| htmlComment
		| CDATA
		| NEWLINE
		| text
		| javadocInlineTag
	)+
	;


