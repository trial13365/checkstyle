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

public final class JavadocRuleTypes
{
    public static final int JAVADOC = JavadocParser.RULE_javadoc;

    public static final int HTML_ELEMENT = JavadocParser.RULE_htmlElement;
    public static final int HTML_ELEMENT_OPEN = JavadocParser.RULE_htmlElementOpen;
    public static final int HTML_ELEMENT_CLOSE = JavadocParser.RULE_htmlElementClose;
    public static final int ATTRIBUTE = JavadocParser.RULE_attribute;

    public static final int HTML_COMMENT = JavadocParser.RULE_htmlComment;

    public static final int HTML_TAG = JavadocParser.RULE_htmlTag;

    public static final int P_TAG_OPEN = JavadocParser.RULE_pTagOpen;
    public static final int P_TAG_CLOSE = JavadocParser.RULE_pTagClose;
    public static final int PARAGRAPH = JavadocParser.RULE_paragraph;

    public static final int LI_TAG_OPEN = JavadocParser.RULE_liTagOpen;
    public static final int LI_TAG_CLOSE = JavadocParser.RULE_liTagClose;
    public static final int LI = JavadocParser.RULE_li;

    public static final int TR_TAG_OPEN = JavadocParser.RULE_trTagOpen;
    public static final int TR_TAG_CLOSE = JavadocParser.RULE_trTagClose;
    public static final int TR = JavadocParser.RULE_tr;

    public static final int TD_TAG_OPEN = JavadocParser.RULE_tdTagOpen;
    public static final int TD_TAG_CLOSE = JavadocParser.RULE_tdTagClose;
    public static final int TD = JavadocParser.RULE_td;

    public static final int TH_TAG_OPEN = JavadocParser.RULE_thTagOpen;
    public static final int TH_TAG_CLOSE = JavadocParser.RULE_thTagClose;
    public static final int TH = JavadocParser.RULE_th;

    public static final int BODY_TAG_OPEN = JavadocParser.RULE_bodyTagOpen;
    public static final int BODY_TAG_CLOSE = JavadocParser.RULE_bodyTagClose;
    public static final int BODY = JavadocParser.RULE_body;
    public static final int COLGROUP_TAG_OPEN = JavadocParser.RULE_colgroupTagOpen;
    public static final int COLGROUP_TAG_CLOSE = JavadocParser.RULE_colgroupTagClose;
    public static final int COLGROUP = JavadocParser.RULE_colgroup;
    public static final int DD_TAG_OPEN = JavadocParser.RULE_ddTagOpen;
    public static final int DD_TAG_CLOSE = JavadocParser.RULE_ddTagClose;
    public static final int DD = JavadocParser.RULE_dd;
    public static final int DT_TAG_OPEN = JavadocParser.RULE_dtTagOpen;
    public static final int DT_TAG_CLOSE = JavadocParser.RULE_dtTagClose;
    public static final int DT = JavadocParser.RULE_dt;
    public static final int HEAD_TAG_OPEN = JavadocParser.RULE_headTagOpen;
    public static final int HEAD_TAG_CLOSE = JavadocParser.RULE_headTagClose;
    public static final int HEAD = JavadocParser.RULE_head;
    public static final int HTML_TAG_OPEN = JavadocParser.RULE_htmlTagOpen;
    public static final int HTML_TAG_CLOSE = JavadocParser.RULE_htmlTagClose;
    public static final int HTML = JavadocParser.RULE_html;
    public static final int OPTION_TAG_OPEN = JavadocParser.RULE_optionTagOpen;
    public static final int OPTION_TAG_CLOSE = JavadocParser.RULE_optionTagClose;
    public static final int OPTION = JavadocParser.RULE_option;
    public static final int TBODY_TAG_OPEN = JavadocParser.RULE_tbodyTagOpen;
    public static final int TBODY_TAG_CLOSE = JavadocParser.RULE_tbodyTagClose;
    public static final int TBODY = JavadocParser.RULE_tbody;
    public static final int TFOOT_TAG_OPEN = JavadocParser.RULE_tfootTagOpen;
    public static final int TFOOT_TAG_CLOSE = JavadocParser.RULE_tfootTagClose;
    public static final int TFOOT = JavadocParser.RULE_tfoot;
    public static final int THEAD_TAG_OPEN = JavadocParser.RULE_theadTagOpen;
    public static final int THEAD_TAG_CLOSE = JavadocParser.RULE_theadTagClose;
    public static final int THEAD = JavadocParser.RULE_thead;
    public static final int SINGLETON_TAG = JavadocParser.RULE_singletonTag;
    public static final int CUSTOM_SINGLETON_TAG = JavadocParser.RULE_customSingletonTag;
    public static final int AREA_TAG = JavadocParser.RULE_areaTag;
    public static final int BASE_TAG = JavadocParser.RULE_baseTag;
    public static final int BASEFRONT_TAG = JavadocParser.RULE_basefrontTag;
    public static final int BR_TAG = JavadocParser.RULE_brTag;
    public static final int COL_TAG = JavadocParser.RULE_colTag;
    public static final int FRAME_TAG = JavadocParser.RULE_frameTag;
    public static final int HR_TAG = JavadocParser.RULE_hrTag;
    public static final int IMG_TAG = JavadocParser.RULE_imgTag;
    public static final int INPUT_TAG = JavadocParser.RULE_inputTag;
    public static final int ISINDEX_TAG = JavadocParser.RULE_isindexTag;
    public static final int LINK_TAG = JavadocParser.RULE_linkTag;
    public static final int META_TAG = JavadocParser.RULE_metaTag;
    public static final int PARAM_TAG = JavadocParser.RULE_paramTag;
    public static final int TEXT = JavadocParser.RULE_text;
    public static final int NAME_TEXT = JavadocParser.RULE_nameText;
    public static final int REFERENCE = JavadocParser.RULE_reference;
    public static final int JAVADOC_TAG_AUTHOR = JavadocParser.RULE_javadocTagAuthor;
    public static final int DEPRECATED_TEXT = JavadocParser.RULE_deprecatedText;
    public static final int JAVADOC_TAG_DEPRECATED = JavadocParser.RULE_javadocTagDeprecated;
    public static final int DESCRIPTION = JavadocParser.RULE_description;
    public static final int JAVADOC_TAG_EXCEPTION = JavadocParser.RULE_javadocTagException;
    public static final int JAVADOC_TAG_PARAM = JavadocParser.RULE_javadocTagParam;
    public static final int JAVADOC_TAG_RETURN = JavadocParser.RULE_javadocTagReturn;
    public static final int JAVADOC_TAG_SEE = JavadocParser.RULE_javadocTagSee;
    public static final int FIELD_DESCRIPTION = JavadocParser.RULE_fieldDescription;
    public static final int JAVADOC_TAG_SERIAL = JavadocParser.RULE_javadocTagSerial;
    public static final int JAVADOC_TAG_SERIALFIELD = JavadocParser.RULE_javadocTagSerialField;
    public static final int DATA_DESCRIPTION = JavadocParser.RULE_dataDescription;
    public static final int JAVADOC_TAG_SERIALDATA = JavadocParser.RULE_javadocTagSerialData;
    public static final int SINCE_TEXT = JavadocParser.RULE_sinceText;
    public static final int JAVADOC_TAG_SINCE = JavadocParser.RULE_javadocTagSince;
    public static final int JAVADOC_TAG_THROWS = JavadocParser.RULE_javadocTagThrows;
    public static final int VERSION_TEXT = JavadocParser.RULE_versionText;
    public static final int JAVADOC_TAG_VERSION = JavadocParser.RULE_javadocTagVersion;
    public static final int CUSTOM_ARGUMENT = JavadocParser.RULE_customArgument;
    public static final int JAVADOC_TAG_CUSTOM = JavadocParser.RULE_javadocTagCustom;
    public static final int JAVADOC_INLINE_TAG_CODE = JavadocParser.RULE_javadocInlineTagCode;
    public static final int JAVADOC_INLINE_TAG_DOCROOT = JavadocParser.RULE_javadocInlineTagDocRoot;

    public static final int JAVADOC_INLINE_TAGINHERITDOC =
            JavadocParser.RULE_javadocInlineTagInheritDoc;

    public static final int JAVADOC_INLINE_TAGLINK = JavadocParser.RULE_javadocInlineTagLink;

    public static final int JAVADOC_INLINE_TAGLINKPLAIN =
            JavadocParser.RULE_javadocInlineTagLinkplain;

    public static final int JAVADOC_INLINE_TAGLITERAL = JavadocParser.RULE_javadocInlineTagLiteral;
    public static final int JAVADOC_INLINE_TAGVALUE = JavadocParser.RULE_javadocInlineTagValue;
    public static final int JAVADOC_INLINE_TAG = JavadocParser.RULE_javadocInlineTag;
    public static final int JAVADOC_TAG_SECTION = JavadocParser.RULE_javadocTagSection;
    public static final int MISC = JavadocParser.RULE_misc;

    private JavadocRuleTypes()
    {
    }
}
