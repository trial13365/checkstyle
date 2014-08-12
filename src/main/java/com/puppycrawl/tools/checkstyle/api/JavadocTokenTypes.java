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
    public static final int JAVADOC_TAG_RETURN = JavadocParser.JAVADOC_TAG_RETURN;
    public static final int JAVADOC_TAG_DEPRECATED = JavadocParser.JAVADOC_TAG_DEPRECATED;
    public static final int JAVADOC_TAG_SINCE = JavadocParser.JAVADOC_TAG_SINCE;
    public static final int JAVADOC_TAG_SERIAL_DATA = JavadocParser.JAVADOC_TAG_SERIAL_DATA;
    public static final int JAVADOC_TAG_SERIAL_FIELD = JavadocParser.JAVADOC_TAG_SERIAL_FIELD;
    public static final int JAVADOC_TAG_PARAM = JavadocParser.JAVADOC_TAG_PARAM;
    public static final int JAVADOC_TAG_SEE = JavadocParser.JAVADOC_TAG_SEE;
    public static final int JAVADOC_TAG_SERIAL = JavadocParser.JAVADOC_TAG_SERIAL;
    public static final int JAVADOC_TAG_VERSION = JavadocParser.JAVADOC_TAG_VERSION;
    public static final int JAVADOC_TAG_CUSTOM = JavadocParser.JAVADOC_TAG_CUSTOM;
    public static final int JAVADOC_TAG_EXCEPTION = JavadocParser.JAVADOC_TAG_EXCEPTION;
    public static final int JAVADOC_TAG_THROWS = JavadocParser.JAVADOC_TAG_THROWS;
    public static final int JAVADOC_TAG_AUTHOR = JavadocParser.JAVADOC_TAG_AUTHOR;

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

    public static final int JAVADOC_INLINE_TAG_LINKPLAIN =
            JavadocParser.JAVADOC_INLINE_TAG_LINKPLAIN;

    public static final int JAVADOC_INLINE_TAG_CUSTOM =
            JavadocParser.JAVADOC_INLINE_TAG_CUSTOM;

    public static final int JAVADOC_INLINE_TAG_VALUE =
            JavadocParser.JAVADOC_INLINE_TAG_VALUE;

    public static final int JAVADOC_INLINE_TAG_CODE =
            JavadocParser.JAVADOC_INLINE_TAG_CODE;

    public static final int JAVADOC_INLINE_TAG_START =
            JavadocParser.JAVADOC_INLINE_TAG_START;

    public static final int JAVADOC_INLINE_TAG_INHERIT_DOC =
            JavadocParser.JAVADOC_INLINE_TAG_INHERIT_DOC;

    public static final int JAVADOC_INLINE_TAG_LINK =
            JavadocParser.JAVADOC_INLINE_TAG_LINK;

    public static final int JAVADOC_INLINE_TAG_END =
            JavadocParser.JAVADOC_INLINE_TAG_END;

    public static final int JAVADOC_INLINE_TAG_DOC_ROOT =
            JavadocParser.JAVADOC_INLINE_TAG_DOC_ROOT;

    public static final int JAVADOC_INLINE_TAG_LITERAL =
            JavadocParser.JAVADOC_INLINE_TAG_LITERAL;

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
        if (aID > TOKEN_VALUE_TO_NAME.length - 1) {
            throw new IllegalArgumentException("given id " + aID);
        }
        final String name = TOKEN_VALUE_TO_NAME[aID];
        if (name == null) {
            throw new IllegalArgumentException("given id " + aID);
        }
        return name;
    }
}
