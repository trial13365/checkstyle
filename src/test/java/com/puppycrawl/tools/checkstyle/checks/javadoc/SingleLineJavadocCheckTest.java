package com.puppycrawl.tools.checkstyle.checks.javadoc;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class SingleLineJavadocCheckTest
        extends BaseCheckTestSupport
{
    @Test
    public void test1()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(SingleLineJavadocCheck.class);
        final String[] expected = {
                "1: Single-line Javadoc comment should be multi-line",
                "7: Single-line Javadoc comment should be multi-line",
        };
        verify(checkConfig, getPath("javadoc/InputSingleLineJavadocCheck.java"), expected);
    }

}
