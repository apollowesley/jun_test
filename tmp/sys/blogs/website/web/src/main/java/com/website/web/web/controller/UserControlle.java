package com.website.web.web.controller;

import com.website.common.utils.Result;
import com.website.model.user.User;
import com.website.service.user.UserService;
import com.website.service.user.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author: xiaokai
 * @Description: web用户控制台页面
 * @Date: 2019/5/18
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserControlle {

    @Autowired
    private UserService userService;


    @RequestMapping("regist")
    public Result register(@RequestBody User user){
        return userService.userRegister(user);
    }

    @RequestMapping("login")
    public Result login(@RequestBody User user){
        return  userService.userLogin(user);
    }

}
