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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import com.google.common.base.CaseFormat;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParseTreeTest;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * Generates source code of methods that build ParseTree of given Javadoc comments.
 * List text files in 'inputNames' array. Only one Javadoc comment should be in each file.
 * A text file should not contain '/*' at the begining and '*&#47;' at the end.
 */
public final class ExpectedParseTreeGenerator
{
    private static String mFolder = "src/test/resources/com/puppycrawl/tools/checkstyle/grammars/javadoc/";
    private static String[] inputNames = {
        "/InputLeadingAsterisks.txt",
        "/htmlTags/InputAttributeValueWithoutQuotes.txt",
        "/htmlTags/InputClosedOtherTag.txt",
        "/htmlTags/InputComments.txt",
        "/htmlTags/InputHtmlTagsInParagraph.txt",
        "/htmlTags/InputListWithUnclosedItemInUnclosedParagraph.txt",
        "/htmlTags/InputMixedCaseOfHtmlTags.txt",
        "/htmlTags/InputNegativeNumberInAttribute.txt",
        "/htmlTags/InputOneSimpleHtmlTag.txt",
        "/htmlTags/InputUnclosedAndClosedParagraphs.txt",
        "/htmlTags/InputUnclosedParagraphFollowedByJavadocTag.txt",
        "/javadocTags/InputAllJavadocInlineTags.txt",
        "/javadocTags/InputAllStandardJavadocTags.txt",
        "/javadocTags/InputAsteriskInJavadocInlineTag.txt",
        "/javadocTags/InputAsteriskInLiteral.txt",
        "/javadocTags/InputAuthorWithMailto.txt",
        "/javadocTags/InputCustomJavadocTags.txt",
        "/javadocTags/InputDocRootInheritDoc.txt",
        "/javadocTags/InputDollarInLink.txt",
        "/javadocTags/InputFewWhiteSpacesAsSeparator.txt",
        "/javadocTags/InputInnerBracesInCodeTag.txt",
        "/javadocTags/InputJavadocTagDescriptionWithInlineTags.txt",
        "/javadocTags/InputLinkInlineTags.txt",
        "/javadocTags/InputNewlineAndAsteriskInParameters.txt",
        "/javadocTags/InputParamWithGeneric.txt",
        "/javadocTags/InputSeeReferenceWithFewNestedClasses.txt",
        "/javadocTags/InputSerial.txt",
        "/javadocTags/InputSince.txt",
        "/javadocTags/InputTextBeforeJavadocTags.txt",
        "/javadocTags/InputTwoLinkTagsInRow.txt"
    };

    Map<String, Integer> variableCounters = new HashMap<>();

    private ExpectedParseTreeGenerator()
    {
    }

    public static void main(String[] args)
        throws Exception
    {
        ExpectedParseTreeGenerator generator = new ExpectedParseTreeGenerator();
        for (String inputName: inputNames) {
            File file = new File(mFolder + inputName);
            JavadocParseTreeTest test = new JavadocParseTreeTest();
            ParseTree generatedTree = test.parseJavadoc(JavadocParseTreeTest
                    .getFileContent(file));

            String filename = file.getName();
            String treeName = filename;
            String inputWord = "Input";
            if (filename.indexOf(inputWord) >= 0) {
                treeName = filename.substring(filename.indexOf(inputWord) + inputWord.length());
                if (treeName.lastIndexOf('.') >= 0) {
                    treeName = treeName.substring(0, treeName.lastIndexOf('.'));
                }
            }

            System.out.println("public static ParseTree tree" + treeName
                    + "()\n{");
            String id = generator.walk(generatedTree, "null");
            generator.resetCounter();
            System.out.println("    return " + id + ";\n}\n");
        }
    }

    private void resetCounter() {
        variableCounters.clear();
    }

    public String walk(ParseTree t, String parentObjectName)
    {
        String className = t.getClass().getSimpleName();
        String id = null;

        if (t instanceof TerminalNode) {
            TerminalNodeImpl terminal = (TerminalNodeImpl) t;
            int type = terminal.symbol.getType();
            String tokenType = "";
            if (type == -1) {
                tokenType = "EOF";
            }
            else {
                tokenType = JavadocUtils.getTokenName(type);
            }
            String text = terminal.getText();
            if ("\n".equals(text)) {
                text = "\\n";
            }
            else if ("\t".equals(text)) {
                text = "\\t";
            } else {
                text = text.replace("\"", "\\\"");
            }

            int number = getVariableCounter(tokenType);

            id = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tokenType.toLowerCase()) + number;

            System.out.println("    CommonToken " + id
                    + " = new CommonToken(JavadocTokenTypes." + tokenType
                    + ", \"" + text + "\");");
        }
        else {
            int number = getVariableCounter(className);

            id = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, className) + number++;

            System.out.println("    " + className + " " + id + " = new "
                    + className + "(" + parentObjectName + ", 0);");

            int n = t.getChildCount();
            for (int i = 0; i < n; i++) {
                String childId = walk(t.getChild(i), id);
                System.out.println("    " + id + ".addChild(" + childId + ");");
            }
        }
        return id;
    }

    private int getVariableCounter(String className) {
        int  number = 0;
        if (variableCounters.containsKey(className)) {
            number = variableCounters.get(className) + 1;
        }

        variableCounters.put(className, number);

        return number;
    }

}
