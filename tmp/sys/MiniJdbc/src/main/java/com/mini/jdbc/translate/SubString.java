package com.mini.jdbc.translate;

import com.mini.jdbc.dialect.Dialect;
import com.mini.jdbc.dialect.MysqlDialect;
import com.mini.jdbc.dialect.OracleDialect;
import com.mini.jdbc.dialect.SqlServerDialect;

/**
 * 字符串截取函数
 * 
 * @author komojoemary
 * @version [版本号, 2016-1-27]
 */
public class SubString implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof SqlServerDialect || dialect instanceof MysqlDialect) {
            if (sql.indexOf("substr(") != -1) {
                sql = sql.replaceAll("substr\\(", "substring\\(");
            }
        }
        else if (dialect instanceof OracleDialect) {
            if (sql.indexOf("substring(") != -1) {
                sql = sql.replaceAll("substring\\(", "substr\\(");
            }
        }
        return sql;
    }

}
