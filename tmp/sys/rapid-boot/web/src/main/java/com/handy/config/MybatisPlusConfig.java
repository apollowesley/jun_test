package com.handy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/21 11:01
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }

    /**
     * SQL执行效率插件(开发环境使用)
     */
    /*@Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }*/
}