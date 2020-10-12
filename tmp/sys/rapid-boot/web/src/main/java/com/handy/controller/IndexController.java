package com.handy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author handy
 * @Description: {临时跳转路径}
 * @date 2019/8/21 14:49
 */
@Controller
public class IndexController {

    @GetMapping("/page/welcome-1")
    public String welcome1() {
        return "page/welcome-1";
    }

    @GetMapping("/page/welcome-2")
    public String welcome2() {
        return "page/welcome-2";
    }

    @GetMapping("/page/menu")
    public String menu() {
        return "page/menu";
    }

    @GetMapping("/page/setting")
    public String setting() {
        return "page/setting";
    }

    @GetMapping("/page/table")
    public String table() {
        return "page/table";
    }

    @GetMapping("/page/form")
    public String form() {
        return "page/form";
    }

    @GetMapping("/page/form-step")
    public String formStep() {
        return "page/form-step";
    }

    @GetMapping("/page/login-1")
    public String login1() {
        return "member/login";
    }

    @GetMapping("/page/login-2")
    public String login2() {
        return "page/login-2";
    }

    @GetMapping("/page/404")
    public String a404() {
        return "page/404";
    }

    @GetMapping("/page/button")
    public String button() {
        return "page/button";
    }

    @GetMapping("/page/layer")
    public String layer() {
        return "page/layer";
    }

    @GetMapping("/page/icon-picker")
    public String iconPicker() {
        return "page/icon-picker";
    }

    @GetMapping("/page/color-select")
    public String colorSelect() {
        return "page/color-select";
    }

    @GetMapping("/page/table-select")
    public String tableSelect() {
        return "page/table-select";
    }

    @GetMapping("/page/upload")
    public String upload() {
        return "page/upload";
    }

    @GetMapping("/page/editor")
    public String editor() {
        return "page/editor";
    }

    @GetMapping("/page/error")
    public String error() {
        return "page/error";
    }

    @GetMapping("/page/icon")
    public String icon() {
        return "page/icon";
    }
}
