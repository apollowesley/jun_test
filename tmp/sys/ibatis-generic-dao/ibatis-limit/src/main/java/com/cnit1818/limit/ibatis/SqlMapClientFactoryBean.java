package com.cnit1818.limit.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SqlMapClientFactoryBean
        extends org.springframework.orm.ibatis.SqlMapClientFactoryBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SqlExecutor sqlExecutor;

    public SqlExecutor getSqlExecutor() {
        return this.sqlExecutor;
    }

    public void setSqlExecutor(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public void afterPropertiesSet()
            throws Exception {
        super.afterPropertiesSet();
        SqlMapClient c = getObject();
        if ((this.sqlExecutor != null) && ((c instanceof SqlMapClientImpl))) {
            SqlMapClientImpl client = (SqlMapClientImpl) c;
            SqlMapExecutorDelegate delegate = client.getDelegate();
            try {
                Class clazz = delegate.getClass();
                Field field = clazz.getDeclaredField("sqlExecutor");
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                field.set(delegate, this.sqlExecutor);
            } catch (Exception e) {
                this.logger.error("Cannot set limit SqlMapClient.sqlExecutor = "
                        + this.sqlExecutor.getClass().getName() + " cause:" + e.getMessage());
            }
        }
    }
}


