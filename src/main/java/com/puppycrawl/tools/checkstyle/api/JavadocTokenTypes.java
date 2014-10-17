////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

public final class JavadocTokenTypes
{
    private static final int _ruleTypesOffset = 10000;

    // root node
    public static final int JAVADOC = JavadocParser.RULE_javadoc + _ruleTypesOffset;

    //--------------------------------------------------------------------------------------------//
    //------------------        JAVADOC TAGS          --------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    public static final int JAVADOC_TAG = JavadocParser.RULE_javadocTag + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG = JavadocParser.RULE_javadocInlineTag + _ruleTypesOffset;

    public static final int JAVADOC_TAG_RETURN_LITERAL = JavadocParser.JAVADOC_TAG_RETURN_LITERAL;
    public static final int JAVADOC_TAG_DEPRECATED_LITERAL = JavadocParser.JAVADOC_TAG_DEPRECATED_LITERAL;
    public static final int JAVADOC_TAG_SINCE_LITERAL = JavadocParser.JAVADOC_TAG_SINCE_LITERAL;
    public static final int JAVADOC_TAG_SERIAL_DATA_LITERAL = JavadocParser.JAVADOC_TAG_SERIAL_DATA_LITERAL;
    public static final int JAVADOC_TAG_SERIAL_FIELD_LITERAL = JavadocParser.JAVADOC_TAG_SERIAL_FIELD_LITERAL;
    public static final int JAVADOC_TAG_PARAM_LITERAL = JavadocParser.JAVADOC_TAG_PARAM_LITERAL;
    public static final int JAVADOC_TAG_SEE_LITERAL = JavadocParser.JAVADOC_TAG_SEE_LITERAL;
    public static final int JAVADOC_TAG_SERIAL_LITERAL = JavadocParser.JAVADOC_TAG_SERIAL_LITERAL;
    public static final int JAVADOC_TAG_VERSION_LITERAL = JavadocParser.JAVADOC_TAG_VERSION_LITERAL;
    public static final int JAVADOC_TAG_CUSTOM_LITERAL = JavadocParser.JAVADOC_TAG_CUSTOM_LITERAL;
    public static final int JAVADOC_TAG_EXCEPTION_LITERAL = JavadocParser.JAVADOC_TAG_EXCEPTION_LITERAL;
    public static final int JAVADOC_TAG_THROWS_LITERAL = JavadocParser.JAVADOC_TAG_THROWS_LITERAL;
    public static final int JAVADOC_TAG_AUTHOR_LITERAL = JavadocParser.JAVADOC_TAG_AUTHOR_LITERAL;

    public static final int JAVADOC_INLINE_TAG_START = JavadocParser.JAVADOC_INLINE_TAG_START; // '{'
    public static final int JAVADOC_INLINE_TAG_END = JavadocParser.JAVADOC_INLINE_TAG_END; // '}'

    public static final int JAVADOC_INLINE_TAG_CODE = JavadocParser.RULE_javadocInlineTagCode + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_DOC_ROOT = JavadocParser.RULE_javadocInlineTagDocRoot + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_LINK = JavadocParser.RULE_javadocInlineTagLink + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_INHERIT_DOC = JavadocParser.RULE_javadocInlineTagInheritDoc + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_LINKPLAIN = JavadocParser.RULE_javadocInlineTagLinkplain + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_LITERAL = JavadocParser.RULE_javadocInlineTagLiteral + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_CUSTOM = JavadocParser.RULE_javadocInlineTagCustom + _ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_VALUE = JavadocParser.RULE_javadocInlineTagValue + _ruleTypesOffset;

    public static final int JAVADOC_INLINE_TAG_CODE_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_CODE_LITERAL;
    public static final int JAVADOC_INLINE_TAG_DOC_ROOT_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_DOC_ROOT_LITERAL;
    public static final int JAVADOC_INLINE_TAG_LINK_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_LINK_LITERAL;
    public static final int JAVADOC_INLINE_TAG_INHERIT_DOC_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_INHERIT_DOC_LITERAL;
    public static final int JAVADOC_INLINE_TAG_LINKPLAIN_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_LINKPLAIN_LITERAL;
    public static final int JAVADOC_INLINE_TAG_LITERAL_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_LITERAL_LITERAL; // @literal
    public static final int JAVADOC_INLINE_TAG_VALUE_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_VALUE_LITERAL;
    public static final int JAVADOC_INLINE_TAG_CUSTOM_LITERAL = JavadocParser.JAVADOC_INLINE_TAG_CUSTOM_LITERAL;

    // @see and {@link} argument
    public static final int REFERENCE = JavadocParser.RULE_reference + _ruleTypesOffset;

    // @see and {@link} reference components
    public static final int PACKAGE = JavadocParser.PACKAGE;
    public static final int CLASS = JavadocParser.CLASS;
    public static final int DOT = JavadocParser.DOT;
    public static final int HASH = JavadocParser.HASH; // #
    public static final int MEMBER = JavadocParser.MEMBER;
    public static final int PARAMETERS = JavadocParser.RULE_parameters + _ruleTypesOffset; // parent node for LEFT_BRACE, RIGHT_BRACE, ARGUMENT, COMMA nodes
    public static final int LEFT_BRACE = JavadocParser.LEFT_BRACE;
    public static final int RIGHT_BRACE = JavadocParser.RIGHT_BRACE;
    public static final int ARGUMENT = JavadocParser.ARGUMENT;
    public static final int COMMA = JavadocParser.COMMA;

    // @see and {@link} argument
    public static final int STRING = JavadocParser.STRING; // "text"

    // description argument for many Javadoc tags
    public static final int DESCRIPTION = JavadocParser.RULE_description + _ruleTypesOffset;

    // First argument of @exception: @exception class-name description
    public static final int CLASS_NAME = JavadocParser.CLASS_NAME;

    // First argument of @param: @param parameter-name description
    public static final int PARAMETER_NAME = JavadocParser.PARAMETER_NAME;

    // @serial and @serialField arguments
    public static final int LITERAL_EXCLUDE = JavadocParser.LITERAL_EXCLUDE;
    public static final int LITERAL_INCLUDE = JavadocParser.LITERAL_INCLUDE;
    public static final int FIELD_NAME = JavadocParser.FIELD_NAME;
    public static final int FIELD_TYPE = JavadocParser.FIELD_TYPE;


    //--------------------------------------------------------------------------------------------//
    //------------------        HTML TAGS          -----------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    public static final int HTML_ELEMENT = JavadocParser.RULE_htmlElement + _ruleTypesOffset; // parent node for all html tags
    public static final int HTML_ELEMENT_OPEN = JavadocParser.RULE_htmlElementOpen + _ruleTypesOffset + _ruleTypesOffset; // <XXX>
    public static final int HTML_ELEMENT_CLOSE = JavadocParser.RULE_htmlElementClose + _ruleTypesOffset; // </XXX>

    public static final int HTML_TAG = JavadocParser.RULE_htmlTag + _ruleTypesOffset; // non-special HTML tag
    public static final int HTML_TAG_IDENT = JavadocParser.HTML_TAG_IDENT; // identifier inside HTML tag: tag name or attribute name
    public static final int ATTRIBUTE = JavadocParser.RULE_attribute + _ruleTypesOffset + _ruleTypesOffset; // html tag attribute. Parent node for: HTML_TAG_IDENT, EQUALS, ATTR_VALUE

    // HTML tag components
    public static final int OPEN = JavadocParser.OPEN; // '<'
    public static final int SLASH = JavadocParser.SLASH; // '/'
    public static final int CLOSE = JavadocParser.CLOSE; // '>'
    public static final int SLASH_CLOSE = JavadocParser.SLASH_CLOSE; // '/>'
    public static final int EQUALS = JavadocParser.EQUALS; // '='
    public static final int ATTR_VALUE = JavadocParser.ATTR_VALUE; // attribute value

    /////////////////////// HTML TAGS WITH OPTIONAL CLOSE TAG /////////////////////////////////////
    public static final int PARAGRAPH = JavadocParser.RULE_paragraph + _ruleTypesOffset;
    public static final int P_TAG_OPEN = JavadocParser.RULE_pTagOpen + _ruleTypesOffset;
    public static final int P_TAG_CLOSE = JavadocParser.RULE_pTagClose + _ruleTypesOffset;
    public static final int P_HTML_TAG_NAME = JavadocParser.P_HTML_TAG_NAME;

    public static final int LI = JavadocParser.RULE_li + _ruleTypesOffset;
    public static final int LI_TAG_OPEN = JavadocParser.RULE_liTagOpen + _ruleTypesOffset;
    public static final int LI_TAG_CLOSE = JavadocParser.RULE_liTagClose + _ruleTypesOffset;
    public static final int LI_HTML_TAG_NAME = JavadocParser.LI_HTML_TAG_NAME;

    public static final int TR = JavadocParser.RULE_tr + _ruleTypesOffset;
    public static final int TR_TAG_OPEN = JavadocParser.RULE_trTagOpen + _ruleTypesOffset;
    public static final int TR_TAG_CLOSE = JavadocParser.RULE_trTagClose + _ruleTypesOffset;
    public static final int TR_HTML_TAG_NAME = JavadocParser.TR_HTML_TAG_NAME;

    public static final int TD = JavadocParser.RULE_td + _ruleTypesOffset;
    public static final int TD_TAG_OPEN = JavadocParser.RULE_tdTagOpen + _ruleTypesOffset;
    public static final int TD_TAG_CLOSE = JavadocParser.RULE_tdTagClose + _ruleTypesOffset;
    public static final int TD_HTML_TAG_NAME = JavadocParser.TD_HTML_TAG_NAME;

    public static final int TH = JavadocParser.RULE_th + _ruleTypesOffset;
    public static final int TH_TAG_OPEN = JavadocParser.RULE_thTagOpen + _ruleTypesOffset;
    public static final int TH_TAG_CLOSE = JavadocParser.RULE_thTagClose + _ruleTypesOffset;
    public static final int TH_HTML_TAG_NAME = JavadocParser.TH_HTML_TAG_NAME;

    public static final int BODY = JavadocParser.RULE_body + _ruleTypesOffset;
    public static final int BODY_TAG_OPEN = JavadocParser.RULE_bodyTagOpen + _ruleTypesOffset;
    public static final int BODY_TAG_CLOSE = JavadocParser.RULE_bodyTagClose + _ruleTypesOffset;
    public static final int BODY_HTML_TAG_NAME = JavadocParser.BODY_HTML_TAG_NAME;

    public static final int COLGROUP = JavadocParser.RULE_colgroup + _ruleTypesOffset;
    public static final int COLGROUP_TAG_OPEN = JavadocParser.RULE_colgroupTagOpen + _ruleTypesOffset;
    public static final int COLGROUP_TAG_CLOSE = JavadocParser.RULE_colgroupTagClose + _ruleTypesOffset;
    public static final int COLGROUP_HTML_TAG_NAME = JavadocParser.COLGROUP_HTML_TAG_NAME;

    public static final int DD = JavadocParser.RULE_dd + _ruleTypesOffset;
    public static final int DD_TAG_OPEN = JavadocParser.RULE_ddTagOpen + _ruleTypesOffset;
    public static final int DD_TAG_CLOSE = JavadocParser.RULE_ddTagClose + _ruleTypesOffset;
    public static final int DD_HTML_TAG_NAME = JavadocParser.DD_HTML_TAG_NAME;

    public static final int DT = JavadocParser.RULE_dt + _ruleTypesOffset;
    public static final int DT_TAG_OPEN = JavadocParser.RULE_dtTagOpen + _ruleTypesOffset;
    public static final int DT_TAG_CLOSE = JavadocParser.RULE_dtTagClose + _ruleTypesOffset;
    public static final int DT_HTML_TAG_NAME = JavadocParser.DT_HTML_TAG_NAME;

    public static final int HEAD = JavadocParser.RULE_head + _ruleTypesOffset;
    public static final int HEAD_TAG_OPEN = JavadocParser.RULE_headTagOpen + _ruleTypesOffset;
    public static final int HEAD_TAG_CLOSE = JavadocParser.RULE_headTagClose + _ruleTypesOffset;
    public static final int HEAD_HTML_TAG_NAME = JavadocParser.HEAD_HTML_TAG_NAME;

    public static final int HTML = JavadocParser.RULE_html + _ruleTypesOffset;
    public static final int HTML_TAG_OPEN = JavadocParser.RULE_htmlTagOpen + _ruleTypesOffset;
    public static final int HTML_TAG_CLOSE = JavadocParser.RULE_htmlTagClose + _ruleTypesOffset;
    public static final int HTML_HTML_TAG_NAME = JavadocParser.HTML_HTML_TAG_NAME;

    public static final int OPTION = JavadocParser.RULE_option + _ruleTypesOffset;
    public static final int OPTION_TAG_OPEN = JavadocParser.RULE_optionTagOpen + _ruleTypesOffset;
    public static final int OPTION_TAG_CLOSE = JavadocParser.RULE_optionTagClose + _ruleTypesOffset;
    public static final int OPTION_HTML_TAG_NAME = JavadocParser.OPTION_HTML_TAG_NAME;

    public static final int TBODY = JavadocParser.RULE_tbody + _ruleTypesOffset;
    public static final int TBODY_TAG_OPEN = JavadocParser.RULE_tbodyTagOpen + _ruleTypesOffset;
    public static final int TBODY_TAG_CLOSE = JavadocParser.RULE_tbodyTagClose + _ruleTypesOffset;
    public static final int TBODY_HTML_TAG_NAME = JavadocParser.TBODY_HTML_TAG_NAME;

    public static final int TFOOT = JavadocParser.RULE_tfoot + _ruleTypesOffset;
    public static final int TFOOT_TAG_OPEN = JavadocParser.RULE_tfootTagOpen + _ruleTypesOffset;
    public static final int TFOOT_TAG_CLOSE = JavadocParser.RULE_tfootTagClose + _ruleTypesOffset;
    public static final int TFOOT_HTML_TAG_NAME = JavadocParser.TFOOT_HTML_TAG_NAME;

    public static final int THEAD = JavadocParser.RULE_thead + _ruleTypesOffset;
    public static final int THEAD_TAG_OPEN = JavadocParser.RULE_theadTagOpen + _ruleTypesOffset;
    public static final int THEAD_TAG_CLOSE = JavadocParser.RULE_theadTagClose + _ruleTypesOffset;
    public static final int THEAD_HTML_TAG_NAME = JavadocParser.THEAD_HTML_TAG_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////// SINGLETON HTML TAGS  //////////////////////////////////////////////////
    public static final int SINGLETON_ELEMENT = JavadocParser.RULE_singletonElement + _ruleTypesOffset; // parent node for all singleton tags

    public static final int SINGLETON_TAG = JavadocParser.RULE_singletonTag + _ruleTypesOffset; // non-special singleton tag

    public static final int AREA_TAG = JavadocParser.RULE_areaTag + _ruleTypesOffset;
    public static final int AREA_HTML_TAG_NAME = JavadocParser.AREA_HTML_TAG_NAME;

    public static final int BASE_TAG = JavadocParser.RULE_baseTag + _ruleTypesOffset;
    public static final int BASE_HTML_TAG_NAME = JavadocParser.BASE_HTML_TAG_NAME;

    public static final int BASEFRONT_TAG = JavadocParser.RULE_basefrontTag + _ruleTypesOffset;
    public static final int BASEFRONT_HTML_TAG_NAME = JavadocParser.BASEFRONT_HTML_TAG_NAME;

    public static final int BR_TAG = JavadocParser.RULE_brTag + _ruleTypesOffset;
    public static final int BR_HTML_TAG_NAME = JavadocParser.BR_HTML_TAG_NAME;

    public static final int COL_TAG = JavadocParser.RULE_colTag + _ruleTypesOffset;
    public static final int COL_HTML_TAG_NAME = JavadocParser.COL_HTML_TAG_NAME;

    public static final int FRAME_TAG = JavadocParser.RULE_frameTag + _ruleTypesOffset;
    public static final int FRAME_HTML_TAG_NAME = JavadocParser.FRAME_HTML_TAG_NAME;

    public static final int HR_TAG = JavadocParser.RULE_hrTag + _ruleTypesOffset;
    public static final int HR_HTML_TAG_NAME = JavadocParser.HR_HTML_TAG_NAME;

    public static final int IMG_TAG = JavadocParser.RULE_imgTag + _ruleTypesOffset;
    public static final int IMG_HTML_TAG_NAME = JavadocParser.IMG_HTML_TAG_NAME;

    public static final int INPUT_TAG = JavadocParser.RULE_inputTag + _ruleTypesOffset;
    public static final int INPUT_HTML_TAG_NAME = JavadocParser.INPUT_HTML_TAG_NAME;

    public static final int ISINDEX_TAG = JavadocParser.RULE_isindexTag + _ruleTypesOffset;
    public static final int ISINDEX_HTML_TAG_NAME = JavadocParser.ISINDEX_HTML_TAG_NAME;

    public static final int LINK_TAG = JavadocParser.RULE_linkTag + _ruleTypesOffset;
    public static final int LINK_HTML_TAG_NAME = JavadocParser.LINK_HTML_TAG_NAME;

    public static final int META_TAG = JavadocParser.RULE_metaTag + _ruleTypesOffset;
    public static final int META_HTML_TAG_NAME = JavadocParser.META_HTML_TAG_NAME;

    public static final int PARAM_TAG = JavadocParser.RULE_paramTag + _ruleTypesOffset;
    public static final int PARAM_HTML_TAG_NAME = JavadocParser.PARAM_HTML_TAG_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // HTML comments
    public static final int HTML_COMMENT = JavadocParser.RULE_htmlComment + _ruleTypesOffset + _ruleTypesOffset;
    public static final int HTML_COMMENT_START = JavadocParser.HTML_COMMENT_START; // <!---
    public static final int HTML_COMMENT_END = JavadocParser.HTML_COMMENT_END; // -->

    public static final int CDATA = JavadocParser.CDATA; // '<![CDATA[...]]>'

    //--------------------------------------------------------------------------------------------//
    //------------------        OTHER          ---------------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    public static final int LEADING_ASTERISK = JavadocParser.LEADING_ASTERISK;
    public static final int NEWLINE = JavadocParser.NEWLINE; // \n
    public static final int CHAR = JavadocParser.CHAR; // any symbol
    public static final int WS = JavadocParser.WS; // whitespace, \t
    public static final int TEXT = JavadocParser.RULE_text + _ruleTypesOffset; // CHAR and WS sequence

    public static final int MISC = JavadocParser.RULE_misc + _ruleTypesOffset; // can be: LEADING_ASTERISK, NEWLINE, HTML_COMMENT, CDATA, TEXT, JAVADOC_INLINE_TAG

    public static final int EOF = JavadocParser.EOF; // end of file



    private JavadocTokenTypes()
    {
    }

}
