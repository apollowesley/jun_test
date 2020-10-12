package cn.backflow.admin.controller;

import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.common.secure.Permissions;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.Role;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.RoleService;
import cn.backflow.lib.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController extends BaseSpringController {
    private static final String DEFAULT_SORT_COLUMNS = "id desc"; //默认多列排序,example: username desc,createTime asc

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /* 列表 */
    @RequestMapping
    @Permissions("role.view")
    public Object index(HttpServletRequest request) throws Exception {
        PageRequest pageRequest = pageRequest(request, DEFAULT_SORT_COLUMNS);
        return roleService.findPage(pageRequest);
    }

    /* 获取指定角色拥有的权限 */
    @RequestMapping("owns")
    public List<Integer> owns(@RequestParam("id") Integer id) {
        return roleService.findOwnedPermissionId(Collections.singletonMap("roleId", id));
    }

    /* 获取当前用户所有角色 */
    @RequestMapping("all")
    @Permissions("role.view")
    public List<Role> all() {
        return roleService.findAll(Collections.singletonMap("status", 1));
    }

    /* 获取当前用户所有 ID=>角色 映射MAP */
    @RequestMapping("map")
    @Permissions("role.view")
    public Object map() {
        return roleService.findMap(Collections.singletonMap("status", 1), "id");
    }

    /* 获取角色拥有的权限集合 */
    @RequestMapping("perms")
    public Object perms(@RequestParam("id") Integer id) {
        return roleService.findOwnedPermission(Collections.singletonMap("roleId", id));
    }

    /* 保存角色权限更改 */
    @Permissions("role.edit")
    @RequestMapping(value = "perms", method = RequestMethod.PUT)
    public Object perms(@RequestParam("id") Integer id,
                        @RequestParam(value = "perms", required = false) Integer[] perms) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (id == -1) {
            return JsonMap.fail("系统管理员不可修改");
        }
        roleService.saveRolePermissions(id, perms);
        return json;
    }

    /* 保存新增 */
    @Permissions("role.edit")
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@Valid Role role, BindingResult errors) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        try {
            roleService.save(role);
        } catch (Exception e) {
            if (e.getCause().getLocalizedMessage().startsWith("Duplicate entry")) {
                return json.success(false).msg("角色名 [" + role.getName() + "] 已存在!");
            }
        }
        return json.put("role", role);
    }

    /* 保存更新 */
    @Permissions("role.edit")
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@Valid Role role, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        User user = getCurrentUser(request);
        if (role.getId() == -1 && !user.isAdmin()) {
            return json.success(false).msg("系统管理员只能由系统管理员修改!");
        }
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        try {
            roleService.update(role);
        } catch (Exception e) {
            if (e.getCause().getLocalizedMessage().startsWith("Duplicate entry")) {
                return json.success(false).msg("角色名 [" + role.getName() + "] 已存在!");
            }
        }
        return json.put("role", role);
    }

    /* 删除 */
    @Permissions("role.del")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Integer id) {
        if (id == -1) {
            return JsonMap.fail("系统管理员角色不可删除");
        }
        if (id == 0) {
            return JsonMap.fail("不能删除系统默认角色");
        }
        roleService.deleteById(id);
        return JsonMap.succeed();
    }
}