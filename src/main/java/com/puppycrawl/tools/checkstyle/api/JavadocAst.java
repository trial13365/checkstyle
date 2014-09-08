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


/**
 * Class that represents abstract syntax tree (AST) of parsed Javadoc comment.
 * Each node in tree is JavadocAST as well. Implementation of JavadocAST is based
 * on DetailAST that is used for parsing java code.
 *
 * @author Baratali Izmailov
 * @see DetailAST
 *
 */
public final class JavadocAst
{
    /** constant to indicate non-initialized fields */
    private static final int NOT_INITIALIZED = Integer.MIN_VALUE;

    /** type */
    private int mType = NOT_INITIALIZED;

    /** the line number **/
    private int mLineNumber = NOT_INITIALIZED;
    /** the column number **/
    private int mColumnNumber = NOT_INITIALIZED;

    /** number of children */
    private int mChildCount = NOT_INITIALIZED;
    /** the parent token */
    private JavadocAst mParent;
    /** previous sibling */
    private JavadocAst mPreviousSibling;
    /** next sibling */
    private JavadocAst mNextSibling;
    /** first child */
    private JavadocAst mFirstChild;
    /** last child */
    private JavadocAst mLastChild;

    /** text */
    private String mText;

    public int getType()
    {
        return mType;
    }

    public JavadocAst getFirstChild()
    {
        return mFirstChild;
    }

    public JavadocAst getNextSibling()
    {
        return mNextSibling;
    }

    public JavadocAst getPreviousSibling()
    {
        return mPreviousSibling;
    }

    public int getChildCount()
    {
        return mChildCount;
    }

    public JavadocAst getParent()
    {
        return mParent;
    }

    public int getLineNumber()
    {
        return mLineNumber;
    }

    public int getColumnNumber()
    {
        return mColumnNumber;
    }

    public String getText()
    {
        return mText;
    }

    /**
     * Gets last child.
     * @return last child.
     */
    public JavadocAst getLastChild()
    {
        if (mLastChild == null) {
            mLastChild = getFirstChild();
            while ((mLastChild != null) && (mLastChild.getNextSibling() != null)) {
                mLastChild = mLastChild.getNextSibling();
            }
        }
        return mLastChild;
    }

    protected void setType(int aType)
    {
        mType = aType;
    }

    protected void setFirstChild(JavadocAst aFirstChild)
    {
        mFirstChild = aFirstChild;
    }

    protected void setNextSibling(JavadocAst aNextSibling)
    {
        mNextSibling = aNextSibling;
    }

    protected void setPreviousSibling(JavadocAst aPreviousSibling)
    {
        mPreviousSibling = aPreviousSibling;
    }

    protected void setChildCount(int aChildCount)
    {
        mChildCount = aChildCount;
    }

    protected void setParent(JavadocAst aParent)
    {
        mParent = aParent;
    }

    protected void setLineNumber(int aLineNumber)
    {
        mLineNumber = aLineNumber;
    }

    protected void setColumnNumber(int aColumnNumber)
    {
        mColumnNumber = aColumnNumber;
    }

    protected void setText(String aText)
    {
        mText = aText;
    }

    @Override
    public String toString()
    {
        return JavadocTokenTypes.getTokenName(getType())
                + "[" + getLineNumber() + "x" + getColumnNumber() + "]";
    }

}
