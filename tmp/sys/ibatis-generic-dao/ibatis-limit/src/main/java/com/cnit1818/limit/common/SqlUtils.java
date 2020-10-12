package com.cnit1818.limit.common;


import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang.ArrayUtils;

import java.util.Map;


public class SqlUtils {
    public static String getLogSql(String sql, Object[] param) {
        return "sql: " + sql + "\r\nparam: " + ArrayUtils.toString(param);
    }


    public static String getLogSql(String sql, Map map) {
        return "sql: " + sql + "\r\nparam: " + ArrayUtils.toString(map.entrySet().toArray());
    }


    public static String getLogSql(String sql, Object param) {
        return "sql: " + sql + "\r\nparam: " + ArrayUtils.toString(new BeanMap(param).entrySet().toArray());
    }


    public static String getRecordCountSql(String sql) {
        return "select count(1) from (" + sql + ") temp";
    }
}
