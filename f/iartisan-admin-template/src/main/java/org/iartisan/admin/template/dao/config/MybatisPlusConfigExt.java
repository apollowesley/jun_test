package org.iartisan.admin.template.dao.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.iartisan.runtime.jdbc.config.MybatisPlusConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author King  2019-8-22
 */
//@EnableTransactionManagement
//@Configuration
public class MybatisPlusConfigExt extends MybatisPlusConfig {

    @Bean
    @Override
    public PaginationInterceptor initPaginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
