package com.puppycrawl.tools.checkstyle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class A
{

    public static void main(String[] args) throws Exception
    {
        BufferedReader r = new BufferedReader(new FileReader(new File("/home/bizmailov/git/jdk8/target/site/checkstyle.html")));
        String line = r.readLine();
        int n = 1;
        int i = 0;
        while(line != null) {
            line = line.replaceAll("<div class=\"section\">", "\n<div class=\"section\">\n");
            String[] lines = line.split("\n");
            for (String str: lines) {
                System.out.print(str);
                if (str.contains("<div class=\"section\">")) {
                    i++;
                    if (i > 4) {
                        System.out.print("<h1>(" + n++ + ")</h1>");
                    }
                }
                System.out.println();
            }
            line = r.readLine();
        }
    }

}
