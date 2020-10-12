package com.xieke.test.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${info.from}")
    private String from;

    @RequestMapping(value = "/test")
    public List<String> test(){
        List<String> list = new ArrayList<>();
        list.add(driverClassName);
        list.add(url);
        list.add(username);
        list.add(password);
        list.add(from);
        return list;
    }

}
