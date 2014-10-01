package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.MutableDetailNode;

public class JavadocNodeImpl implements MutableDetailNode
{
    private int index;
    private int type;
    private int lineNumber;
    private int columnNumber;
    private DetailNode[] children;
    private DetailNode parent;

    @Override
    public int getIndex()
    {
        return index;
    }

    @Override
    public int getType()
    {
        return type;
    }

    @Override
    public int getLineNumber()
    {
        return lineNumber;
    }

    @Override
    public int getColumnNumber()
    {
        return columnNumber;
    }

    @Override
    public DetailNode[] getChildren()
    {
        return children;
    }

    @Override
    public DetailNode getParent()
    {
        return parent;
    }

    @Override
    public void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    @Override
    public void setColumnNumber(int columnNumber)
    {
        this.columnNumber = columnNumber;
    }

    @Override
    public void setChildren(DetailNode[] children)
    {
        this.children = children;
    }

    @Override
    public void setParent(DetailNode parent)
    {
        this.parent = parent;
    }

    @Override
    public String toString()
    {
        return JavadocTokenTypes.getTokenName(getType())
                + "[" + getLineNumber() + "x" + getColumnNumber() + "]";
    }

}
