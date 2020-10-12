package com.mini.jdbc.translate;

import com.mini.jdbc.dialect.Dialect;
import com.mini.jdbc.dialect.MysqlDialect;
import com.mini.jdbc.dialect.OracleDialect;
import com.mini.jdbc.dialect.SqlServerDialect;

/**
 * 把多行转换成一合并列函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class ManyRowToOneColumnTranslate implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof SqlServerDialect || dialect instanceof MysqlDialect) {
            if (sql.indexOf("wm_concat(") != -1) {
                sql = sql.replaceAll("wm_concat\\(", "group_concat\\(");
            }
        }
        else if (dialect instanceof OracleDialect) {
            if (sql.indexOf("group_concat(") != -1) {
                sql = sql.replaceAll("group_concat\\(", "wm_concat\\(");
            }
        }
        return sql;
    }

}
