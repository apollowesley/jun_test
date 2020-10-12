package org.simplestudio.restful.expression;

import org.junit.Test;
import org.simplestudio.restful.httpclient.cache.FeatureCache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Assert;

public class ExecuteEngineTest {

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
    public void testSimpleExecute() throws Exception {
        String expression = "1 == 1";
        Assert.assertTrue(ExecuteEngine.execute(expression));

        expression = "1 == 2";
        Assert.assertFalse(ExecuteEngine.execute(expression));

        expression = "\"abc\" == \"abc\"";
        Assert.assertTrue(ExecuteEngine.execute(expression));

        expression = "\"abc\" == \"abcd\"";
        Assert.assertFalse(ExecuteEngine.execute(expression));
    }

    @Test
    public void testComplexExecute() throws Exception {
        Assert.assertTrue(ExecuteEngine.execute("${name} == \"testname\""));
        Assert.assertTrue(ExecuteEngine.execute("${json.name} == \"张三\""));
        Assert.assertTrue(ExecuteEngine.execute("${age} == 100"));
        Assert.assertTrue(ExecuteEngine.execute("${json.age} == 20"));
        Assert.assertFalse(ExecuteEngine.execute("${json.age2} == 20"));
    }
}
