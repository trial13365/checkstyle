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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.base.CaseFormat;
import com.google.common.primitives.Ints;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocLexer;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

/**
 * Base class for Checks that process Javadoc comments.
 *
 * @author Baratali Izmailov
 */
public abstract class AbstractJavadocCheck extends Check
{
    /**
     * Custom error listener.
     */
    private final DescriptiveErrorListener mErrorListener =
            new DescriptiveErrorListener();

    /**
     * DetailAST node of considered Javadoc comment that is just a block comment
     * in Java language syntax tree.
     */
    private DetailAST mBlockCommentAst;

    /**
     * Returns the default token types a check is interested in.
     *
     * @return the default token types
     * @see JavadocTokenTypes
     */
    public abstract int[] getDefaultJavadocTokens();

    /**
     * Called before the starting to process a tree.
     *
     * @param aRootAst the root of the tree
     */
    public void beginJavadocTree(DetailNode aRootAst)
    {
    }

    /**
     * Called after finished processing a tree.
     *
     * @param aRootAst the root of the tree
     */
    public void finishJavadocTree(DetailNode aRootAst)
    {
    }

    /**
     * Called to process a Javadoc token.
     * @param aAst the token to process
     */
    public void visitJavadocToken(DetailNode aAst)
    {
    }

    /**
     * Called after all the child nodes have been process.
     *
     * @param aAst the token leaving
     */
    public void leaveJavadocToken(DetailNode aAst)
    {
    }

    @Override
    public final int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
    }

    @Override
    public final int[] getAcceptableTokens()
    {
        return super.getAcceptableTokens();
    }

    @Override
    public final int[] getRequiredTokens()
    {
        return super.getRequiredTokens();
    }

    @Override
    public final boolean isCommentNodesRequired()
    {
        return true;
    }

    @Override
    public final void visitToken(DetailAST aBlockCommentAst)
    {
        mBlockCommentAst = aBlockCommentAst;

        final String commentContent = JavadocUtils.getBlockCommentContent(aBlockCommentAst);

        if (JavadocUtils.isJavadocComment(commentContent)) {

            final String javadocComment = commentContent.substring(1);

            // Log messages should have line number in scope of file,
            // not in scope of Javadoc comment.
            // Offset is line number of beginning of Javadoc comment.
            mErrorListener.setOffset(aBlockCommentAst.getLineNo() - 1);

            try {
                final ParseTree parseTree = parseJavadoc(javadocComment);

                DetailNode node = convert(parseTree);

                processTree(node);
            }
            catch (IOException e) {
                // Antlr can not initiate its ANTLRInputStream
                log(aBlockCommentAst.getLineNo(), "javadoc.parse.error",
                        e.getMessage());
            }
            catch (ParseCancellationException e) {
                // If syntax error occurs then message is printed by error listener
                // and parser throws this runtime exception to stop parsing.
                // Just stop processing current Javadoc comment.
                return;
            }
        }
    }

    protected DetailAST getBlockCommentAst()
    {
        return mBlockCommentAst;
    }

    public JavadocNodeImpl convert(ParseTree rootParseTree)
    {
        ParseTree currentParseTreeNode = rootParseTree;
        JavadocNodeImpl rootJavadocNode = create(currentParseTreeNode, null, -1);

        int childCount = currentParseTreeNode.getChildCount();
        JavadocNodeImpl[] children = (JavadocNodeImpl[]) rootJavadocNode.getChildren();

        for (int i = 0; i < childCount; i++) {
            JavadocNodeImpl child = create(currentParseTreeNode.getChild(i), rootJavadocNode, i);
            children[i] = child;
        }

        JavadocNodeImpl currentJavadocParent = rootJavadocNode;
        ParseTree currentParseTreeParent = currentParseTreeNode;

        while (currentJavadocParent != null) {
            children = (JavadocNodeImpl[]) currentJavadocParent.getChildren();
            childCount = children.length;

            for (int i = 0; i < childCount; i++) {
                JavadocNodeImpl currentJavadocNode = children[i];
                ParseTree currentParseTreeNodeChild = currentParseTreeParent.getChild(i);

                JavadocNodeImpl[] subChildren = (JavadocNodeImpl[]) currentJavadocNode
                        .getChildren();

                for (int j = 0; j < subChildren.length; j++) {
                    JavadocNodeImpl child = create(currentParseTreeNodeChild.getChild(j),
                            currentJavadocNode, j);
                    subChildren[j] = child;
                }
            }

            if (childCount > 0) {
                currentJavadocParent = children[0];
                currentParseTreeParent = currentParseTreeParent.getChild(0);
            }
            else {
                JavadocNodeImpl nextJavadocSibling = (JavadocNodeImpl) JavadocUtils
                        .getNextSibling(currentJavadocParent);
                ParseTree nextParseTreeSibling = getNextSibling(currentParseTreeParent);

                if (nextJavadocSibling == null) {
                    JavadocNodeImpl tempJavadocParent = (JavadocNodeImpl) currentJavadocParent.getParent();
                    ParseTree tempParseTreeParent = currentParseTreeParent.getParent();

                    while (nextJavadocSibling == null && tempJavadocParent != null) {

                        nextJavadocSibling = (JavadocNodeImpl) JavadocUtils
                                .getNextSibling(tempJavadocParent);

                        nextParseTreeSibling = getNextSibling(tempParseTreeParent);

                        tempJavadocParent = (JavadocNodeImpl) tempJavadocParent.getParent();
                        tempParseTreeParent = tempParseTreeParent.getParent();
                    }
                }
                currentJavadocParent = nextJavadocSibling;
                currentParseTreeParent = nextParseTreeSibling;
            }
        }

        return rootJavadocNode;
    }

    private JavadocNodeImpl
    create(ParseTree parseTree, DetailNode parent, int index)
    {
        JavadocNodeImpl node = new JavadocNodeImpl();
        node.setText(parseTree.getText());
        node.setColumnNumber(getColumn(parseTree));
        node.setLineNumber(getLine(parseTree) + mBlockCommentAst.getLineNo());
        node.setIndex(index);
        node.setType(getTokenType(parseTree));
        node.setParent(parent);
        node.setChildren(new JavadocNodeImpl[parseTree.getChildCount()]);
        return node;
    }

    private static ParseTree getNextSibling(ParseTree node)
    {
        if (node.getParent() == null) {
            return null;
        }

        ParseTree parent = node.getParent();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            ParseTree currentNode = parent.getChild(i);
            if (currentNode.equals(node)) {
                if (i == childCount - 1) {
                    return null;
                }
                return parent.getChild(i + 1);
            }
        }
        return null;
    }

    private static int getTokenType(ParseTree aNode)
    {
        int tokenType = Integer.MIN_VALUE;

        if (aNode.getChildCount() == 0) {
            tokenType = ((TerminalNode) aNode).getSymbol().getType();
        }
        else {
            final String className = getNodeClassNameWithoutContext(aNode);
            final String typeName =
                    CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, className);
            tokenType = JavadocUtils.getTokenId(typeName);
        }

        return tokenType;
    }

    /**
     * Gets class name of ParseTree node and removes 'Context' postfix at the end.
     *
     * @param aNode ParseTree node.
     * @return class name without 'Context'
     */
    private static String getNodeClassNameWithoutContext(ParseTree aNode) {
        final String className = aNode.getClass().getSimpleName();
        // remove 'Context' at the end
        final int contextLength = 7;
        return className.substring(0, className.length() - contextLength);
    }

    /**
     * Gets line number from ParseTree node.
     * @param aTree ParseTree node
     * @return line number
     */
    private static int getLine(ParseTree aTree)
    {
        if (aTree instanceof TerminalNode) {
            return ((TerminalNode) aTree).getSymbol().getLine() - 1;
        }
        else {
            final ParserRuleContext rule = (ParserRuleContext) aTree;
            return rule.start.getLine() - 1;
        }
    }

    /**
     * Gets column number from ParseTree node.
     * @param aTree ParseTree node
     * @return column number
     */
    private static int getColumn(ParseTree aTree)
    {
        if (aTree instanceof TerminalNode) {
            return ((TerminalNode) aTree).getSymbol().getCharPositionInLine();
        }
        else {
            final ParserRuleContext rule = (ParserRuleContext) aTree;
            return rule.start.getCharPositionInLine();
        }
    }

    /**
     * Parses block comment content as javadoc comment.
     * @param aBlockComment
     *        block comment content.
     * @return parse tree
     * @throws IOException
     *         errors in ANTLRInputStream
     */
    private ParseTree parseJavadoc(String aBlockComment)
            throws IOException
    {
        final Charset utf8Charset = Charset.forName("UTF-8");
        final InputStream in = new ByteArrayInputStream(aBlockComment.getBytes(utf8Charset));

        final ANTLRInputStream input = new ANTLRInputStream(in);

        final JavadocLexer lexer = new JavadocLexer(input);

        // remove default error listeners
        lexer.removeErrorListeners();

        // add custom error listener that logs parsing errors
        lexer.addErrorListener(mErrorListener);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final JavadocParser parser = new JavadocParser(tokens);

        // remove default error listeners
        parser.removeErrorListeners();

        // add custom error listener that logs syntax errors
        parser.addErrorListener(mErrorListener);

        // This strategy stops parsing when parser error occurs.
        // By default it uses Error Recover Strategy which is slow and useless.
        parser.setErrorHandler(new BailErrorStrategy());

        return parser.javadoc();
    }

    /**
     * Processes JavadocAST tree notifying Check.
     *
     * @param aRoot root of JavadocAST tree.
     */
    private void processTree(DetailNode aRoot)
    {
        beginJavadocTree(aRoot);
        walk(aRoot);
        finishJavadocTree(aRoot);
    }

    /**
     * Processes a node calling Check at interested nodes.
     *
     * @param aRoot
     *        the root of tree for process
     */
    private void walk(DetailNode aRoot)
    {
        final int[] defaultTokenTypes = getDefaultJavadocTokens();

        if (defaultTokenTypes == null) {
            return;
        }

        DetailNode curNode = aRoot;
        while (curNode != null) {
            final boolean waitsFor = Ints.contains(defaultTokenTypes, curNode.getType());

            if (waitsFor) {
                visitJavadocToken(curNode);
            }
            DetailNode toVisit = JavadocUtils.getFirstChild(curNode);
            while ((curNode != null) && (toVisit == null)) {

                if (waitsFor) {
                    leaveJavadocToken(curNode);
                }

                toVisit = JavadocUtils.getNextSibling(curNode);
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }
            curNode = toVisit;
        }
    }

    /**
     * Custom error listener for JavadocParser that prints user readable errors.
     */
    class DescriptiveErrorListener extends BaseErrorListener
    {
        /**
         * Message key of error message. Missed close HTML tag breaks structure of parse tree,
         * so parser stops parsing and generates such error message.
         * This case is special because parser prints error like
         * {@code "no viable alternative at input 'b \n *\n'"} and it is not clear that error is
         * about missed close HTML tag.
         */
        private static final String JAVADOC_MISSED_HTML_CLOSE = "javadoc.missed.html.close";
        private static final String JAVADOC_WRONG_SINGLETON_TAG = "javadoc.wrong.singleton.html.tag";

        /**
         * Offset is line number of beginning of the Javadoc comment.
         * Log messages should have line number in scope of file, not in scope of Javadoc comment.
         */
        private int mOffset;

        /**
         * Sets offset. Offset is line number of beginning of the Javadoc comment.
         * Log messages should have line number in scope of file, not in scope of Javadoc comment.
         *
         * @param aOffset offset line number
         */
        public void setOffset(int aOffset)
        {
            mOffset = aOffset;
        }

        /**
         * <p>
         * Logs parser errors in Checkstyle manner.
         * Parser can generate error messages. There is special error that parser can generate. It is
         * missed close HTML tag. This case is special because parser prints error like
         * {@code "no viable alternative at input 'b \n *\n'"} and it is not clear that error is
         * about missed close HTML tag.
         * Other error messages are not special and logged simply as "Parse Error...".
         *
         * {@inheritDoc}
         */
        @Override
        public void syntaxError(
                Recognizer<?, ?> aRecognizer, Object aOffendingSymbol,
                int aLine, int aCharPositionInLine,
                String aMsg, RecognitionException aEx)
        {
            int lineNumber = mOffset + aLine;
            Token token = (Token) aOffendingSymbol;

            if (JAVADOC_MISSED_HTML_CLOSE.equals(aMsg)) {
                log(lineNumber, JAVADOC_MISSED_HTML_CLOSE, token.getText());
                throw new ParseCancellationException();
            }
            else if (JAVADOC_WRONG_SINGLETON_TAG.equals(aMsg)) {
                log(lineNumber, JAVADOC_WRONG_SINGLETON_TAG, token.getText());
                throw new ParseCancellationException();
            }
            else {
                log(lineNumber, "javadoc.parse.error", aMsg);
            }
        }
    }

}
