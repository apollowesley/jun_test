package com.mkfree.springAop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloWorldController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(String name) {
        return "hello world";
    }

    @PostMapping(value = "/hello_world")
    @AnalysisActuator
    public String helloWorld(@RequestBody User user) {


        log.warn("user.username : {}",user.getUsername());
        return "hello world";
    }
}