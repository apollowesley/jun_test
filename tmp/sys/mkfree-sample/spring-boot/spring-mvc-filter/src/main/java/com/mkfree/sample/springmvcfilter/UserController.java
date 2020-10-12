package com.mkfree.sample.springmvcfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @GetMapping(value = "/testFilter")
    public String testFilter(){
        log.info("ok");
        return "ok";
    }
}
