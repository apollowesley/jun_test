package com.jfast.common.pagination;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guoyou on 2018/3/26.
 */
public abstract class AbstractPagingInterceptor implements Interceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");

    private static final String FIELD_DELEGATE = "delegate";

    private static final String FIELD_ROWBOUNDS = "rowBounds";

    private static final String FIELD_MAPPEDSTATEMENT = "mappedStatement";

    private static final String FIELD_SQL = "sql";

    private static final String BLANK = " ";

    public static final String SELECT = "select";

    public static final String FROM = "from";

    public static final String ORDER_BY = "order by";

    public static final String UNION = "union";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /**
        Connection connection = (Connection) invocation.getArgs()[0];
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();

        StatementHandler handler = (StatementHandler) readField(statementHandler, FIELD_DELEGATE);
        PagingBounds pagingBounds = (PagingBounds) readField(handler, FIELD_ROWBOUNDS);
        MappedStatement mappedStatement = (MappedStatement) readField(handler, FIELD_MAPPEDSTATEMENT);
        BoundSql boundSql = handler.getBoundSql();

        //replace all blank
        String targetSql = replaceSqlBlank(boundSql.getSql());
        if (pagingBounds.getSortColumns() != null && pagingBounds.getSortColumns() != "") {
            targetSql = targetSql + " " + "order by " + pagingBounds.getSortColumns();
        }
        //paging
        getTotalAndSetInPagingBounds(targetSql, boundSql, pagingBounds, mappedStatement, connection);

        String pagingSql = getSelectPagingSql(targetSql, pagingBounds);
        writeDeclaredField(boundSql, FIELD_SQL, pagingSql);

        //ensure set to default
        pagingBounds.setMeToDefault();
         **/
        return invocation.proceed();
    }

    private void getTotalAndSetInPagingBounds(String targetSql, BoundSql boundSql, PagingBounds bounds,
                                              MappedStatement mappedStatement, Connection connection) throws SQLException {
        String totalSql = getSelectTotalSql(targetSql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(), totalSql, parameterMappings, parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, totalBoundSql);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(totalSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                int totalRecord = rs.getInt(1);
                bounds.setTotal(totalRecord);
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
        }
    }

    protected abstract String getSelectTotalSql(String targetSql);

    protected abstract String getSelectPagingSql(String targetSql, PagingBounds pagingBounds);

    private String replaceSqlBlank(String originalSql) {
        Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
        return matcher.replaceAll(BLANK);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler) {
            try {
                Field delegate = getField(RoutingStatementHandler.class, FIELD_DELEGATE);
                delegate.setAccessible(true);
                StatementHandler handler = (StatementHandler) delegate.get(target);
                RowBounds rowBounds = (RowBounds) readField(handler, FIELD_ROWBOUNDS);
                if (rowBounds != RowBounds.DEFAULT && rowBounds instanceof PagingBounds) {
                    return Plugin.wrap(target, this);
                }
            } catch (Exception e) {
                // ignore
            }
        }
        else if (target instanceof DefaultResultSetHandler) {
            try {
                RowBounds rowBounds = (RowBounds) readField(target, FIELD_ROWBOUNDS);
                if (rowBounds != RowBounds.DEFAULT && rowBounds instanceof PagingBounds) {

                    Executor executor = (Executor) readField(target, "executor");
                    MappedStatement mappedStatement = (MappedStatement) readField(target, FIELD_MAPPEDSTATEMENT);

                    ParameterHandler parameterHandler = (ParameterHandler) readField(target, "parameterHandler");
                    ResultHandler resultHandler = (ResultHandler) readField(target, "resultHandler");
                    BoundSql boundSql = (BoundSql) readField(target, "boundSql");

                    PagingResultSetHandler pagingResultSetHandler = new PagingResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
                    return pagingResultSetHandler;
                }
            } catch (IllegalAccessException e) {
                // ignore
            }
        }
        return target;
    }

    private void writeDeclaredField(Object target, String fieldName, Object value)
            throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
        }
        field.set(target, value);
    }

    private Object readField(Object target, String fieldName)
            throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field.get(target);
    }

    private static Field getField(final Class<?> cls, String fieldName) {
        for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
            try {
                Field field = acls.getDeclaredField(fieldName);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                    return field;
                }
            } catch (NoSuchFieldException ex) {
                // ignore
            }
        }
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect");
        LOGGER.info("mybatis intercept dialect:{}", dialect);
    }
}