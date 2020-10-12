package com.luoqy.speedy.core.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/common/plugin")
public class PluginController {
    private String PREFIX = "WEB-INF/plugin";
    @RequestMapping("/menu")
    public String menu(){
        return PREFIX+"/menu";
    }
}
