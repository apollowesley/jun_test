package org.neuedu.his.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

/**
 * @description
 * @author : CDHONG.IT
 * @date: 2019/12/13-19:31
 **/
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "org.neuedu.his.mapper")
public class MyBatisPlusConfig{

    /**
     * @apiNote : 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    /**
     * @apiNote :自动填充，插入或者更新是录入，需要在对应的字段上使用注解指定生成策略
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MetaObjectHandler() {
            //新增的自动填充
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "delMark", Integer.class, 0);
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
            }
            //更新的自动填充
            @Override
            public void updateFill(MetaObject metaObject) {}
        };
    }

}
