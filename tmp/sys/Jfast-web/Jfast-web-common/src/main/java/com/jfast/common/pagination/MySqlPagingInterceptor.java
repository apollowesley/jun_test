package com.jfast.common.pagination;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;

/**
 * Created by guoyou on 2018/3/27.
 */
@Intercepts(@Signature(type= StatementHandler.class,method="prepare",args={Connection.class}))
public class MySqlPagingInterceptor extends AbstractPagingInterceptor {

    @Override
    protected String getSelectTotalSql(String targetSql) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT COUNT(1) AS COUNT FROM( ")
                .append(targetSql).append(" ) X");

        return sb.toString();
    }

    @Override
    protected String getSelectPagingSql(String targetSql, PagingBounds bounds) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);

        sqlBuilder.append(" LIMIT ")
                .append(" " + bounds.getOffset())
                .append(" , ")
                .append(bounds.getSelectCount() + " ");
        return sqlBuilder.toString();
    }

}