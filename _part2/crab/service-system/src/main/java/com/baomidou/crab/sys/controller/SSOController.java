package com.baomidou.crab.sys.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.common.annotation.LogTrack;
import com.baomidou.crab.sys.dto.LoginDTO;
import com.baomidou.crab.sys.entity.User;
import com.baomidou.crab.sys.service.IUserService;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 单点登录
 * </p>
 *
 * @author jobob
 * @since 2018-10-12
 */
@Api(tags = {"SSO "})
@RestController
@RequestMapping("/sso")
public class SSOController extends ApiController {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Autowired
    private IUserService userService;


    /**
     * 用户登录
     */
    //@LogTrack("登录")
    @Login(action = Action.Skip)
    @PostMapping("/login")
    public R<User> login(LoginDTO dto) {
        Assert.fail(!"102400".equals(request.getParameter("signal")), "暗号不正确！");
        return success(userService.loginByDto(request, response, dto));
    }


    /**
     * 用户退出
     */
    @LogTrack("退出")
    @Login(action = Action.Skip)
    @GetMapping("/logout")
    public void logout() {
        try {
            SSOHelper.logout(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
