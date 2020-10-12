package com.site.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

/**
 * 基类Controller
 * 
 * 
 */
public class BaseController {

    /**
     * 重定向到错误提示页面
     * 
     * @param model
     * @param errorMessage
     * @return
     */
    public String forwordErrorPage(Model model, String errorMessage) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("status", "0");
        result.put("msg", errorMessage);
        model.addAttribute("result", result);
        return "pages/error";
    }

}
