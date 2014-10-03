package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

public interface MutableDetailNode
{
    int getType();
    void setType(int type);

    int getLineNumber();
    void setLineNumber(int lineNumber);

    int getColumnNumber();
    void setColumnNumber(int columnNumber);

    DetailNode[] getChildren();
    void setChildren(DetailNode[] children);

    DetailNode getParent();
    void setParent(DetailNode parent);

    int getIndex();
    void setIndex(int index);
}
