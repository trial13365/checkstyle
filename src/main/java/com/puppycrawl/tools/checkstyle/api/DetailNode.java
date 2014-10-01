package com.puppycrawl.tools.checkstyle.api;

public interface DetailNode
{
    int getIndex();
    int getType();
    int getLineNumber();
    int getColumnNumber();
    DetailNode[] getChildren();
    DetailNode getParent();
}
