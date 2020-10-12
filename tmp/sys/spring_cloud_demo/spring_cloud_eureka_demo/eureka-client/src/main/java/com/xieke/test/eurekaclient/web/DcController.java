package com.xieke.test.eurekaclient.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/dc")
    public String dc() throws InterruptedException {

        //Thread.sleep(5000); //延迟5秒，为了触发服务降级逻辑（hystrix）

        String services = "Services: " + discoveryClient.getServices();
        System.err.println(services);
        return services;
    }

}