package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.JavadocAst;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class WriteAuthorCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {JavadocTokenTypes.JAVADOC, JavadocTokenTypes.JAVADOC_TAG_AUTHOR };
    }

    @Override
    public void visitJavadocToken(JavadocAst aAst)
    {
        switch (aAst.getType()) {

        case JavadocTokenTypes.JAVADOC:
            JavadocAST javadocAst = aAst.findFirstToken(JavadocTokenTypes.JAVADOC_TAG_SECTION);
            if (javadocAst == null
                    || javadocAst.findFirstToken(JavadocTokenTypes.JAVADOC_TAG_AUTHOR) == null)
            {
                log(aAst.getLineNo(), "author.missed");
            }
            break;

        case JavadocTokenTypes.JAVADOC_TAG_AUTHOR:
            JavadocAST nameText = aAst.findFirstToken(JavadocTokenTypes.NAME_TEXT);
            if (nameText == null
                    || nameText.getText() == null
                    || nameText.getText().isEmpty())
            {
                log(aAst.getLineNo(), "author.missed");
            }
            else {
                String name = nameText.getText();
                if (!Character.isUpperCase(name.charAt(0))) {
                    log(nameText.getLineNo(), "author.wrong");
                }
            }
            break;

        default:
            throw new IllegalArgumentException();
        }

    }

}
