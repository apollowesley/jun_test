package com.cnit1818.limit.dialect;

import org.apache.commons.lang.StringUtils;


public class OracleDialect extends Dialect {
    public boolean supportsLimit() {
        return true;
    }


    public boolean supportsLimitOffset() {
        return true;
    }

    public String getLimitString(String sql, int skipResults, int maxResults) {
        boolean isForUpdate = false;
        if (StringUtils.endsWithIgnoreCase(sql, " for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuilder limitSql = new StringBuilder();
        if (skipResults > 0) {
            limitSql.append("select * from ( select row_.*, rownum rownum_ from ( ").append(sql).
                    append(" ) row_ ) where rownum_ <= ").append(skipResults + maxResults).
                    append(" and rownum_ > ").append(skipResults);
        } else {
            limitSql.append("select * from ( ").append(sql).append(" ) where rownum <= ").append(maxResults);
        }
        if (isForUpdate) {
            limitSql.append(" for update");
        }
        return limitSql.toString();
    }
}

