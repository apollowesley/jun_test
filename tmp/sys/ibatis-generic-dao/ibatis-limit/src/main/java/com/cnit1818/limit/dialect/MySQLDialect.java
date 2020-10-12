package com.cnit1818.limit.dialect;


public class MySQLDialect extends Dialect {
    public boolean supportsLimit() {
        return true;
    }


    public boolean supportsLimitOffset() {
        return true;
    }

    public String getLimitString(String sql, int skipResults, int maxResults) {
        StringBuilder limitSql = new StringBuilder();
        if (skipResults > 0) {
            limitSql.append(sql).append(" limit ").append(skipResults).append(",").append(maxResults);
        } else {
            limitSql.append(sql).append(" limit ").append(maxResults);
        }
        return limitSql.toString();
    }
}

