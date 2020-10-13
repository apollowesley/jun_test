package com.cjlgb.design.upms.controller;

import com.cjlgb.design.common.core.constant.HttpStatus;
import com.cjlgb.design.common.core.exception.BizException;
import com.cjlgb.design.common.core.util.StrUtils;
import com.cjlgb.design.common.mybatis.enums.LockFlag;
import com.cjlgb.design.common.oauth.dto.GrantParameter;
import com.cjlgb.design.common.oauth.entity.OauthUser;
import com.cjlgb.design.common.oauth.feign.OauthGrantFeign;
import com.cjlgb.design.common.oauth.feign.OauthUserFeign;
import com.cjlgb.design.common.security.bean.AccessToken;
import com.cjlgb.design.common.security.bean.Authentication;
import com.cjlgb.design.common.security.service.SecurityService;
import com.cjlgb.design.common.upms.entity.Employee;
import com.cjlgb.design.upms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author WFT
 * @date 2020/7/19
 * description:认证控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/grant")
public class GrantController {

    private final OauthGrantFeign oauthGrantFeign;
    private final OauthUserFeign oauthUserFeign;
    private final EmployeeService employeeService;

    @Resource
    @Qualifier(value = "securityServiceByRedisImpl")
    private SecurityService securityService;

    /**
     * 授权码认证接口
     * @param code 授权码
     * @param flag 标记
     * @return com.cjlgb.design.common.security.bean.AccessToken
     */
    @GetMapping(value = "/{code}/{flag}")
    public AccessToken grant(@PathVariable(value = "code") String code, @PathVariable(value = "flag") String flag){
        //  通过授权码向oauth服务器,换取AccessToken
        AccessToken accessToken = this.oauthGrantFeign.getToken(code,flag,"10003", "c1940b54-288d-11ea-b6bb-0242ac120004");
        //  通过AccessToken,获取用户信息
        OauthUser user = this.oauthUserFeign.getMyInfo(accessToken);
        //  获取员工信息
        Employee employee = this.employeeService.selectByUserId(user.getId());
        if (null == employee){
            throw new BizException(HttpStatus.USE_PROXY,"请前往绑定员工信息",user);
        }
        return this.generateToken(employee);
    }

    private AccessToken generateToken(Employee employee){
        Assert.isTrue(LockFlag.enable.equals(employee.getLockFlag()),"账号被锁定,请与管理员联系");
        //  获取权限列表
        Collection<String> authorities = this.employeeService.selectAuthorities(employee.getId());
        //  生成访问令牌
        return this.securityService.generateToken(new Authentication(employee.getId(),authorities));
    }

    /**
     * 绑定用户Id
     * @param parameter com.cjlgb.design.common.oauth.dto.GrantParameter
     * @return com.cjlgb.design.common.security.bean.AccessToken
     */
    @PutMapping
    @Transactional(rollbackFor = Throwable.class)
    public AccessToken binding(@RequestBody GrantParameter parameter){
        //  参数校验
        Assert.hasText(parameter.getUsername(),"手机号码不能为空");
        Assert.hasText(parameter.getPassword(),"密码不能为空");
        Assert.hasText(parameter.getSalt(),"随机盐不能为空");
        Assert.notNull(parameter.getOpenId(),"openId不能为空");
        //  获取员工信息
        Employee employee = this.employeeService.selectByUserMobile(parameter.getUsername());
        Assert.notNull(employee,"用户名不存在");
        Assert.isNull(employee.getUserId(),"此员工账号已绑定其他用户了");
        //  密码校验
        String cipherText = StrUtils.getInstance().md5(employee.getPassword(),parameter.getSalt());
        Assert.isTrue(cipherText.equals(parameter.getPassword()),"用户名或密码错误");
        //  绑定关系
        employee.setUserId(parameter.getOpenId());
        this.employeeService.updateById(employee);
        //  生成访问令牌
        return this.generateToken(employee);
    }

}
