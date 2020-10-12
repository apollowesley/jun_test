package com.uei.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        model.put("name", "小荟");
        return "index.html";
    }

    @RequestMapping(value = "/hello")
    public String hello(ModelMap model) throws Exception {
        model.addAttribute("title", "Spring MVC And Freemarker");
        model.addAttribute("content", " Hello world ， test my first spring mvc ! ");
        return "hello.html";
    }

    @RequestMapping(value = "/test")
    public ModelAndView test(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView("test.html");
        mv.addObject("title", "Spring MVC And Freemarker");
        mv.addObject("content", " Hello world ， test my first spring mvc !");
        return mv;
    }
}
