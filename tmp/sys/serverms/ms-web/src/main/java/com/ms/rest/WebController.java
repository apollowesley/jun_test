package com.ms.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

/**
 * Author:zxm
 * Date:2019/3/15
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping()
    public String re(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "客户端返回数据";
    }
    @PostMapping
    public String rep(){
        return "bbb";
    }
}
