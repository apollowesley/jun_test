package com.leo.vhr.controller;

import com.leo.vhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/13
 * @version: 1.0
 */
@RestController
public class LoginController
{
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录，请登录！");
    }
}
