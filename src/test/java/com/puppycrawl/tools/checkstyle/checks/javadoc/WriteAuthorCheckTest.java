package com.puppycrawl.tools.checkstyle.checks.javadoc;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class WriteAuthorCheckTest
        extends BaseCheckTestSupport
{
    @Test
    public void test1()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(WriteAuthorCheck.class);
        final String[] expected = {
                "1: Where is author name?",
                "11: Where is author name?",
                "17: Write author name with a capital letter!",
        };
        verify(checkConfig, getPath("javadoc/InputWriteAuthorCheck.java"), expected);
    }

}
