package com.baomidou.crab.sys.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.dto.UserDTO;
import com.baomidou.crab.sys.entity.User;
import com.baomidou.crab.sys.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-09-16
 */
@Api(tags = {"用户"})
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserService, User> {


    @GetMapping("/page")
    public R<IPage<User>> page(User user) {
        return success(baseService.page(getPage(), user));
    }


    @PostMapping("/dto")
    public R<Boolean> save(UserDTO dto) {
        return success(baseService.saveDto(dto));
    }


    @PutMapping("/status_{id}")
    public R<Boolean> status(@PathVariable("id") Long id,
                             @RequestParam Integer status) {
        Assert.fail(0L == id, "不允许修改系统管理员状态！");
        return success(baseService.updateStatus(id, status));
    }


    @PostMapping("/unlock")
    public R<Boolean> unlock(@RequestParam String password) {
        return baseService.unlock(getLoginUserId(), password)
                ? success(true) : R.failed("解锁失败");
    }


    @PutMapping("/reset_password/{id}")
    public R<Boolean> resetPassword(@PathVariable("id") Long id) {
        Assert.fail(0L == id && getLoginUserId() != 0L, "无权重置系统管理员登录密码！");
        return success(baseService.resetPassword(id));
    }


    @Override
    @DeleteMapping("/{id}")
    public R<Boolean> remove(@PathVariable("id") Long id) {
        Assert.fail(0L == id, "不允许删除系统管理员！");
        return super.remove(id);
    }
}
