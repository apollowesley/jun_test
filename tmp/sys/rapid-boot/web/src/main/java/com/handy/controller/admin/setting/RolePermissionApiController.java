package com.handy.controller.admin.setting;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.handy.common.vo.PageVO;
import com.handy.common.vo.ResultVO;
import com.handy.controller.BaseController;
import com.handy.param.permission.RolePermissionParam;
import com.handy.service.entity.sys.SysPermission;
import com.handy.service.entity.sys.SysResource;
import com.handy.service.entity.sys.SysRole;
import com.handy.service.service.sys.ISysPermissionService;
import com.handy.service.service.sys.ISysResourceService;
import com.handy.service.service.sys.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/29 12:44
 */
@RestController
@RequestMapping("/api/admin/setting/rolePermission")
@Api(value = "角色权限")
public class RolePermissionApiController extends BaseController {
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysResourceService sysResourceService;

    @GetMapping("/list")
    @ApiOperation(value = "获取角色列表数据")
    public PageVO list() {
        val wrapper = new QueryWrapper<SysRole>();
        LambdaQueryWrapper<SysRole> queryWrapper = wrapper.lambda();
        queryWrapper.orderByDesc(SysRole::getOrdinal);
        val list = sysRoleService.list(wrapper);
        return PageVO.pageVO(list, (long) list.size());
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/confirm/add")
    public ResultVO confirmAdd(@ApiIgnore() HttpSession session, SysRole sysRole,
                               @ApiParam(name = "rolePermissionStr", value = "角色权限") String rolePermissionStr) {
        JSONArray array = JSONUtil.parseArray(rolePermissionStr);
        List<RolePermissionParam> rolePermissionParamList = new ArrayList<>();
        for (Object object : array) {
            rolePermissionParamList.add(JSONUtil.toBean(object.toString(), RolePermissionParam.class));
        }
        val wrapper = new QueryWrapper<SysRole>();
        LambdaQueryWrapper<SysRole> queryWrapper = wrapper.lambda();
        queryWrapper.eq(SysRole::getName, sysRole.getName());
        val list = sysRoleService.list(wrapper);
        if (CollUtil.isNotEmpty(list)) {
            return ResultVO.failure("该角色已存在");
        }
        sysRole.setCreator(getLoginUser(session).getCode());
        val rst = sysRoleService.save(sysRole);
        if (rst) {
            addRolePermission(sysRole.getId(), getLoginUser(session).getCode(), rolePermissionParamList);
        }
        return rst ? ResultVO.success("新增成功") : ResultVO.failure("新增失败");
    }

    @ApiOperation(value = "编辑角色")
    @PutMapping("/confirm/edit")
    public ResultVO confirmEdit(@ApiIgnore() HttpSession session, SysRole sysRole,
                                @ApiParam(name = "rolePermissionStr", value = "角色权限") String rolePermissionStr) {
        // 更改角色信息
        val rst = sysRoleService.updateById(sysRole);
        // 删除角色全部权限
        val sysPermissionWrapper = new QueryWrapper<SysPermission>();
        LambdaQueryWrapper<SysPermission> sysPermissionQueryWrapper = sysPermissionWrapper.lambda();
        sysPermissionQueryWrapper.eq(SysPermission::getRoleId, sysRole.getId());
        sysPermissionService.remove(sysPermissionWrapper);
        // 新增角色权限
        JSONArray array = JSONUtil.parseArray(rolePermissionStr);
        List<RolePermissionParam> rolePermissionParamList = new ArrayList<>();
        for (Object object : array) {
            rolePermissionParamList.add(JSONUtil.toBean(object.toString(), RolePermissionParam.class));
        }
        if (rst) {
            addRolePermission(sysRole.getId(), getLoginUser(session).getCode(), rolePermissionParamList);
        }
        return rst ? ResultVO.success("编辑成功") : ResultVO.failure("编辑失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/confirm/delete/{id}")
    public ResultVO confirmDelete(@ApiParam(name = "id", value = "角色id") Long id) {
        // 删除角色
        val rst = sysRoleService.removeById(id);
        // 删除角色全部权限
        val sysPermissionWrapper = new QueryWrapper<SysPermission>();
        LambdaQueryWrapper<SysPermission> sysPermissionQueryWrapper = sysPermissionWrapper.lambda();
        sysPermissionQueryWrapper.eq(SysPermission::getRoleId, id);
        sysPermissionService.remove(sysPermissionWrapper);
        return rst ? ResultVO.success("删除成功") : ResultVO.failure("删除失败");
    }

    /**
     * 新增角色权限
     *
     * @param id                      角色id
     * @param creator                 创建人
     * @param rolePermissionParamList 角色集合
     */
    private void addRolePermission(Long id, String creator, List<RolePermissionParam> rolePermissionParamList) {
        val sysPermission = new SysPermission();
        sysPermission.setRoleId(id);
        for (RolePermissionParam permissionParam : rolePermissionParamList) {
            sysPermission.setSubmenuId(permissionParam.getId());
            sysPermission.setCreator(creator);
            sysPermissionService.save(sysPermission);
            if (CollUtil.isNotEmpty(permissionParam.getChildren())) {
                addRolePermission(id, creator, permissionParam.getChildren());
            }
        }
    }

    @GetMapping("/viewPermission")
    @ApiOperation(value = "根据角色id获取对应的权限树")
    public List<RolePermissionParam> viewPermission(@ApiParam(name = "id", value = "角色id") Long id) {
        List<RolePermissionParam> list = new ArrayList<>();
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(id);
        val sysPermissionList = sysPermissionService.findByRoleIdList(roleIdList);
        if (CollUtil.isNotEmpty(sysPermissionList)) {
            List<Long> idList = new ArrayList<>();
            for (SysPermission sysPermission : sysPermissionList) {
                idList.add(sysPermission.getSubmenuId());
            }
            val sysResourceList = sysResourceService.findByIdList(idList);
            list = set(sysResourceList, "0", true, null);
        }
        return list;
    }

    @GetMapping("/addPermission")
    @ApiOperation(value = "新增角色时候获取全部权限菜单树")
    public List<RolePermissionParam> addPermission() {
        return set(sysResourceService.list(), "0", false, null);
    }

    /**
     * 轮训获取对应的权限
     *
     * @param resourceList 权限列表
     * @param superId      父级
     * @param spread       是否展开
     * @param menuIdList   拥有的权限id集合
     * @return
     */
    private List<RolePermissionParam> set(List<SysResource> resourceList, String superId, Boolean spread, List<Long> menuIdList) {
        val list = new ArrayList<RolePermissionParam>();
        for (SysResource sysResource : resourceList) {
            if (superId.equals(sysResource.getSuperId())) {
                val param = new RolePermissionParam();
                param.setTitle(sysResource.getTitle());
                param.setId(sysResource.getId());
                param.setSpread(spread);
                param.setDisabled(false);
                val rolePermissionParams = set(resourceList, sysResource.getId().toString(), spread, menuIdList);
                if (CollUtil.isNotEmpty(rolePermissionParams)) {
                    param.setChildren(rolePermissionParams);
                } else {
                    param.setChildren(new ArrayList<RolePermissionParam>());
                    // 判断是否属于已经存在
                    if (CollUtil.isNotEmpty(menuIdList) && menuIdList.contains(sysResource.getId())) {
                        param.setChecked(true);
                    } else {
                        param.setChecked(false);
                    }
                }
                list.add(param);
            }
        }
        return list;
    }

    @GetMapping("/editPermission")
    @ApiOperation(value = "编辑角色时候获取角色权限菜单树")
    public List<RolePermissionParam> editPermission(@ApiParam(name = "roleId", value = "角色id") Long roleId) {
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(roleId);
        val sysPermissionList = sysPermissionService.findByRoleIdList(roleIdList);
        List<Long> menuIdList = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            menuIdList.add(sysPermission.getSubmenuId());
        }
        return set(sysResourceService.list(), "0", false, menuIdList);
    }
}
