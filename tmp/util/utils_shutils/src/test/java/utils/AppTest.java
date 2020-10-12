package utils;

import com.dtdream.rdic.saas.def.AbstractLogger;
import com.dtdream.rdic.saas.def.Result;
import com.dtdream.rdic.saas.def.StdRet;
import com.dtdream.rdic.saas.service.ServiceList;
import com.dtdream.rdic.saas.utils.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    protected Logger logger = LoggerFactory.getLogger(AppTest.class);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testTs () {
        for (int i = 0; i < 1000; ++ i) {
            System.out.println(IdUtils.version(this.getClass()));
            System.out.println(IdUtils.version(LogUtils.class));
        }
    }

    public void testError() {
        Result error = new Result(0, 0 ,"");
    }

    public void testDateTime() {
        System.out.println(TimeUtils.start());
        System.out.println(TimeUtils.over());
        System.out.println(TimeUtils.start(new Date()));
        System.out.println(TimeUtils.over(new Date()));
    }

    public void testFileUtils () {
        FileUtils fileUtils = new FileUtils();
        System.out.println(fileUtils.getRootdir());
        fileUtils.mkdir("/test/123/");
        fileUtils.rmdirs("/");
    }

    public void testUuid () {
        System.out.println(UUID.randomUUID());
    }

    public static class TestJson {
        private String token;
        private long value;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
    public void testJson () {
        StdRet ret = JsonUtils.json("{\"token\":\"123456\",\"v\":10}", TestJson.class);
        if (ret.failure())
            logger.debug("解析失败");
    }

    public void testTime () {
        logger.debug(TimeUtils.min(new Date(1436371140000L)).toString());
        logger.debug(String.valueOf(TimeUtils.dayofweek(new Date(1436054400000L))));
    }

    public void testMap () {
        Map<String, Object> map = new HashMap<>();
        map.put(null, "hello");
        logger.debug((String) map.get(null));
    }

    public static class LogTest extends AbstractLogger {
        public LogTest() {
            logger.debug("123");
        }
    }
    public void testLog (Long v) {
        new LogTest();
        logger.debug("this 'is'.' a '.test".concat(v.toString()));
    }

    public void testInvoke () {
        BeanUtils.invoke(null, "this.hello.world", "test");
        logger.debug("test");
        BeanUtils.invoke(null, new AppTest("testLog"), "testLog", 12L);
        Map<String,Object> info = new HashMap<>(2);
        info.put("test", 0L);
        info.put("hello", "world");
        logger.debug(String.valueOf(info));
    }

    public void testBean() {
        logger.debug(JsonUtils.json(new ServiceList<List>(0L, new ArrayList(0))));
    }
}
