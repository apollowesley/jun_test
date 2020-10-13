package org.neuedu.crm.system.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/13-19:31
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "org.neuedu.crm.system.mapper")
public class MyBatisPlusConfiguration {

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
    
}
