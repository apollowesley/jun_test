package org.simplestudio.restful.util;

import org.junit.Test;

import junit.framework.Assert;

public class PropertyUtilTest {

    @Test
    public void testLoadResource() {
        //验证common.properties的加载
        String moduleName = PropertyUtil.getProperty("moduleName");
        Assert.assertNotNull(moduleName);
        Assert.assertEquals("kuaidi100", moduleName);
    }

}
