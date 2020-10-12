package com.slavic.hors.admin.api.controller.portal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slavic.hors.admin.api.dto.portal.role.request.ListRequest;
import com.slavic.hors.admin.api.dto.portal.role.request.ReResourcesRequest;
import com.slavic.hors.admin.api.dto.portal.role.request.SaveOrUpdateRequest;
import com.slavic.hors.orm.entity.HorsPortalRole;
import com.slavic.hors.orm.entity.HorsPortalUser;
import com.slavic.hors.service.PortalRoleService;
import com.slavic.veles.base.response.ResponseEntity;
import com.slavic.veles.utils.AssertUtil;
import com.slavic.veles.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "portal/role",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
public class RoleController {

    private final PortalRoleService portalRoleService;

    public RoleController(PortalRoleService portalRoleService) {
        this.portalRoleService = portalRoleService;
    }

    @RequestMapping("list")
    public ResponseEntity<PageInfo<HorsPortalRole>> list(ListRequest request) {
        return ResponseEntity.SUCCESS(
                PageHelper
                        .startPage(request.getCurrentPage(), request.getPageSize())
                        .doSelectPageInfo(
                                () -> portalRoleService.list(BeanUtil.copyObject(request,new HorsPortalRole()))
                        )
        );
    }

    @RequestMapping("add")
    public ResponseEntity<Boolean> add(@Validated SaveOrUpdateRequest request) {
        return ResponseEntity.CONDITION(portalRoleService.add(BeanUtil.copyObject(request, new HorsPortalRole())),
                "添加角色失败");
    }

    @RequestMapping("update")
    public ResponseEntity<Boolean> update(@Validated SaveOrUpdateRequest request) {
        return ResponseEntity.CONDITION(null != portalRoleService.update(BeanUtil.copyObject(request,new HorsPortalRole())),
                "角色信息更新失败");
    }

    @RequestMapping("delete")
    public ResponseEntity<Boolean> delete(Long id) {
        AssertUtil.assertTrue(null != id, "删除角色ID不允许为空");
        AssertUtil.assertTrue(null != portalRoleService.findById(id), "角色已不存在,请刷新确认");
        return ResponseEntity.CONDITION(portalRoleService.delete(id),"删除用户失败");
    }

    @RequestMapping("reResources")
    public ResponseEntity<Boolean> reResources(@RequestBody @Validated ReResourcesRequest request) {
        AssertUtil.assertTrue(null != portalRoleService.findById(request.getId()), "角色已不存在,请刷新确认");
        return ResponseEntity.CONDITION(portalRoleService.reResources(request.getId(),request.getResourceIds()),
                "角色分配资源失败");
    }

    @RequestMapping("viewUsers")
    public ResponseEntity<List<HorsPortalUser>> viewUsers(Long roleId) {
        AssertUtil.assertTrue(null != portalRoleService.findById(roleId),
                "角色已不存在,请刷新确认");
        return ResponseEntity.SUCCESS(portalRoleService.roleUsers(roleId));
    }
}
