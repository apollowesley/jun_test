package org.simplestudio.restful.expression;

import org.junit.Test;
import org.simplestudio.restful.exception.RestException;

import junit.framework.Assert;

public class NestedParamHelperTest {

    @Test
    public void testNoExistParam() {
        String key = "test";
        try {
            NestedParamHelper.get(key);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof RestException);
            Assert.assertEquals("[" + key + "]未定义,请确认定义之后再使用！", e.getMessage());
        }
    }

    @Test
    public void testGetRandomStr() throws Exception {
        String randomStr = NestedParamHelper.get("随机串").toString();
        Assert.assertTrue(randomStr.length() == 5);
        randomStr = NestedParamHelper.get("随机串.100").toString();
        Assert.assertTrue(randomStr.length() == 100);
    }

    @Test
    public void testGetDate() throws Exception {
        String dateStr = NestedParamHelper.get("当前时间").toString();
        Assert.assertEquals(10, dateStr.length());
        dateStr = NestedParamHelper.get("当前时间.yyyy-MM-dd hh:mm:ss").toString();
        Assert.assertEquals(19, dateStr.length());
    }

}
