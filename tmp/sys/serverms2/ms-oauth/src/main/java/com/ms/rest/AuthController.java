package com.ms.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhuxiaomeng
 * @date 2019-03-23.
 * @email 154040976@qq.com
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
