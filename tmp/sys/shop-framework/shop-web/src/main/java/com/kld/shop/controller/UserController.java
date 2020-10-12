package com.kld.shop.controller;

import com.kld.sys.api.ISysUserService;
import com.kld.sys.dto.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by anpushang on 2016/3/15.
 */
@Controller
@RequestMapping(value="/sys")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap)throws Exception{

        System.out.println("ddfdfdfdfdfdffdf");
        SysUser sysUser = sysUserService.getSysUserById(1);
        modelMap.put("sysUser",sysUser);
        return "sys/index";
    }
}
