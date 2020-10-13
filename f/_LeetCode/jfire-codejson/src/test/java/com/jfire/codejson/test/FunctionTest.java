package com.jfire.codejson.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import com.jfire.baseutil.reflect.TypeUtil;
import com.jfire.codejson.JsonObject;
import com.jfire.codejson.JsonTool;

public class FunctionTest extends Support
{
    
    @Test
    public void rightTest()
    {
        String string = JsonTool.write(data);
        logger.debug("输出的json是\n\n{}\n\n", string);
        Assert.assertTrue(data.equal(JsonTool.read(Data.class, string)));
        logger.debug("输出的数组json是\n\n{}\n\n", JsonTool.write(new Data[] { data, data }));
        Data[][] origin = new Data[][] { { data, data }, { data, data, data } };
        string = JsonTool.write(origin);
        Data[][] result = JsonTool.read(Data[][].class, string);
        for (int i = 0; i < origin.length; i++)
        {
            for (int j = 0; j < origin[i].length; j++)
            {
                Assert.assertTrue(origin[i][j].equal(result[i][j]));
            }
        }
        Data[] test1 = new Data[] { data, data };
        string = JsonTool.write(test1);
        Data[] test1Result = JsonTool.read(Data[].class, string);
        for (int i = 0; i < test1.length; i++)
        {
            Assert.assertTrue(test1[i].equal(test1Result[i]));
        }
        Data[][][] test2 = new Data[][][] { { { data, data }, { data } }, { { data, data, data, data }, { data }, { data } } };
        string = JsonTool.write(test2);
        Data[][][] test2Result = JsonTool.read(Data[][][].class, string);
        for (int i = 0; i < test2Result.length; i++)
        {
            for (int j = 0; j < test2Result[i].length; j++)
            {
                for (int k = 0; k < test2Result[i][j].length; k++)
                {
                    Assert.assertTrue(test2Result[i][j][k].equal(test2[i][j][k]));
                }
            }
        }
    }
    
    @Test
    public void typeTest()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("sdadasd");
        list.add("sdadsasda");
        String value = JsonTool.write(list);
        ArrayList<String> result = (ArrayList<String>) JsonTool.read(new TypeUtil<ArrayList<String>>() {}.getType(), value);
        Assert.assertTrue(list.equals(result));
        ArrayList<NestData> arrayList = new ArrayList<>();
        NestData cdata = new NestData();
        cdata.setAge(1212);
        cdata.setName("sdasdas");
        arrayList.add(cdata);
        cdata = new NestData();
        cdata.setAge(1212121);
        cdata.setName("dassdas");
        arrayList.add(cdata);
        value = JsonTool.write(arrayList);
        System.out.println(value);
        ArrayList<NestData> result1 = JsonTool.read(new TypeUtil<ArrayList<NestData>>() {}.getType(), value);
        Assert.assertTrue(arrayList.equals(result1));
        ArrayList<Data> list2 = new ArrayList<>();
        list2.add(data);
        list2.add(data);
        value = JsonTool.toString(list2);
        ArrayList<Data> result3 = JsonTool.read(new TypeUtil<ArrayList<Data>>() {}.getType(), value);
        Assert.assertTrue(list2.get(0).equal(result3.get(0)) && list2.get(1).equal(result3.get(1)));
        HashMap<String, Data> map = new HashMap<>();
        map.put("12wq", data);
        map.put("xczc", data);
        value = JsonTool.write(map);
        HashMap<String, Data> result4 = JsonTool.read(new TypeUtil<HashMap<String, Data>>() {}.getType(), value);
        Assert.assertTrue(map.get("12wq").equal(result4.get("12wq")));
        Assert.assertTrue(map.get("xczc").equal(result4.get("xczc")));
    }
    
    @Test
    public void fileTest() throws URISyntaxException, IOException
    {
        File configFile = new File(this.getClass().getClassLoader().getResource("config.json").toURI());
        FileInputStream inputStream = new FileInputStream(configFile);
        byte[] array = new byte[inputStream.available()];
        inputStream.read(array);
        String jsonStr = new String(array);
        System.out.println(jsonStr);
        JsonObject jsonObject = (JsonObject) JsonTool.fromString(jsonStr);
        System.out.println(JsonTool.write(jsonObject));
    }
}
