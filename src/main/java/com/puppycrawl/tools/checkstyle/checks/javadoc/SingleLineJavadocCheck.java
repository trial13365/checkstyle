package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.JavadocAst;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class SingleLineJavadocCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] { JavadocTokenTypes.JAVADOC };
    }

    @Override
    public void visitJavadocToken(JavadocAst aAst)
    {
        if (isSingleLineJavadoc()
                && (hasJavadocTags(aAst) || hasJavadocInlineTags(aAst))) {
            log(aAst.getLineNumber(), "singleline.javadoc");
        }
    }

    private boolean isSingleLineJavadoc()
    {
        DetailAST blockCommentStart = getBlockCommentAst();
        DetailAST blockCommentEnd = blockCommentStart.getLastChild();

        return blockCommentStart.getLineNo() == blockCommentEnd.getLineNo();
    }

    private boolean hasJavadocTags(JavadocAst aJavadocAst)
    {
        JavadocAst javadocTagSection =
                JavadocUtils.findFirstToken(aJavadocAst, JavadocTokenTypes.JAVADOC_TAG_SECTION);
        return javadocTagSection != null;
    }

    private boolean hasJavadocInlineTags(JavadocAst aAst)
    {
        return JavadocUtils.branchContains(aAst, JavadocTokenTypes.JAVADOC_INLINE_TAG);
    }

}
