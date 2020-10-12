package com.jfast.web.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfast.web.auth.fegin.SystemAdminFeign;
import com.jfast.web.common.core.base.BaseController;
import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.core.utils.ResultCode;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 用户 Controller
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/16 22:00
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户 Controller")
public class UserController extends BaseController {

    @Autowired
    private SystemAdminFeign systemAdminFeign;
    @Autowired
    private TokenStore tokenStore;

    /**
     * 获取用户信息
     * @param principal
     * @return
     */
    @GetMapping
    @ApiOperation("获取用户信息")
    public Principal getUserInfo(@ApiParam(hidden = true) Principal principal) {
        return principal;
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("getSystemSecurityUser")
    @ApiOperation("获取用户信息")
    public SystemSecurityUser getSystemSecurityUser() {
        return UserUtils.getSystemSecurityUser();
    }

    /**
     * 退出
     * @return
     */
    @DeleteMapping("/logout")
    @ApiOperation("退出")
    public ResultCode removeToken(@ApiParam(hidden = true) HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.replace(OAuth2AccessToken.BEARER_TYPE + " ", "");
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (ObjectUtils.isNotEmpty(accessToken)) {
            tokenStore.removeAccessToken(accessToken);
            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
            tokenStore.removeRefreshToken(refreshToken);
        }
        return new ResultCode(ResultCode.SUCCESS, "退出成功");
    }
}
