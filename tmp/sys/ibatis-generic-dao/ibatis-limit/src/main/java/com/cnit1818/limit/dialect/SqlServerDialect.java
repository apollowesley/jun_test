package com.cnit1818.limit.dialect;


public class SqlServerDialect extends Dialect {
    public boolean supportsLimit() {
        return true;
    }


    public boolean supportsLimitOffset() {
        return false;
    }


    public String getLimitString(String sql, int skipResults, int maxResults) {
        if (skipResults > 0) {
            throw new UnsupportedOperationException("sqlserver has no offset");
        }
        return new StringBuffer(sql.length() + 8).append(sql)
                .insert(getAfterSelectInsertPoint(sql), " top " + maxResults).toString();
    }


    static int getAfterSelectInsertPoint(String sql) {
        int selectIndex = sql.toLowerCase().indexOf("select");
        int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
        return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }
}

