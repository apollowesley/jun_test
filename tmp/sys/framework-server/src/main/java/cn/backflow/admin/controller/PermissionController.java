package cn.backflow.admin.controller;

import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.common.secure.Permissions;
import cn.backflow.admin.common.treeable.Treeable;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.Permission;
import cn.backflow.admin.service.PermissionService;
import cn.backflow.lib.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("permission")
public class PermissionController extends BaseSpringController {
    private static final String DEFAULT_SORT_COLUMNS = "priority desc, updated desc"; //默认多列排序,example: username desc,created asc

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /* 查询 */
    @RequestMapping
    @Permissions("permission.view")
    public Object query(HttpServletRequest request) {
        PageRequest pageRequest = pageRequest(request, DEFAULT_SORT_COLUMNS);
        return permissionService.findPage(pageRequest);
    }

    /* treetable 列表 */
    @RequestMapping("treetable")
    @Permissions("permission.view")
    public Object treetable() {
        Collection<Permission> list = permissionService.findAll();
        long start = System.currentTimeMillis();
        List<Treeable> treeables = Treeable.sort(list, 0, new ArrayList<>());
        System.out.println(System.currentTimeMillis() - start);
        return treeables;
    }

    /* 获取 */
    @RequestMapping("{id}")
    @Permissions("permission.view")
    public Object byId(@PathVariable Integer id) throws Exception {
        return permissionService.getById(id);
    }

    @RequestMapping("jstree")
    public List<Treeable.Treenode> jstree(
            @RequestParam(value = "pid", required = false) Integer pid,
            @RequestParam(value = "selected", required = false) Collection<Integer> selected,
            @RequestParam(value = "unselectable", required = false) Collection<Integer> unselectable) {
        List<Permission> permissions = permissionService.findAll(Collections.singletonMap("parentId", pid));
        return Treeable.jstree(permissions, selected, unselectable);
    }

    @RequestMapping("tree")
    public List<Treeable> tree(@RequestParam(value = "pid", required = false) Integer pid) {
        List<Permission> permissions = permissionService.findAll(Collections.singletonMap("parentId", pid));
        return Treeable.tree(permissions);
    }

    @RequestMapping(value = "priority", method = RequestMethod.PUT)
    public Object priority(
            @RequestParam(value = "id") Integer id,         // 权限ID
            @RequestParam(value = "from") Integer from,     // fromIndex
            @RequestParam(value = "to") Integer to) {       // toIndex
        int effected = permissionService.updatePriority(id, from, to);
        return new JsonMap(effected > 0);
    }

    /* 保存新增 */
    @Permissions("permission.edit")
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@Valid Permission permission, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        String[] children = request.getParameterValues("children");
        permissionService.saveWithChildren(permission, children);
        return json;
    }

    /* 保存更新 */
    @Permissions("permission.edit")
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@Valid Permission permission, BindingResult errors) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        permissionService.update(permission);
        return json;
    }

    /* 删除 */
    @Permissions("permission.del")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Integer id) {
        permissionService.deleteById(id);
        return JsonMap.succeed();
    }
}