package com.jfast.web.api.admin.controller;

import com.jfast.web.api.admin.service.SystemAdminService;
import com.jfast.web.common.core.base.BaseController;
import com.jfast.web.common.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/17 19:33
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员管理接口")
public class AdminController extends BaseController {

    @Autowired
    private SystemAdminService systemAdminService;

    /**
     * 根据name 查找用户
     * @param userName
     * @return
     */
    @GetMapping("findByName")
    @ApiOperation("根据用户名获取用户信息")
    public Map findByName(@ApiParam(name = "userName", value = "用户名", required = true)
                          @RequestParam String userName) {
        return systemAdminService.findByName(userName);
    }

    /**
     * 管理员列表
     * @param params
     * @return
     */
    @PreAuthorize("@authorityService.hasAuthority('system:admin:list')")
    @GetMapping
    @ApiOperation("管理员列表")
    public Result<Map> list(@RequestParam Map params) {
        return systemAdminService.pagination(params);
    }

    @GetMapping("test")
    public Map test() {
        Map resultMap = new HashMap<>();
        resultMap.put("code", "success");
        return resultMap;
    }
}
