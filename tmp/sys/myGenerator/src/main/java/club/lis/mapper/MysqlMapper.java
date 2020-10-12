package club.lis.mapper;

import java.util.HashMap;
import java.util.Map;

public class MysqlMapper {
    public static Map<String,String> MYSQL_MAP = new HashMap<>();

    static {
        MYSQL_MAP.put("BIGINT","Long");
        MYSQL_MAP.put("BIGINT UNSIGNED","Long");
        MYSQL_MAP.put("TINYINT","Integer");
        MYSQL_MAP.put("INT","Integer");
        MYSQL_MAP.put("INTEGER","Integer");
        MYSQL_MAP.put("VARCHAR","String");
        MYSQL_MAP.put("BIT","Integer");
        MYSQL_MAP.put("FLOAT","Float");
        MYSQL_MAP.put("DOUBLE","Double");
        MYSQL_MAP.put("DECIMAL","BigDecimal");
        MYSQL_MAP.put("NUMERIC","BigDecimal");
        MYSQL_MAP.put("CHAR","String");
        MYSQL_MAP.put("DATE","Date");
        MYSQL_MAP.put("TIME","Date");
        MYSQL_MAP.put("YEAR","Date");
        MYSQL_MAP.put("LONGVARCHAR","String");
        MYSQL_MAP.put("TIMESTAMP","Timestamp");
    }
}
