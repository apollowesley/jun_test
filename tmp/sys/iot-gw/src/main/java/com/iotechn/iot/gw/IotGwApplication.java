package com.iotechn.iot.gw;

import com.iotechn.iot.gw.controller.ApiController;
import io.dubbo.springboot.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = DubboAutoConfiguration.class)
@ImportResource("classpath:applicationContext-dubbo.xml")
@RestController
@RequestMapping("/")
public class IotGwApplication {

    @RequestMapping("abc")
    @ResponseBody
    public String test() {
        return "abc";
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(IotGwApplication.class, args);
    }
}
