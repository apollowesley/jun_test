package com.xieke.test.controller;

import com.xieke.test.entity.User;
import com.xieke.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/show")
    @ResponseBody
    public String show(@RequestParam(value = "name")String name){
        User user = userService.findUserByName(name);
        if(null != user)
            return user.getId()+"/"+user.getName()+"/"+user.getPassword();
        else
            return "null";
    }

    @RequestMapping(value = "/show2")
    @ResponseBody
    public String show2(@RequestParam(value = "name")String name, @RequestParam(value = "password")String password){
        User user = userService.findUserByNameAndPassword(name, password);
        if(null != user)
            return user.getId()+"/"+user.getName()+"/"+user.getPassword();
        else
            return "null";
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(){
        userService.saveUser();
        User user = userService.findUserByName("a123456789");
        if(null != user)
            return user.getId()+"/"+user.getName()+"/"+user.getPassword();
        else
            return "null";
    }

}