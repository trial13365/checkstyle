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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocAst;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Contains utility methods for working with Javadoc.
 * @author Lyle Hanson
 */
public final class JavadocUtils
{
    ///CLOVER:OFF
    /** prevent instantiation */
    private JavadocUtils()
    {
    }
    ///CLOVER:ON

    /**
     * Gets validTags from a given piece of Javadoc.
     * @param aCmt the Javadoc comment to process.
     * @param aTagType the type of validTags we're interested in
     * @return all standalone validTags from the given javadoc.
     */
    public static JavadocTags getJavadocTags(TextBlock aCmt,
                                             JavadocTagType aTagType)
    {
        final String[] text = aCmt.getText();
        final List<JavadocTag> tags = Lists.newArrayList();
        final List<InvalidJavadocTag> invalidTags = Lists.newArrayList();
        Pattern blockTagPattern =
            Utils.getPattern("/\\*{2,}\\s*@(\\p{Alpha}+)\\s");
        for (int i = 0; i < text.length; i++) {
            final String s = text[i];
            final Matcher blockTagMatcher = blockTagPattern.matcher(s);
            if ((aTagType.equals(JavadocTagType.ALL) || aTagType
                    .equals(JavadocTagType.BLOCK)) && blockTagMatcher.find())
            {
                final String tagName = blockTagMatcher.group(1);
                String content = s.substring(blockTagMatcher.end(1));
                if (content.endsWith("*/")) {
                    content = content.substring(0, content.length() - 2);
                }
                final int line = aCmt.getStartLineNo() + i;
                int col = blockTagMatcher.start(1) - 1;
                if (i == 0) {
                    col += aCmt.getStartColNo();
                }
                if (JavadocTagInfo.isValidName(tagName)) {
                    tags.add(
                        new JavadocTag(line, col, tagName, content.trim()));
                }
                else {
                    invalidTags.add(new InvalidJavadocTag(line, col, tagName));
                }
            }
            // No block tag, so look for inline validTags
            else if (aTagType.equals(JavadocTagType.ALL)
                    || aTagType.equals(JavadocTagType.INLINE))
            {
                // Match JavaDoc text after comment characters
                final Pattern commentPattern =
                    Utils.getPattern("^\\s*(?:/\\*{2,}|\\*+)\\s*(.*)");
                final Matcher commentMatcher = commentPattern.matcher(s);
                final String commentContents;
                final int commentOffset; // offset including comment characters
                if (!commentMatcher.find()) {
                    commentContents = s; // No leading asterisks, still valid
                    commentOffset = 0;
                }
                else {
                    commentContents = commentMatcher.group(1);
                    commentOffset = commentMatcher.start(1) - 1;
                }
                final Pattern tagPattern =
                    Utils.getPattern(".*?\\{@(\\p{Alpha}+)\\s+(.*?)\\}");
                final Matcher tagMatcher = tagPattern.matcher(commentContents);
                while (tagMatcher.find()) {
                    if (tagMatcher.groupCount() == 2) {
                        final String tagName = tagMatcher.group(1);
                        final String tagValue = tagMatcher.group(2).trim();
                        final int line = aCmt.getStartLineNo() + i;
                        int col = commentOffset + (tagMatcher.start(1) - 1);
                        if (i == 0) {
                            col += aCmt.getStartColNo();
                        }
                        if (JavadocTagInfo.isValidName(tagName)) {
                            tags.add(new JavadocTag(line, col, tagName,
                                    tagValue));
                        }
                        else {
                            invalidTags.add(new InvalidJavadocTag(line, col,
                                    tagName));
                        }
                    }
                    // else Error: Unexpected match count for inline JavaDoc
                    // tag!
                }
            }
            blockTagPattern =
                Utils.getPattern("^\\s*\\**\\s*@(\\p{Alpha}+)\\s");
        }
        return new JavadocTags(tags, invalidTags);
    }

    /**
     * The type of Javadoc tag we want returned.
     */
    public enum JavadocTagType
    {
        /** block type. */
        BLOCK,
        /** inline type. */
        INLINE,
        /** all validTags. */
        ALL;
    }

    /**
     * Checks that aCommentContent starts with '*' javadoc comment identifier.
     *
     * @param aCommentContent content of block comment
     * @return true if aCommentContent starts with '*' javadoc comment identifier.
     */
    public static boolean isJavadocComment(String aCommentContent)
    {
        boolean result = false;

        if (!aCommentContent.isEmpty()) {
            final char docCommentIdentificator = aCommentContent.charAt(0);
            result = docCommentIdentificator == '*';
        }

        return result;
    }

    /**
     * Checks block comment content starts with '*' javadoc comment identifier.
     *
     * @param aBlockCommentBegin block comment AST
     * @return true if block comment content starts with '*' javadoc comment identifier.
     */
    public static boolean isJavadocComment(DetailAST aBlockCommentBegin)
    {
        final char docCommentIdentificator = getBlockCommentContent(
                aBlockCommentBegin).charAt(0);
        return docCommentIdentificator == '*';
    }

    /**
     * Gets content of block comment.
     *
     * @param aBlockCommentBegin block comment AST.
     * @return content of block comment.
     */
    public static String getBlockCommentContent(DetailAST aBlockCommentBegin)
    {
        final DetailAST commentContent = aBlockCommentBegin.getFirstChild();
        return commentContent.getText();
    }

    /**
     * Returns the number of direct child tokens that have the specified type.
     * @param aNode
     *        Javadoc AST node
     * @param aType
     *        the token type to match
     * @return the number of matching token
     */
    public static int getChildCount(JavadocAst aNode, int aType)
    {
        int count = 0;
        for (JavadocAst i = aNode.getFirstChild(); i != null; i = i.getNextSibling()) {
            if (i.getType() == aType) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the first child token that has a specified type.
     * @param aNode
     *        Javadoc AST node
     * @param aType
     *        the token type to match
     * @return the matching token, or null if no match
     */
    public static DetailNode findFirstToken(DetailNode aNode, int aType)
    {
        DetailNode retVal = null;
        for (DetailNode i = getFirstChild(aNode);
            i != null;
            i = getNextSibling(i)) {
            if (i.getType() == aType) {
                retVal = i;
                break;
            }
        }
        return retVal;
    }

    public static DetailNode getFirstChild(DetailNode aNode) {
        return aNode.getChildren().length > 0 ? aNode.getChildren()[0] : null;
    }

    public static boolean branchContains(DetailNode aNode, int aType) {
        DetailNode curNode = aNode;
        while (curNode != null) {

            if (aType == curNode.getType()) {
                return true;
            }

            DetailNode toVisit = getFirstChild(curNode);
            while ((curNode != null) && (toVisit == null)) {
                toVisit = getNextSibling(curNode);
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }

            if (curNode == toVisit) {
                break;
            }

            curNode = toVisit;
        }

        return false;
    }

    public static DetailNode getNextSibling(DetailNode node) {
        DetailNode parent = node.getParent();
        if (parent != null) {
            int nextSiblingIndex = node.getIndex() + 1;
            DetailNode[] children = parent.getChildren();
            if (nextSiblingIndex <= children.length - 1) {
                return children[nextSiblingIndex];
            }
        }
        return null;
    }

    public static DetailNode getPreviousSibling(DetailNode node) {
        DetailNode parent = node.getParent();
        if (parent != null) {
            int previousSiblingIndex = node.getIndex() - 1;
            DetailNode[] children = parent.getChildren();
            if (previousSiblingIndex >= 0) {
                return children[previousSiblingIndex];
            }
        }
        return null;
    }

}
