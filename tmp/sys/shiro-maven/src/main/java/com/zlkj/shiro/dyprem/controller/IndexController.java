package com.zlkj.shiro.dyprem.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlkj.shiro.dyprem.bind.CurrentUser;
import com.zlkj.shiro.dyprem.entity.Resource;
import com.zlkj.shiro.dyprem.entity.User;
import com.zlkj.shiro.dyprem.service.ResourceService;
import com.zlkj.shiro.dyprem.service.UserService;
@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
