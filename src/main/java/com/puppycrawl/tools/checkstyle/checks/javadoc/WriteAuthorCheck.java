package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class WriteAuthorCheck extends AbstractJavadocCheck
{
    private boolean mAuthorFound;

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {JavadocTokenTypes.JAVADOC_TAG_AUTHOR_LITERAL };
    }

    @Override
    public void visitJavadocToken(DetailNode aAuthorLiteral)
    {
        mAuthorFound = true;

        DetailNode nameText = JavadocUtils.findFirstToken(aAuthorLiteral.getParent()
                , JavadocTokenTypes.DESCRIPTION);
        if (nameText == null
                || nameText.getText() == null
                || nameText.getText().isEmpty())
        {
            log(aAuthorLiteral.getLineNumber(), "author.missed");
        }
        else {
            String name = nameText.getText();
            if (!Character.isUpperCase(name.charAt(0))) {
                log(nameText.getLineNumber(), "author.wrong");
            }
        }

    }

    @Override
    public void finishJavadocTree(DetailNode aRootAst)
    {
        if (!mAuthorFound) {
            log(aRootAst.getLineNumber(), "author.missed");
        }
    }
}
