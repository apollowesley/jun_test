package com.jfast.common.pagination;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by guoyou on 2018/3/27.
 */
@Intercepts( {@Signature(type= StatementHandler.class,method="prepare",args={Connection.class}),
        @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = {Statement.class})
})
public class PagingInterceptorAdapter implements Interceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private AbstractPagingInterceptor delegate;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof ResultSetHandler) {
            ResultSetHandler resultSetHandler = (ResultSetHandler) target;
            return invocation.proceed();
        }
        else if (target instanceof RoutingStatementHandler) {
            return delegate.intercept(invocation);
        }
        else {
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        return delegate.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect", "SqlServer");
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class interceptorClass = contextClassLoader.loadClass("com.jfast.common.pagination." + dialect + "PagingInterceptor");
            delegate = (AbstractPagingInterceptor) interceptorClass.newInstance();
        } catch (ClassNotFoundException e) {
            LOGGER.error("mybatis intercept dialect:{} configured incorrect, please write \"MySql\", \"Oracle\", \"SqlServer\"", dialect, e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        LOGGER.info("mybatis intercept dialect:{}", dialect);
    }
}
