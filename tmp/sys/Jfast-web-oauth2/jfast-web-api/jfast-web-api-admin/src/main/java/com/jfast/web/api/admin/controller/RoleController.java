package com.jfast.web.api.admin.controller;

import com.jfast.web.api.admin.service.SystemRoleService;
import com.jfast.web.common.core.base.BaseController;
import com.jfast.web.common.core.model.Result;
import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/28 11:57
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 角色列表
     * @param params
     * @return
     */
    @GetMapping
    public Result<Map> list(Map params) {
        return systemRoleService.pagination(params);
    }

    /**
     * 获取角色tree菜单
     * @return
     */
    @GetMapping("getMenuByUserRole")
    @ApiOperation("获取用户tree菜单")
    public Result<List<Map>> getMenuByUserRole() {
        SystemSecurityUser systemSecurityUser = UserUtils.getSystemSecurityUser();
        if (ObjectUtils.isEmpty(systemSecurityUser))
            return null;
        return systemRoleService.getMenuByUserRole(systemSecurityUser);
    }


}
