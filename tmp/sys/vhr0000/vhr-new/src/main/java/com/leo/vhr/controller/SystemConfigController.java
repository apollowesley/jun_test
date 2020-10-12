package com.leo.vhr.controller;

import com.leo.vhr.model.Menu;
import com.leo.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/14
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController
{
    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getAllMenusById(){
        //这里的传入的ID不要使用前端传来的id，不安全
        //用户登录成功之后，id是保存在了内存里的
        return menuService.getAllMenusById();
    }
}
