package com.site.web.controller.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.site.auth.model.Permission;
import com.site.auth.service.AuthService;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "manager", method = RequestMethod.GET)
    public String manager(Model model) {
        List<Permission> pers = authService.getPermissions();
        model.addAttribute("pers", pers);
        return "auth/menu/menu_manager";
    }

}
