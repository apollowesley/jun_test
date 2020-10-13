package com.zb.mapper;

import java.io.IOException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.NestedIOException;

public class BeanFactory extends SqlSessionFactoryBean {

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        try {
            return super.buildSqlSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NestedIOException("wwwwwwwwwwwwwwwwwwwwwwwwwwww", e.getCause());
        } finally {
            ErrorContext.instance().reset();
        }

    }
}
