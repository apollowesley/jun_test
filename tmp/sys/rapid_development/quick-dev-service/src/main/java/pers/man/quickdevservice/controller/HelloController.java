package pers.man.quickdevservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.man.quickdevcommon.annotation.Log;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 13:25
 * @project quick-dev
 */
@Controller
public class HelloController {
    @Log
    @RequestMapping("/hello")
    public void test() {
        System.out.println("AOP Test");
    }
}
