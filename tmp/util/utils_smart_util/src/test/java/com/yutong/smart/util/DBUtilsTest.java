
package com.yutong.smart.util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import com.yutong.smart.domain.Teacher;


public class DBUtilsTest {

    private static Connection conn = null;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        conn = ConnectionUtils.getOracleConnection("127.0.0.1", "1521", "orcl",
                "scott", "tiger");
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }


    @Test
    public void testQueryMap() {
        Map<String, Object> map =
                DBUtils.queryMap(conn, "SELECT * FROM T_TEACHER", false);
        System.out.println(JSONObject.toJSONString(map));
    }


    @Test
    public void testQueryMapList() {
        List<Map<String, Object>> mapList =
                DBUtils.queryMapList(conn, "SELECT * FROM T_TEACHER", false);
        System.out.println(JSONObject.toJSONString(mapList));
    }


    @Test
    public void testQueryCount() {
        long count = DBUtils.queryCount(conn, "SELECT COUNT(0) FROM T_TEACHER",
                false);
        System.out.println(count);
    }


    @Test
    public void testQueryT() {
       Teacher teacher = new DBUtils<Teacher>().queryT(conn, "SELECT * FROM T_TEACHER", Teacher.class, false);
       System.out.println(JSONObject.toJSONString(teacher));
       System.out.println(JSONObject.toJSONStringWithDateFormat(teacher, "yyyy-MM-dd"));
    }


    @Test
    public void testQueryTList() {
        List<Teacher> teacherList = new DBUtils<Teacher>().queryTList(conn, "SELECT * FROM T_TEACHER", Teacher.class, false);
        System.out.println(JSONObject.toJSONString(teacherList));
        System.out.println(JSONObject.toJSONStringWithDateFormat(teacherList, "yyyy-MM-dd"));
    }

}
