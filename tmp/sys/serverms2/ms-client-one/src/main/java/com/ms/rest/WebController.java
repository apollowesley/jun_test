package com.ms.rest;

import com.ms.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:zxm
 * Date:2019/3/15
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @Autowired
    WebClient webClient;

    @GetMapping()
    public String re() {
        return webClient.re();
    }

    @PostMapping
    public String rep() {
        return "bbb";
    }
}
