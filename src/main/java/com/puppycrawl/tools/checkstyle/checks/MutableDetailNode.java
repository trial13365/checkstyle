package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

public interface MutableDetailNode
{
    void setType(int type);
    void setText(String text);
    void setLineNumber(int lineNumber);
    void setColumnNumber(int columnNumber);
    void setChildren(DetailNode[] children);
    void setParent(DetailNode parent);
    void setIndex(int index);
}
