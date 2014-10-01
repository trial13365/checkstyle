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
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.base.CaseFormat;
import com.google.common.primitives.Ints;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocAst;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.MutableDetailNode;
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

                final JavadocAst tree = convertParseTree2Ast(parseTree, null, null);

                processTree(tree);
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

    /**
     * Converts ParseTree to JavadocAST.
     * @param aNode
     *        ParseTree node
     * @param aParent
     *        JavadocAST parent
     * @param aPreviousSibling
     *        JavadocAST previous sibling
     * @return tree JavdocAST
     */
    private JavadocAst convertParseTree2Ast(ParseTree aNode, JavadocAst aParent, JavadocAst aPreviousSibling)
    {
        final JavadocAst nodeAst = createJavadocAstNode(aNode, aParent, aPreviousSibling);

        final int childCount = aNode.getChildCount();

        if (childCount > 0) {
            final ParseTree firstChild = aNode.getChild(0);
            final JavadocAst firstChildAst = convertParseTree2Ast(firstChild, nodeAst, null);

            nodeAst.setFirstChild(firstChildAst);

            JavadocAst previousAst = firstChildAst;
            for (int i = 1; i < childCount; i++) {
                final ParseTree nextChild = aNode.getChild(i);
                final JavadocAst nextChildAst = convertParseTree2Ast(nextChild, nodeAst, previousAst);

                previousAst = nextChildAst;
            }

        }

        return nodeAst;
    }

    public MutableDetailNode convert(ParseTree tree) {
        MutableDetailNode root = create(tree, null, -1);

        int childCount = tree.getChildCount();
        MutableDetailNode[] children = (MutableDetailNode[]) root.getChildren();

        for (int i = 0; i < childCount; i++) {
            MutableDetailNode child = create(tree.getChild(i), root, i);
            children[i] = child;
        }

        for (int i = 0; i < childCount; i++) {
            DetailNode parent = children[i];
            ParseTree subtree = tree.getChild(i);

            int cnt = subtree.getChildCount();
            MutableDetailNode[] subChildren = (MutableDetailNode[]) parent.getChildren();

            for (int j = 0; j < cnt; j++) {
                MutableDetailNode child = create(tree.getChild(i), parent, i);
                subChildren[i] = child;
            }
        }


        return null;
    }

    private MutableDetailNode create(ParseTree parseTree, DetailNode parent, int index) {
        MutableDetailNode node = new JavadocNodeImpl();
        node.setColumnNumber(getColumn(parseTree));
        node.setLineNumber(getLine(parseTree));
        node.setIndex(index);
        node.setType(getTokenType(parseTree));
        node.setParent(parent);
        node.setChildren(new MutableDetailNode[parseTree.getChildCount()]);
        return node;
    }

    private int getTokenType(ParseTree aNode) {
        int tokenType = Integer.MIN_VALUE;

        if (aNode instanceof TerminalNode) {
            tokenType = ((TerminalNode) aNode).getSymbol().getType();
        }
        else {
            final String className = getNodeClassNameWithoutContext(aNode);
            final String typeName =
                    CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, className);
            tokenType = JavadocTokenTypes.getTokenId(typeName);
        }

        return tokenType;
    }

    /**
     * Creates JavadocAST node from ParseTree node.
     *
     * @param aNode ParseTree node
     * @param aParentAst parent of created JavadocAST node
     * @param aPreviousSibling previous sibling to created node
     * @return JavadocAST node
     */
    private JavadocAst createJavadocAstNode(ParseTree aNode, JavadocAst aParentAst
            , JavadocAst aPreviousSibling)
    {
        final JavadocAst ast = new JavadocAst();
        ast.setParent(aParentAst);
        ast.setColumnNumber(getColumn(aNode));
        ast.setLineNumber(getLine(aNode) + mBlockCommentAst.getLineNo());
        ast.setChildCount(aNode.getChildCount());
        ast.setText(aNode.getText());

        if (aPreviousSibling != null) {
            ast.setPreviousSibling(aPreviousSibling);
            aPreviousSibling.setNextSibling(ast);
        }

        int tokenId = -1;

        if (aNode instanceof TerminalNode) {
            tokenId = ((TerminalNode) aNode).getSymbol().getType();
        }
        else {
            final String className = getNodeClassNameWithoutContext(aNode);
            final String typeName =
                    CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, className);
            tokenId = JavadocTokenTypes.getTokenId(typeName);
        }

        ast.setType(tokenId);

        return ast;
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
            JavadocAst toVisit = curNode.getFirstChild();
            while ((curNode != null) && (toVisit == null)) {

                if (waitsFor) {
                    leaveJavadocToken(curNode);
                }

                toVisit = curNode.getNextSibling();
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
         * Offset is line number of beginning of the Javadoc comment.
         * Log messages should have line number in scope of file, not in scope of Javadoc comment.
         */
        private int mOffset;

        /**
         * Set of known error messages that could be occurred while parsing Javadoc comment.
         */
        private final Set<String> mErrorMessages;

        /**
         * Adds default error messages.
         */
        public DescriptiveErrorListener()
        {
            super();

            mErrorMessages = new HashSet<String>();
            mErrorMessages.add("javadoc.missed.html.close");
        }

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

        @Override
        public void syntaxError(
                Recognizer<?, ?> aRecognizer, Object aOffendingSymbol,
                int aLine, int aCharPositionInLine,
                String aMsg, RecognitionException aEx)
        {
            // if message is error code from collection of known errors
            if (mErrorMessages.contains(aMsg)) {
                log(mOffset + aLine, aMsg);
            }
            // else print general error message
            else {
                log(mOffset + aLine, "javadoc.parse.error", aMsg);
            }
        }
    }

}
