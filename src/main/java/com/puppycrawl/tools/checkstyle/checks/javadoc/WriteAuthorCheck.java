package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class WriteAuthorCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {JavadocTokenTypes.JAVADOC, JavadocTokenTypes.JAVADOC_TAG_AUTHOR };
    }

    @Override
    public void visitJavadocToken(DetailNode aAst)
    {
        switch (aAst.getType()) {

        case JavadocTokenTypes.JAVADOC:
            DetailNode javadocAst = JavadocUtils.findFirstToken(aAst, JavadocTokenTypes.JAVADOC_TAG_SECTION);
            if (javadocAst == null
                    || JavadocUtils.findFirstToken(javadocAst, JavadocTokenTypes.JAVADOC_TAG_AUTHOR) == null)
            {
                log(aAst.getLineNumber(), "author.missed");
            }
            break;

        case JavadocTokenTypes.JAVADOC_TAG_AUTHOR:
            DetailNode nameText = JavadocUtils.findFirstToken(aAst, JavadocTokenTypes.DESCRIPTION);
            if (nameText == null
                    || nameText.getText() == null
                    || nameText.getText().isEmpty())
            {
                log(aAst.getLineNumber(), "author.missed");
            }
            else {
                String name = nameText.getText();
                if (!Character.isUpperCase(name.charAt(0))) {
                    log(nameText.getLineNumber(), "author.wrong");
                }
            }
            break;

        default:
            throw new IllegalArgumentException();
        }

    }

}
