package com.cjlgb.design.developer.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjlgb.design.common.core.util.WebUtils;
import com.cjlgb.design.common.mybatis.enums.AuditFlag;
import com.cjlgb.design.common.oauth.entity.Developer;
import com.cjlgb.design.common.oauth.entity.OauthUser;
import com.cjlgb.design.common.oauth.feign.OauthGrantFeign;
import com.cjlgb.design.common.oauth.feign.OauthUserFeign;
import com.cjlgb.design.common.security.annotation.PreAuthorize;
import com.cjlgb.design.common.security.bean.AccessToken;
import com.cjlgb.design.common.security.bean.Authentication;
import com.cjlgb.design.common.security.context.SecurityContextHolder;
import com.cjlgb.design.common.security.service.SecurityService;
import com.cjlgb.design.developer.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author WFT
 * @date 2020/6/27
 * description:开发者账号控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/developer")
public class DeveloperController {

    private final DeveloperService developerService;
    private final OauthGrantFeign oauthGrantFeign;
    private final OauthUserFeign oauthUserFeign;

    @Resource
    @Qualifier(value = "securityServiceByRedisImpl")
    private SecurityService securityService;

    /**
     * 申请开发者账号
     * @param parameter com.cjlgb.design.common.oauth.entity.Developer
     */
    @PreAuthorize
    @PostMapping
    public Developer apply(@RequestBody @Validated Developer parameter,HttpServletRequest request){
        //  参数校验
        switch (parameter.getIdentityFlag()){
            case personal:
                Assert.hasText(parameter.getIdentity(),"身份证不能为空");
                break;
            case company:
                Assert.hasText(parameter.getCompanyName(),"企业名称不能为空");
                Assert.hasText(parameter.getCompanyCode(),"社会信用代码不能为空");
                break;
            default:
                throw new IllegalArgumentException("身份类型参数异常");
        }
        //  账号Id
        parameter.setId(SecurityContextHolder.getAuthentication().getId());
        //  申请时间
        parameter.setApplyTime(LocalDateTime.now());
        //  待审核状态
        parameter.setAuditFlag(AuditFlag.wait);
        //  获取申请人用户Ip
        parameter.setApplyIp(WebUtils.getInstance().getRemoteAddr(request));
        this.developerService.updateById(parameter);
        return parameter;
    }

    /**
     * 认证接口
     * @param code 授权码
     * @return com.cjlgb.design.common.security.bean.AccessToken
     */
    @GetMapping(value = "/grant/{code}/{flag}")
    public AccessToken grant(@PathVariable(value = "code") String code,@PathVariable(value = "flag") String flag){
        //  通过授权码向oauth服务器,换取AccessToken
        AccessToken accessToken = this.oauthGrantFeign.getToken(code,flag,"10002", "c1940b54-288d-11ea-b6bb-0242ac120004");
        //  通过AccessToken,获取用户信息
        OauthUser user = this.oauthUserFeign.getMyInfo(accessToken);
        //  获取开发者信息
        Developer developer = this.developerService.selectByApplyId(user.getId());
        if (null == developer){
            //  初始化开发者信息
            developer = new Developer();
            developer.setApplyId(user.getId());
            developer.setAuditFlag(AuditFlag.sleep);
            this.developerService.save(developer);
        }
        //  返回AccessToken
        return this.securityService.generateToken(new Authentication(developer.getId(),new ArrayList<>(0)));
    }

    /**
     * 分页查询开发者账号列表
     * @param page 查询条件
     * @param parameter 查询条件
     * @return 分页结果
     */
    @PreAuthorize
    @GetMapping(value = "/page")
    public IPage<Developer> pagingQuery(Page<Developer> page,Developer parameter){
        parameter = null == parameter ? new Developer() : parameter;
        return this.developerService.pagingQuery(page,parameter);
    }

    /**
     * 审核接口
     * @param parameter com.cjlgb.design.common.oauth.entity.Developer
     */
    @PreAuthorize
    @PutMapping
    public void audit(@RequestBody @Validated Developer parameter, HttpServletRequest request){
        Assert.notNull(parameter.getAuditFlag(),"审核标记不能为空");
        //  填充字段
        parameter.setOperatingIp(WebUtils.getInstance().getRemoteAddr(request));
        parameter.setOperatingId(SecurityContextHolder.getAuthentication().getId());
        parameter.setOperatingTime(LocalDateTime.now());
        //  保存
        this.developerService.updateById(parameter);
    }

    /**
     * 获取当前开发者信息
     * @return com.cjlgb.design.common.oauth.entity.Developer
     */
    @PreAuthorize
    @GetMapping(value = "/getMyInfo")
    public Developer getMyInfo(){
        Long userId = SecurityContextHolder.getAuthentication().getId();
        return this.developerService.getById(userId);
    }

}
