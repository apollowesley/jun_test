package com.iotechn.iot.device;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.iotechn.iot.device.api.DeviceOpenService;
import io.dubbo.springboot.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

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
public class DeviceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DeviceApplication.class, args);
        DeviceOpenService bean = run.getBean(DeviceOpenService.class);
        System.out.println(bean);
    }
}
