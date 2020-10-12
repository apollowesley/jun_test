package com.cnit1818.limit.ibatis;

import com.cnit1818.limit.common.SqlUtils;
import com.cnit1818.limit.dialect.Dialect;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class LimitSqlExecutor extends SqlExecutor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String pageRecordCountFlag = "__recordCount__";
    private boolean printSql;
    private Dialect dialect;

    public Dialect getDialect() {
        return this.dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public int executeUpdate(StatementScope statementScope, Connection conn, String sql, Object[] parameters)
            throws SQLException {
        if ((this.printSql) && (this.logger.isDebugEnabled())) {
            String sqlId = statementScope.getStatement().getId();
            this.logger.debug("sqlId: {}\r\n{}", new Object[]{sqlId, SqlUtils.getLogSql(sql, parameters)});
        }
        return super.executeUpdate(statementScope, conn, sql, parameters);
    }

    public void executeQuery(StatementScope statementScope, Connection conn, String sql, Object[] parameters,
                             int skipResults, int maxResults, RowHandlerCallback callback)
            throws SQLException {
        String sqlId = statementScope.getStatement().getId();

        if ((supportsLimit()) && ((skipResults != 0) || (maxResults != -999999))) {
            sql = StringUtils.trim(sql);
            if (this.dialect.supportsLimitOffset()) {
                sql = this.dialect.getLimitString(sql, skipResults, maxResults);
                skipResults = 0;
            } else {
                sql = this.dialect.getLimitString(sql, 0, maxResults);
            }
            maxResults = -999999;
        }
        if ((this.printSql) && (this.logger.isDebugEnabled())) {
            this.logger.debug("sqlId: {}\r\n{}", new Object[]{sqlId, SqlUtils.getLogSql(sql, parameters)});
        }
        super.executeQuery(statementScope, conn, sql, parameters, skipResults, maxResults, callback);
    }

    public boolean isPrintSql() {
        return this.printSql;
    }

    public void setPrintSql(boolean printSql) {
        this.printSql = printSql;
    }

    public String getPageRecordCountFlag() {
        return this.pageRecordCountFlag;
    }

    public void setPageRecordCountFlag(String pageRecordCountFlag) {
        this.pageRecordCountFlag = pageRecordCountFlag;
    }

    private boolean supportsLimit() {
        if (this.dialect != null) {
            return this.dialect.supportsLimit();
        }
        return false;
    }
}
