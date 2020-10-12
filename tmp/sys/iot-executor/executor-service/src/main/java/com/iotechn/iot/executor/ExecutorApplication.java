package com.iotechn.iot.executor;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.iotechn.iot.executor.entity.ExecutorRegisterDo;
import com.iotechn.iot.executor.mapper.ExecutorRegisterMapper;
import io.dubbo.springboot.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-05-05
 * Time: 上午8:21
 */

@SpringBootApplication(exclude = {DubboAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ImportResource("classpath:applicationContext-dubbo.xml")
//@EnableDiscoveryClient
public class ExecutorApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ExecutorApplication.class, args);
        ExecutorRegisterMapper bean = applicationContext.getBean(ExecutorRegisterMapper.class);
        DataSource bean1 = (DataSource) applicationContext.getBean("dynamicDataSource");
        System.out.println(bean);
    }
}
