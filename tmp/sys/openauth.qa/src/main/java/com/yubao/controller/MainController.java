package com.yubao.controller;

import org.springframework.format.Printer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

/**
 * 主控制器
 * Created by Administrator on 2016-11-17.
 */
@Controller
@RequestMapping(value="/Main")
public class MainController {


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "hello";
    }


    @RequestMapping(value = "/getmodel", method = RequestMethod.GET)
    public ModelAndView GetWithModel() {
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("hello", "yubao");
        return modelAndView;
    }

    /**
     * 直接返回string
     * @param out 输出流
     */
    @ResponseBody
    @RequestMapping(value="/getstring", method = RequestMethod.GET)
    public void Get(PrintWriter out){
        out.write("ok");
    }
}