package com.jfire.codejson.test;

import org.junit.Ignore;
import org.junit.Test;
import com.alibaba.fastjson.JSON;
import com.jfire.baseutil.time.Timewatch;
import com.jfire.codejson.JsonTool;
import com.jfire.codejson.test.simple.Home;
import com.jfire.json.JsonUtil;

public class SpeedTest extends Support
{
    @Test
    @Ignore
    public void writeSpeedTest()
    {
        logger.debug("codejson的输出是\n\n{}\n\n", JsonTool.write(data));
        logger.debug("jfirejson的输出是\n\n{}\n\n", JsonUtil.toJsonString(data));
        Timewatch timewatch = new Timewatch();
        int count = 100000;
        timewatch.start();
        for (int i = 0; i < count; i++)
        {
            JSON.toJSONString(data);
        }
        timewatch.end();
        logger.info("fastjson输出耗时：{}", timewatch.getTotal());
        timewatch.start();
        for (int i = 0; i < count; i++)
        {
            JsonTool.write(data);
        }
        timewatch.end();
        logger.info("codejson输出耗时：{}", timewatch.getTotal());
        timewatch.start();
        for (int i = 0; i < count; i++)
        {
            JsonUtil.toJsonString(data);
        }
        timewatch.end();
        logger.info("jfirejson输出耗时：{}", timewatch.getTotal());
    }
    
    @Test
    @Ignore
    public void writeSpeedTest2()
    {
        Home home = new Home();
        Timewatch timewatch = new Timewatch();
        int count = 1000000;
        JsonTool.write(home);
        JsonUtil.toString(home);
        timewatch.start();
        for (int i = 0; i < count; i++)
        {
            JSON.toJSONString(home);
        }
        timewatch.end();
        logger.info("简单测试fastjson输出耗时：{}", timewatch.getTotal());
        timewatch.start();
        for (int i = 0; i < count; i++)
        {
            JsonUtil.toJsonString(home);
        }
        timewatch.end();
        logger.info("简单测试jfirejson输出耗时：{}", timewatch.getTotal());
        timewatch.start();
        for (int i = 0; i < count; i++)
        {
            JsonTool.write(home);
        }
        timewatch.end();
        logger.info("简单测试codejson输出耗时：{}", timewatch.getTotal());
        
    }
    
}
