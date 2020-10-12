package com.ms.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:zxm
 * Date:2019/2/23
 */
@Controller
@RequestMapping("/index")
public class TestController {

    @GetMapping
    public String getTest() {
        return "index";
    }
}
