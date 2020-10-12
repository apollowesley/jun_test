package com.site.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
    public String error(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "msg", required = false) String message) {
        log.error("## 系统错误：status={} , errorMessage={}", status, message);
        return "common/error";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error403() {
        return "common/403";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "common/404";
    }

}
