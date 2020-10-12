package com.yutong.smart.util.other;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import com.alibaba.fastjson.JSONArray;
import com.yutong.smart.util.ConnectionUtils;
import com.yutong.smart.util.DBUtils;


public class Test1 {

    public static void main(String[] args) {
        try {
            Connection conn = ConnectionUtils.getOracleConnection("127.0.0.1",
                    "1521", "orcl", "scott", "tiger");
            String sql = "SELECT * FROM TABLE1";
            List<Map<String, Object>> mapList =
                    DBUtils.queryMapList(conn, sql, true);

            System.out.println(JSONArray.toJSONString(mapList));
            FileUtils.writeLines(
                    new File("C:\\Users\\tech-winning\\Desktop\\测试\\aa.sql"),
                    "utf-8", mapList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
