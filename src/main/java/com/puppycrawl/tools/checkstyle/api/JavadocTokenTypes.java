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

import java.lang.reflect.Field;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

public final class JavadocTokenTypes
{
    private static final int ruleTypesOffset = 10000;

    // Terminal types

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

    public static final int LITERAL_EXCLUDE = JavadocParser.LITERAL_EXCLUDE;
    public static final int LITERAL_INCLUDE = JavadocParser.LITERAL_INCLUDE;

    public static final int PACKAGE = JavadocParser.PACKAGE;
    public static final int CLASS = JavadocParser.CLASS;
    public static final int HASH = JavadocParser.HASH;
    public static final int MEMBER = JavadocParser.MEMBER;
    public static final int COMMA = JavadocParser.COMMA;
    public static final int DOT = JavadocParser.DOT;
    public static final int LEFT_BRACE = JavadocParser.LEFT_BRACE;
    public static final int RIGHT_BRACE = JavadocParser.RIGHT_BRACE;
    public static final int ARGUMENT = JavadocParser.ARGUMENT;

    public static final int FIELD_NAME = JavadocParser.FIELD_NAME;
    public static final int FIELD_TYPE = JavadocParser.FIELD_TYPE;

    public static final int JAVADOC_INLINE_TAG_END =
            JavadocParser.JAVADOC_INLINE_TAG_END;

    public static final int JAVADOC_INLINE_TAG_START =
            JavadocParser.JAVADOC_INLINE_TAG_START;

    public static final int JAVADOC_INLINE_TAG_LINKPLAIN_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_LINKPLAIN_LITERAL;

    public static final int JAVADOC_INLINE_TAG_CUSTOM_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_CUSTOM_LITERAL;

    public static final int JAVADOC_INLINE_TAG_VALUE_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_VALUE_LITERAL;

    public static final int JAVADOC_INLINE_TAG_CODE_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_CODE_LITERAL;

    public static final int JAVADOC_INLINE_TAG_INHERIT_DOC_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_INHERIT_DOC_LITERAL;

    public static final int JAVADOC_INLINE_TAG_LINK_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_LINK_LITERAL;

    public static final int JAVADOC_INLINE_TAG_DOC_ROOT_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_DOC_ROOT_LITERAL;

    public static final int JAVADOC_INLINE_TAG_LITERAL_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_LITERAL_LITERAL;

    public static final int OPEN = JavadocParser.OPEN;
    public static final int SLASH = JavadocParser.SLASH;
    public static final int CLOSE = JavadocParser.CLOSE;
    public static final int SLASH_CLOSE = JavadocParser.SLASH_CLOSE;
    public static final int ATTR_VALUE = JavadocParser.ATTR_VALUE;
    public static final int CDATA = JavadocParser.CDATA;
    public static final int HTML_COMMENT_START = JavadocParser.HTML_COMMENT_START;
    public static final int HTML_COMMENT_END = JavadocParser.HTML_COMMENT_END;
    public static final int EQUALS = JavadocParser.EQUALS;
    public static final int STRING = JavadocParser.STRING;

    public static final int COL_NAME = JavadocParser.COL_NAME;
    public static final int DT_NAME = JavadocParser.DT_NAME;
    public static final int TD_NAME = JavadocParser.TD_NAME;
    public static final int BODY_NAME = JavadocParser.BODY_NAME;
    public static final int TBODY_NAME = JavadocParser.TBODY_NAME;
    public static final int META_NAME = JavadocParser.META_NAME;
    public static final int BASEFRONT_NAME = JavadocParser.BASEFRONT_NAME;
    public static final int ISINDEX_NAME = JavadocParser.ISINDEX_NAME;
    public static final int BR_NAME = JavadocParser.BR_NAME;
    public static final int OPTION_NAME = JavadocParser.OPTION_NAME;
    public static final int AREA_NAME = JavadocParser.AREA_NAME;
    public static final int BASE_NAME = JavadocParser.BASE_NAME;
    public static final int HR_NAME = JavadocParser.HR_NAME;
    public static final int PARAM_NAME = JavadocParser.PARAM_NAME;
    public static final int TH_NAME = JavadocParser.TH_NAME;
    public static final int FRAME_NAME = JavadocParser.FRAME_NAME;
    public static final int LINK_NAME = JavadocParser.LINK_NAME;
    public static final int HEAD_NAME = JavadocParser.HEAD_NAME;
    public static final int LI_NAME = JavadocParser.LI_NAME;
    public static final int CLASS_NAME = JavadocParser.CLASS_NAME;
    public static final int THEAD_NAME = JavadocParser.THEAD_NAME;
    public static final int INPUT_NAME = JavadocParser.INPUT_NAME;
    public static final int PARAMETER_NAME = JavadocParser.PARAMETER_NAME;
    public static final int COLGROUP_NAME = JavadocParser.COLGROUP_NAME;
    public static final int P_NAME = JavadocParser.P_NAME;
    public static final int DD_NAME = JavadocParser.DD_NAME;
    public static final int TR_NAME = JavadocParser.TR_NAME;
    public static final int IMG_NAME = JavadocParser.IMG_NAME;
    public static final int TFOOT_NAME = JavadocParser.TFOOT_NAME;
    public static final int HTML_NAME = JavadocParser.HTML_NAME;
    public static final int NAME = JavadocParser.NAME;

    public static final int NEWLINE = JavadocParser.NEWLINE;
    public static final int CHAR = JavadocParser.CHAR;
    public static final int WS = JavadocParser.WS;
    public static final int LEADING_ASTERISK = JavadocParser.LEADING_ASTERISK;

    public static final int EOF = JavadocParser.EOF;

    // Rule types (non-terminal)

    public static final int JAVADOC = JavadocParser.RULE_javadoc + ruleTypesOffset;

    public static final int HTML_ELEMENT = JavadocParser.RULE_htmlElement + ruleTypesOffset;
    public static final int HTML_ELEMENT_OPEN = JavadocParser.RULE_htmlElementOpen + ruleTypesOffset + ruleTypesOffset;
    public static final int HTML_ELEMENT_CLOSE = JavadocParser.RULE_htmlElementClose + ruleTypesOffset;
    public static final int ATTRIBUTE = JavadocParser.RULE_attribute + ruleTypesOffset + ruleTypesOffset;

    public static final int HTML_COMMENT = JavadocParser.RULE_htmlComment + ruleTypesOffset + ruleTypesOffset;

    public static final int HTML_TAG = JavadocParser.RULE_htmlTag + ruleTypesOffset;

    public static final int P_TAG_OPEN = JavadocParser.RULE_pTagOpen + ruleTypesOffset;
    public static final int P_TAG_CLOSE = JavadocParser.RULE_pTagClose + ruleTypesOffset;
    public static final int PARAGRAPH = JavadocParser.RULE_paragraph + ruleTypesOffset;

    public static final int LI_TAG_OPEN = JavadocParser.RULE_liTagOpen + ruleTypesOffset;
    public static final int LI_TAG_CLOSE = JavadocParser.RULE_liTagClose + ruleTypesOffset;
    public static final int LI = JavadocParser.RULE_li + ruleTypesOffset;

    public static final int TR_TAG_OPEN = JavadocParser.RULE_trTagOpen + ruleTypesOffset;
    public static final int TR_TAG_CLOSE = JavadocParser.RULE_trTagClose + ruleTypesOffset;
    public static final int TR = JavadocParser.RULE_tr + ruleTypesOffset;

    public static final int TD_TAG_OPEN = JavadocParser.RULE_tdTagOpen + ruleTypesOffset;
    public static final int TD_TAG_CLOSE = JavadocParser.RULE_tdTagClose + ruleTypesOffset;
    public static final int TD = JavadocParser.RULE_td + ruleTypesOffset;

    public static final int TH_TAG_OPEN = JavadocParser.RULE_thTagOpen + ruleTypesOffset;
    public static final int TH_TAG_CLOSE = JavadocParser.RULE_thTagClose + ruleTypesOffset;
    public static final int TH = JavadocParser.RULE_th + ruleTypesOffset;

    public static final int BODY_TAG_OPEN = JavadocParser.RULE_bodyTagOpen + ruleTypesOffset;
    public static final int BODY_TAG_CLOSE = JavadocParser.RULE_bodyTagClose + ruleTypesOffset;
    public static final int BODY = JavadocParser.RULE_body + ruleTypesOffset;
    public static final int COLGROUP_TAG_OPEN = JavadocParser.RULE_colgroupTagOpen + ruleTypesOffset;
    public static final int COLGROUP_TAG_CLOSE = JavadocParser.RULE_colgroupTagClose + ruleTypesOffset;
    public static final int COLGROUP = JavadocParser.RULE_colgroup + ruleTypesOffset;
    public static final int DD_TAG_OPEN = JavadocParser.RULE_ddTagOpen + ruleTypesOffset;
    public static final int DD_TAG_CLOSE = JavadocParser.RULE_ddTagClose + ruleTypesOffset;
    public static final int DD = JavadocParser.RULE_dd + ruleTypesOffset;
    public static final int DT_TAG_OPEN = JavadocParser.RULE_dtTagOpen + ruleTypesOffset;
    public static final int DT_TAG_CLOSE = JavadocParser.RULE_dtTagClose + ruleTypesOffset;
    public static final int DT = JavadocParser.RULE_dt + ruleTypesOffset;
    public static final int HEAD_TAG_OPEN = JavadocParser.RULE_headTagOpen + ruleTypesOffset;
    public static final int HEAD_TAG_CLOSE = JavadocParser.RULE_headTagClose + ruleTypesOffset;
    public static final int HEAD = JavadocParser.RULE_head + ruleTypesOffset;
    public static final int HTML_TAG_OPEN = JavadocParser.RULE_htmlTagOpen + ruleTypesOffset;
    public static final int HTML_TAG_CLOSE = JavadocParser.RULE_htmlTagClose + ruleTypesOffset;
    public static final int HTML = JavadocParser.RULE_html + ruleTypesOffset;
    public static final int OPTION_TAG_OPEN = JavadocParser.RULE_optionTagOpen + ruleTypesOffset;
    public static final int OPTION_TAG_CLOSE = JavadocParser.RULE_optionTagClose + ruleTypesOffset;
    public static final int OPTION = JavadocParser.RULE_option + ruleTypesOffset;
    public static final int TBODY_TAG_OPEN = JavadocParser.RULE_tbodyTagOpen + ruleTypesOffset;
    public static final int TBODY_TAG_CLOSE = JavadocParser.RULE_tbodyTagClose + ruleTypesOffset;
    public static final int TBODY = JavadocParser.RULE_tbody + ruleTypesOffset;
    public static final int TFOOT_TAG_OPEN = JavadocParser.RULE_tfootTagOpen + ruleTypesOffset;
    public static final int TFOOT_TAG_CLOSE = JavadocParser.RULE_tfootTagClose + ruleTypesOffset;
    public static final int TFOOT = JavadocParser.RULE_tfoot + ruleTypesOffset;
    public static final int THEAD_TAG_OPEN = JavadocParser.RULE_theadTagOpen + ruleTypesOffset;
    public static final int THEAD_TAG_CLOSE = JavadocParser.RULE_theadTagClose + ruleTypesOffset;
    public static final int THEAD = JavadocParser.RULE_thead + ruleTypesOffset;
    public static final int SINGLETON_TAG = JavadocParser.RULE_singletonTag + ruleTypesOffset;
    public static final int CUSTOM_SINGLETON_TAG = JavadocParser.RULE_customSingletonTag + ruleTypesOffset;
    public static final int AREA_TAG = JavadocParser.RULE_areaTag + ruleTypesOffset;
    public static final int BASE_TAG = JavadocParser.RULE_baseTag + ruleTypesOffset;
    public static final int BASEFRONT_TAG = JavadocParser.RULE_basefrontTag + ruleTypesOffset;
    public static final int BR_TAG = JavadocParser.RULE_brTag + ruleTypesOffset;
    public static final int COL_TAG = JavadocParser.RULE_colTag + ruleTypesOffset;
    public static final int FRAME_TAG = JavadocParser.RULE_frameTag + ruleTypesOffset;
    public static final int HR_TAG = JavadocParser.RULE_hrTag + ruleTypesOffset;
    public static final int IMG_TAG = JavadocParser.RULE_imgTag + ruleTypesOffset;
    public static final int INPUT_TAG = JavadocParser.RULE_inputTag + ruleTypesOffset;
    public static final int ISINDEX_TAG = JavadocParser.RULE_isindexTag + ruleTypesOffset;
    public static final int LINK_TAG = JavadocParser.RULE_linkTag + ruleTypesOffset;
    public static final int META_TAG = JavadocParser.RULE_metaTag + ruleTypesOffset;
    public static final int PARAM_TAG = JavadocParser.RULE_paramTag + ruleTypesOffset;
    public static final int TEXT = JavadocParser.RULE_text + ruleTypesOffset;
    public static final int NAME_TEXT = JavadocParser.RULE_nameText + ruleTypesOffset;
    public static final int REFERENCE = JavadocParser.RULE_reference + ruleTypesOffset;
    public static final int JAVADOC_TAG_AUTHOR = JavadocParser.RULE_javadocTagAuthor + ruleTypesOffset;
    public static final int DEPRECATED_TEXT = JavadocParser.RULE_deprecatedText + ruleTypesOffset;
    public static final int JAVADOC_TAG_DEPRECATED = JavadocParser.RULE_javadocTagDeprecated + ruleTypesOffset;
    public static final int DESCRIPTION = JavadocParser.RULE_description + ruleTypesOffset;
    public static final int JAVADOC_TAG_EXCEPTION = JavadocParser.RULE_javadocTagException + ruleTypesOffset;
    public static final int JAVADOC_TAG_PARAM = JavadocParser.RULE_javadocTagParam + ruleTypesOffset;
    public static final int JAVADOC_TAG_RETURN = JavadocParser.RULE_javadocTagReturn + ruleTypesOffset;
    public static final int JAVADOC_TAG_SEE = JavadocParser.RULE_javadocTagSee + ruleTypesOffset;
    public static final int FIELD_DESCRIPTION = JavadocParser.RULE_fieldDescription + ruleTypesOffset;
    public static final int JAVADOC_TAG_SERIAL = JavadocParser.RULE_javadocTagSerial + ruleTypesOffset;
    public static final int JAVADOC_TAG_SERIALFIELD = JavadocParser.RULE_javadocTagSerialField + ruleTypesOffset;
    public static final int DATA_DESCRIPTION = JavadocParser.RULE_dataDescription + ruleTypesOffset;
    public static final int JAVADOC_TAG_SERIALDATA = JavadocParser.RULE_javadocTagSerialData + ruleTypesOffset;
    public static final int SINCE_TEXT = JavadocParser.RULE_sinceText + ruleTypesOffset;
    public static final int JAVADOC_TAG_SINCE = JavadocParser.RULE_javadocTagSince + ruleTypesOffset;
    public static final int JAVADOC_TAG_THROWS = JavadocParser.RULE_javadocTagThrows + ruleTypesOffset;
    public static final int VERSION_TEXT = JavadocParser.RULE_versionText + ruleTypesOffset;
    public static final int JAVADOC_TAG_VERSION = JavadocParser.RULE_javadocTagVersion + ruleTypesOffset;
    public static final int CUSTOM_ARGUMENT = JavadocParser.RULE_customArgument + ruleTypesOffset;
    public static final int JAVADOC_TAG_CUSTOM = JavadocParser.RULE_javadocTagCustom + ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_CODE = JavadocParser.RULE_javadocInlineTagCode + ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_DOCROOT = JavadocParser.RULE_javadocInlineTagDocRoot + ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG_LINK = JavadocParser.RULE_javadocInlineTagLink + ruleTypesOffset;
    public static final int PARAMETERS = JavadocParser.RULE_parameters + ruleTypesOffset;

    public static final int JAVADOC_INLINE_TAGINHERITDOC =
            JavadocParser.RULE_javadocInlineTagInheritDoc + ruleTypesOffset;

    public static final int JAVADOC_INLINE_TAGLINK = JavadocParser.RULE_javadocInlineTagLink + ruleTypesOffset;

    public static final int JAVADOC_INLINE_TAGLINKPLAIN =
            JavadocParser.RULE_javadocInlineTagLinkplain + ruleTypesOffset;

    public static final int JAVADOC_INLINE_TAGLITERAL = JavadocParser.RULE_javadocInlineTagLiteral + ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAGVALUE = JavadocParser.RULE_javadocInlineTagValue + ruleTypesOffset;
    public static final int JAVADOC_INLINE_TAG = JavadocParser.RULE_javadocInlineTag + ruleTypesOffset;
    public static final int JAVADOC_TAG_SECTION = JavadocParser.RULE_javadocTagSection + ruleTypesOffset;
    public static final int MISC = JavadocParser.RULE_misc + ruleTypesOffset;

    /** maps from a token name to value */
    private static final ImmutableMap<String, Integer> TOKEN_NAME_TO_VALUE;
    /** maps from a token value to name */
    private static final String[] TOKEN_VALUE_TO_NAME;

    private JavadocTokenTypes()
    {
    }

    // initialise the constants
    static {
        final ImmutableMap.Builder<String, Integer> builder =
                ImmutableMap.builder();
        final Field[] fields = JavadocTokenTypes.class.getDeclaredFields();
        String[] tempTokenValueToName = new String[0];
        for (final Field f : fields) {
            // Only process the int declarations.
            if (f.getType() != Integer.TYPE) {
                continue;
            }

            final String name = f.getName();
            try {
                final int tokenValue = f.getInt(name);
                builder.put(name, tokenValue);
                if (tokenValue > tempTokenValueToName.length - 1) {
                    final String[] temp = new String[tokenValue + 1];
                    System.arraycopy(tempTokenValueToName, 0,
                            temp, 0, tempTokenValueToName.length);
                    tempTokenValueToName = temp;
                }
                if (tokenValue == -1) {
                    tempTokenValueToName[0] = name;
                }
                else {
                    tempTokenValueToName[tokenValue] = name;
                }
            }
            catch (final IllegalArgumentException e) {
                e.printStackTrace();
                System.exit(1);
            }
            catch (final IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        TOKEN_NAME_TO_VALUE = builder.build();
        TOKEN_VALUE_TO_NAME = tempTokenValueToName;
    }

    /**
     * Returns the name of a token for a given ID.
     * @param aID
     *        the ID of the token name to get
     * @return a token name
     */
    public static String getTokenName(int aID)
    {
        if (aID == EOF) {
            return "EOF";
        }
        if (aID > TOKEN_VALUE_TO_NAME.length - 1) {
            throw new IllegalArgumentException("given id " + aID);
        }
        final String name = TOKEN_VALUE_TO_NAME[aID];
        if (name == null) {
            throw new IllegalArgumentException("given id " + aID);
        }
        return name;
    }

    /**
     * Returns the ID of a token for a given name.
     * @param aName
     *        the name of the token ID to get
     * @return a token ID
     */
    public static int getTokenId(String aName)
    {
        final Integer id = TOKEN_NAME_TO_VALUE.get(aName);
        if (id == null) {
            throw new IllegalArgumentException("given name " + aName);
        }
        return id.intValue();
    }
}
