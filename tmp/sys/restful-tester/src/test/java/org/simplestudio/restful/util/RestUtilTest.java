package org.simplestudio.restful.util;

import org.junit.Test;

import junit.framework.Assert;

public class RestUtilTest {

    @Test
    public void testGetPackagePath() {
        String path = RestUtil.getPackagePath(RestUtilTest.class);

        Assert.assertEquals("org/simplestudio/restful/util", path);
    }

    @Test
    public void testTrimQuote() {
        String s = null;
        Assert.assertEquals(null, RestUtil.trimQuote(s));
        s = "aa\"bb\"";
        Assert.assertEquals("aabb", RestUtil.trimQuote(s));
    }

}
