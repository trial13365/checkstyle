package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

public class TreeBuilder
{

    private static final String PREFIX = "|--";

    /**
     * @param aRoot
     * @param aLevel
     */
    public static void buildTree(DetailNode aRoot)
    {

        if (aRoot.getChildren().length != 0) {
            for (DetailNode node : aRoot.getChildren()) {
                System.out.println(getLevel(node) + node.toString()
                        + " : ["
                        + node.getText().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t")
                        + "]");
                buildTree(node);
            }
        }
    }

    /**
     * @param aNode
     * @return
     */
    private static String getLevel(DetailNode aNode)
    {
        DetailNode parent = aNode;
        String level = "|";
        while (parent.getParent() != null) {
            parent = parent.getParent();
            if (parent.getParent() != null) {
                level = level + "    ";
            }
            else {
                level = level + PREFIX;
            }
        }
        return level;
    }
}
