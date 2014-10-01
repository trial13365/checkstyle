package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

public interface MutableDetailNode extends DetailNode
{
    void setIndex(int index);
    void setType(int type);
    void setLineNumber(int lineNumber);
    void setColumnNumber(int columnNumber);
    void setChildren(DetailNode[] children);
    void setParent(DetailNode parent);
}
