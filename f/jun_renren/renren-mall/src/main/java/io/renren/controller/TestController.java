package io.renren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-05 12:58
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
