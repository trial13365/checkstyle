package com.puppycrawl.tools.checkstyle.checks.javadoc;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class SimpleJavadocCheckTest
        extends BaseCheckTestSupport
{
    private String filename = "src/test/resources/com/puppycrawl/tools/checkstyle/javadoc/simple.java";

    @Test
    public void test1()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(SimpleJavadocCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, filename, expected);
    }

}
