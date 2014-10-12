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
import java.util.Random;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

public final class ExpectedParseTreeGenerator
{
    private static String mFolder = "src/test/resources/com/puppycrawl/tools/checkstyle/grammars/javadoc/";
    private static String mExtension = "txt";

    private ExpectedParseTreeGenerator()
    {
    }

    public static void main(String[] args)
        throws Exception
    {
        final String inputName = "javadocTags/AllJavadocInlineTags";

        String filename = mFolder + inputName + "." + mExtension;
        JavadocParseTreeTest test = new JavadocParseTreeTest();
        ParseTree generatedTree = test.parseJavadoc(JavadocParseTreeTest
                .getFileContent(new File(filename)));

        System.out.println("public static ParseTree tree_" + inputName
                + "()\n{");
        String id = walk(generatedTree, "null");
        System.out.println("    return " + id + ";\n}");
    }

    public static String walk(ParseTree t, String parentObjectName)
    {
        RandomString random = new RandomString(5);
        String className = t.getClass().getSimpleName();
        String id = random.nextString();

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
            }
            System.out.println("    CommonToken " + id
                    + " = new CommonToken(JavadocTokenTypes." + tokenType
                    + ", \"" + text + "\");");
        }
        else {
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

    static class RandomString
    {

        private static char[] symbols;

        static {
            StringBuilder tmp = new StringBuilder();
            for (char ch = 'A'; ch <= 'Z'; ++ch) {
                tmp.append(ch);
            }
            for (char ch = 'a'; ch <= 'z'; ++ch) {
                tmp.append(ch);
            }
            symbols = tmp.toString().toCharArray();
        }

        private final Random random = new Random();

        private final char[] buf;

        public RandomString(int length)
        {
            if (length < 1) {
                throw new IllegalArgumentException("length < 1: " + length);
            }
            buf = new char[length];
        }

        public String nextString()
        {
            for (int idx = 0; idx < buf.length; ++idx) {
                buf[idx] = symbols[random.nextInt(symbols.length)];
            }
            return new String(buf);
        }
    }

}
