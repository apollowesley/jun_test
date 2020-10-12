package com.cdh.springboot.controller;

import com.cdh.springboot.common.ResponseCode;
import com.cdh.springboot.entity.User;
import com.cdh.springboot.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Controller
@Slf4j
public class TestController {

    @GetMapping("/http")
    public @ResponseBody String http(User user){
        return "http";
    }

    @RequestMapping("/hello")
    public @ResponseBody String hello(){
        log.info("Hello");
        return "hello";
    }

    @RequestMapping("/sysException")
    public void sysException(){
        System.out.println(1/0);
    }

    @RequestMapping("/exception")
    public void exception(){
        throw new PermissionException(ResponseCode.USER_DISABLED);
    }

}
