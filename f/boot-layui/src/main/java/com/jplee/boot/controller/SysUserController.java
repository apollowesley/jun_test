package com.jplee.boot.controller;

import com.jplee.boot.model.SysUser;
import com.jplee.boot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author shkstart
 * @create 2019-01-27 0:02
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    //status:true/false
    //data:object/string
    //errmsg:返回的错误信息

    public HashMap<String, Object> find(Long id) {

        HashMap<String, Object> map = new HashMap<>();
        SysUser sysUser = sysUserService.findbyUserid(id);

        map.put("username", sysUser.getUsername());
        return map;
    }

    /*@GetMapping("/add")
    public String toRegist(Model model) {
        model.addAttribute("hello", "你好");
        return "system/sys_user/add";
    }*/

    @GetMapping("/add")
    public ModelAndView toRegist(ModelAndView model) {
        model.setViewName("system/sys_user/add");
        return model;
    }

    @PostMapping("/regist")
    public HashMap<String, Object> regist(SysUser sysUser) {

        HashMap<String, Object> map = new HashMap<>();
        boolean result = sysUserService.regist(sysUser);

        if (result) {
            map.put("status", true);
        } else {
            map.put("status", false);
            map.put("errmsg", "注册失败");
        }
        return map;
    }
}
