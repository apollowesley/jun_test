package com.baomidou.crab.sys.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.crab.core.web.BaseController;
import com.baomidou.crab.sys.dto.RoleResourceDTO;
import com.baomidou.crab.sys.entity.Role;
import com.baomidou.crab.sys.service.IRoleResourceService;
import com.baomidou.crab.sys.service.IRoleService;
import com.baomidou.crab.sys.service.IUserRoleService;
import com.baomidou.crab.sys.vo.ResourceZTreeVO;
import com.baomidou.crab.sys.vo.UserRoleSelectedVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import io.swagger.annotations.Api;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2018-09-15
 */
@Api(tags = {"角色"})
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController<IRoleService, Role> {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleResourceService roleResourceService;


    @GetMapping("/page")
    public R<IPage<Role>> page(Role role) {
        return success(baseService.page(getPage(), role));
    }


    @GetMapping("/list")
    public R<List<Role>> list() {
        return success(baseService.listAll());
    }


    @GetMapping("/list_{userId}")
    public R<List<UserRoleSelectedVO>> listSelected(@PathVariable("userId") Long userId) {
        return success(userRoleService.listSelectedVO(userId));
    }


    @GetMapping("/ztree_{id}")
    public R<List<ResourceZTreeVO>> listZTreeVO(@PathVariable("id") Long id) {
        return success(roleResourceService.listZTreeVO(id));
    }


    @PostMapping("/ztree")
    public R<Boolean> ztree(@RequestBody RoleResourceDTO dto) {
        return success(roleResourceService.saveByDto(dto));
    }


    @PutMapping("/status_{id}")
    public R<Boolean> status(@PathVariable("id") Long id,
                             @RequestParam Integer status) {
        return success(baseService.updateStatus(id, status));
    }
}
