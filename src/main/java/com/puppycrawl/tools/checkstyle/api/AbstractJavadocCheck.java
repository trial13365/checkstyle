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
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocUtils;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocLexer;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

/**
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
     * Origin AST node of block comment.
     */
    private DetailAST mBlockCommentAst;

    /**
     * Process javadoc parse tree.
     *
     * @param aJavadoc parse tree.
     */
    public abstract void processJavadocParseTree(ParseTree aJavadoc);

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
    }

    @Override
    public boolean isCommentNodesRequired()
    {
        return true;
    }

    protected DetailAST getBlockCommentAst()
    {
        return mBlockCommentAst;
    }

    @Override
    public void visitToken(DetailAST aBlockCommentBegin)
    {
        mBlockCommentAst = aBlockCommentBegin;
        final String commentContent =
                JavadocUtils.getBlockCommentContent(aBlockCommentBegin);
        if (JavadocUtils.isJavadocComment(commentContent)) {
            final String javadocComment = commentContent.substring(1);
            mErrorListener.setOffset(aBlockCommentBegin.getLineNo() - 1);
            try {
                System.out.println("Parsing: "
                        + getFileContents().getFilename());
                final ParseTree parseTree = parseJavadoc(javadocComment);
                processJavadocParseTree(parseTree);
            }
            catch (ParseCancellationException e) {
                return;
            }
            catch (IOException e) {
                log(aBlockCommentBegin.getLineNo(), "javadoc.parse.error",
                        e.getMessage());
            }
        }
    }

    /**
     * Parses block comment content as javadoc comment.
     * @param aBlockComment block comment content.
     * @return parse tree
     * @throws IOException errors in ANTLRInputStream
     */
    private ParseTree parseJavadoc(String aBlockComment)
        throws IOException
    {
        final Charset utf8Charset = Charset.forName("UTF-8");
        final InputStream in = new ByteArrayInputStream(aBlockComment.getBytes(utf8Charset));

        final ANTLRInputStream input = new ANTLRInputStream(in);

        final JavadocLexer lexer = new JavadocLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(mErrorListener);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final JavadocParser parser = new JavadocParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(mErrorListener);
        parser.setErrorHandler(new BailErrorStrategy());

        return parser.javadoc();
    }

    /**
     * Custom error listener for JavadocParser that prints user readable errors.
     */
    class DescriptiveErrorListener extends BaseErrorListener
    {
        /**
         * Line number offset.
         */
        private int mOffset;

        /**
         * Set of error messages.
         */
        private Set<String> mErrorMessages;

        /**
         * Add default error messages.
         */
        public DescriptiveErrorListener()
        {
            super();

            mErrorMessages = new HashSet<String>();
            mErrorMessages.add("javadoc.missed.html.close");
            mErrorMessages.add("javadoc.missed.value.inline.tag.argument");
        }

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
            if (mErrorMessages.contains(aMsg)) {
                log(mOffset + aLine, aMsg);
            }
            else {
                log(mOffset + aLine, "javadoc.parse.error", aMsg);
            }
        }
    }

}
