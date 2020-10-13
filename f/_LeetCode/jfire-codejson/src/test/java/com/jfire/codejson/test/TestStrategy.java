package com.jfire.codejson.test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import com.jfire.baseutil.collection.StringCache;
import com.jfire.baseutil.simplelog.ConsoleLogFactory;
import com.jfire.baseutil.simplelog.Logger;
import com.jfire.codejson.JsonTool;
import com.jfire.codejson.function.WriterAdapter;
import com.jfire.codejson.strategy.WriteStrategy;
import com.jfire.codejson.test.simple.Home;
import com.jfire.codejson.test.simple.Person;
import com.jfire.codejson.test.strategy.BaseData;
import com.jfire.codejson.test.strategy.DateInfo;
import com.jfire.codejson.test.strategy.FunctionData;
import com.jfire.codejson.test.strategy.FunctionData2;
import com.jfire.codejson.test.strategy.FunctionData3;
import com.jfire.codejson.test.strategy.FunctionData4;
import com.jfire.codejson.test.strategy.FunctionData5;
import com.jfire.codejson.test.strategy.FunctionData6;
import com.jfire.codejson.test.strategy.NestInfo;

public class TestStrategy
{
    private Logger           logger = ConsoleLogFactory.getLogger();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private DateInfo         info;
    
    public TestStrategy() throws ParseException
    {
        info = new DateInfo();
        Date[] dates = new Date[] { format.parse("2015-11-14 18:00:00"), format.parse("2015-11-14 18:00:00") };
        info.setDates(dates);
        info.setDate(format.parse("2015-11-14 18:00:00"));
        NestInfo nestInfo = new NestInfo();
        nestInfo.setDate(format.parse("2015-11-14 18:00:00"));
        info.setNestInfo(nestInfo);
    }
    
    @Test
    public void testClass() throws ParseException
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addWriter(Date.class, new WriterAdapter() {
            private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            @Override
            public void write(Object target, StringCache cache)
            {
                cache.append('\"').append(format.format((Date) target)).append('\"');
            }
        });
        
        logger.info(strategy.write(info));
        String result = "{\"d\":2.3569,\"date\":\"2015-11-14\",\"dates\":[\"2015-11-14\",\"2015-11-14\"],\"nestInfo\":{\"date\":\"2015-11-14\"}}";
        assertEquals(result, strategy.write(info));
    }
    
    @Test
    public void testIgnoreAndRename()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addIgnoreField("com.jfire.codejson.test.strategy.DateInfo.dates");
        strategy.addRenameField("com.jfire.codejson.test.strategy.DateInfo.date", "date_key");
        String expect = "{\"d\":2.3569,\"date_key\":\"2015-11-14 00:00:00\",\"nestInfo\":{\"date\":\"2015-11-14 00:00:00\"}}";
        assertEquals(expect, strategy.write(info));
    }
    
    @Test
    public void testBaseFormatAndNameStrategy()
    {
        WriteStrategy strategy = new WriteStrategy();
        // 这样对所有的double输出都格式化
        strategy.addWriter(double.class, new WriterAdapter() {
            public void write(double target, StringCache cache)
            {
                DecimalFormat format = new DecimalFormat("##.00");
                cache.append(format.format(target));
            }
        });
        strategy.addFieldStrategy("com.jfire.codejson.test.strategy.BaseData.percent", new WriterAdapter() {
            public void write(double target, StringCache cache)
            {
                DecimalFormat format = new DecimalFormat("##.00%");
                cache.append('"').append(format.format(target / 100)).append('"');
            }
        });
        String except = "{\"a\":2.2365,\"b\":15.69,\"percent\":\"88.81%\"}";
        assertEquals(except, strategy.write(new BaseData()));
    }
    
    @Test
    public void testNest()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addRenameField("com.jfire.codejson.test.simple.Person.name", "myname");
        strategy.addFieldStrategy("com.jfire.codejson.test.simple.Person.age", new WriterAdapter() {
            public void write(int target, StringCache cache)
            {
                cache.append(20);
            }
        });
        System.out.println(strategy.write(new Home()));
    }
    
    @Test
    public void testFunction()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addFieldStrategy("com.jfire.codejson.test.strategy.FunctionData.map", new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append(((Map) entity).size());
            }
        });
        strategy.addWriter(String.class, new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append("\"$").append((String) entity).append("$\"");
            }
        });
        strategy.addWriter(Date.class, new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                cache.append('"').append(format.format((Date) entity)).append('"');
            }
        });
        String expect = "{\"map\":1,\"map2\":{\"$test$\":\"$test$\"},\"map3\":{\"1\":\"2015-11-15\"}}";
        assertEquals(expect, strategy.write(new FunctionData()));
    }
    
    @Test
    public void testFunction2()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addWriter(Date.class, new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                cache.append('"').append(format.format((Date) entity)).append('"');
            }
        });
        String except = "{\"map\":{\"test\":\"test\"},\"map2\":{\"test\":\"test\"},\"map3\":{\"1\":\"2015-11-15\"}}";
        assertEquals(except, strategy.write(new FunctionData()));
    }
    
    @Test
    public void testFunction3()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addFieldStrategy("com.jfire.codejson.test.strategy.FunctionData2.age", new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append(20);
            }
        });
        System.out.println(strategy.write(new FunctionData2()));
        assertEquals("{\"age\":20,\"name\":\"林斌\"}", strategy.write(new FunctionData2()));
        strategy = new WriteStrategy();
        strategy.write(new FunctionData2());
    }
    
    @Test
    public void testFunction4()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addFieldStrategy("com.jfire.codejson.test.simple.Home.person", new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                Person person = (Person) entity;
                cache.append('"').append(person.getName()).append(',').append(person.getAge()).append('"');
            }
        });
        assertEquals("{\"length\":50.26,\"person\":\"林斌,25\",\"wdith\":12.36}", strategy.write(new Home()));
    }
    
    @Test
    public void testFunction5()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addFieldStrategy("com.jfire.codejson.test.strategy.FunctionData3.list", new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append(((List) entity).size());
            }
        });
        assertEquals("{\"list\":2}", strategy.write(new FunctionData3()));
        assertEquals("{\"list\":[\"hello1\",\"hello2\"]}", JsonTool.write(new FunctionData3()));
        strategy = new WriteStrategy();
        strategy.addWriter(String.class, new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append("\"$").append((String) entity).append('"');
            }
        });
        assertEquals("{\"list\":[\"$hello1\",\"$hello2\"]}", strategy.write(new FunctionData3()));
    }
    
    @Test
    public void testFunction6()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addWriter(String.class, new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append('"').append('$').append((String) entity).append('"');
            }
        });
        String expect = "{\"b\":false,\"bb\":110,\"c\":\"H\",\"d\":2.3569,\"f\":2.232,\"i\":2231231,\"l\":12213123123,\"s\":12312,\"ss\":\"$林斌\",\"sss\":\"$sdadas\"}";
        assertEquals(expect, strategy.write(new FunctionData4()));
    }
    
    @Test
    public void testFunction7()
    {
        WriteStrategy strategy = new WriteStrategy();
        strategy.addWriter(String.class, new WriterAdapter() {
            public void write(Object entity, StringCache cache)
            {
                cache.append('"').append('$').append((String) entity).append('"');
            }
        });
        String expect = "{\"listArrays\":[[\"dsads\",\"dsadssdsasdas\"],[\"ds1212s\",\"d121212dsasdas\"]]}";
        assertEquals(expect, strategy.write(new FunctionData5()));
    }
    
    @Test
    public void testFunction8()
    {
        WriteStrategy strategy = new WriteStrategy();
        assertEquals("{\"maps\":[{\"test\":\"test\"},{\"abc\":\"def\"}]}", strategy.write(new FunctionData6()));
    }
    
    public void testFunction9(){
        WriteStrategy strategy = new WriteStrategy();
        
    }
}
