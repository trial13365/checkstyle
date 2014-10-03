package com.puppycrawl.tools.checkstyle.api;

public interface DetailNode
{
    int getType();
    int getLineNumber();
    int getColumnNumber();
    DetailNode[] getChildren();
    DetailNode getParent();
    int getIndex();
}
