package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class SingleLineJavadocCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] { JavadocTokenTypes.JAVADOC };
    }

    @Override
    public void visitJavadocToken(DetailNode aAst)
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

    private boolean hasJavadocTags(DetailNode aJavadocAst)
    {
        DetailNode javadocTagSection =
                JavadocUtils.findFirstToken(aJavadocAst, JavadocTokenTypes.JAVADOC_TAG_SECTION);
        return javadocTagSection != null;
    }

    private boolean hasJavadocInlineTags(DetailNode aAst)
    {
        return JavadocUtils.branchContains(aAst, JavadocTokenTypes.JAVADOC_INLINE_TAG);
    }

}
