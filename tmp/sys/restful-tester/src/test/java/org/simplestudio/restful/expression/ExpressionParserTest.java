package org.simplestudio.restful.expression;

import org.junit.Test;
import org.simplestudio.restful.httpclient.cache.FeatureCache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Assert;

public class ExpressionParserTest {

    static {
        FeatureCache.put("name", "testname");
        FeatureCache.put("age", 100);

        JSONObject json = new JSONObject();
        json.put("name", "张三");
        json.put("address", "厦门市");
        json.put("age", 20);
        FeatureCache.put("json", json);

        JSONArray array = new JSONArray();
        for (int i = 0; i < 3; i++) {
            json = new JSONObject();
            json.put("index", i);
            array.add(json);
        }
        FeatureCache.put("array", array);
    }

    @Test
    public void testNotExistKey() throws Exception {
        Assert.assertEquals(null, ExpressionParser.parse("${ffdss}"));
    }

    @Test
    public void testSimpleKey() throws Exception {
        Assert.assertEquals("testname", ExpressionParser.parse("${name}"));
        Assert.assertEquals(100, ExpressionParser.parse("${age}"));
        Assert.assertEquals("100 == 100", ExpressionParser.parse("${age} == 100"));
    }

    @Test
    public void testPropertiesKey() throws Exception {
        Assert.assertEquals("kuaidi100", ExpressionParser.parse("${moduleName}"));
    }

    @Test
    public void testJson() throws Exception {
        Object obj = ExpressionParser.parse("${json}");
        Assert.assertTrue(obj instanceof JSONObject);
        Assert.assertEquals("张三", ExpressionParser.parse("${json.name}"));
        Assert.assertEquals("\"张三\" == \"张三\"", ExpressionParser.parse("${json.name} == \"张三\""));
        Assert.assertEquals(20, ExpressionParser.parse("${json.age}"));
        Assert.assertEquals(null, ExpressionParser.parse("${json.age33}"));
    }

    @Test
    public void testJsonArray() throws Exception {
        Object obj = ExpressionParser.parse("${array}");
        Assert.assertTrue(obj instanceof JSONArray);
    }
}
