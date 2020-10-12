package cn.backflow.admin.controller;


import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.common.secure.Permissions;
import cn.backflow.admin.common.treeable.Treeable;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.Department;
import cn.backflow.admin.service.DepartmentService;
import cn.backflow.lib.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("department")
public class DepartmentController extends BaseSpringController {
    private static final String DEFAULT_SORT_COLUMNS = "updated desc";

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /* 显示 */
    @RequestMapping("{id}")
    @Permissions("department.view")
    public Object byId(@PathVariable Integer id) throws Exception {
        return departmentService.getById(id);
    }

    /* 树形表格 */
    @RequestMapping("treetable")
    @Permissions("department.view")
    public Object treetable(Department department, HttpServletRequest request) throws Exception {
        PageRequest pr = pageRequest(request, DEFAULT_SORT_COLUMNS);
        Map<Comparable, Department> map = departmentService.findMap(pr.getFilters());
        return Treeable.sort(map.values(), department.getParent(), new ArrayList<>());
    }

    /* 部门树, 为jstree.js构建 */
    @RequestMapping("jstree")
    @Permissions("department.view")
    public Object jstree(@RequestParam(value = "selected[]", required = false) Integer[] selected, HttpServletRequest request) {
        List<Department> list = departmentService.findAll(null);
        return Treeable.jstree(list, Arrays.asList(selected), null);
    }

    /* 部门树, 过滤后 */
    @RequestMapping("tree")
    @Permissions("department.view")
    public Object tree(@RequestParam(required = false) boolean map) throws Exception {
        List<Department> departments = departmentService.findAll(null);

        if (map) {
            List<Treeable> treeables = Treeable.tree(new ArrayList<>(departments));
            return JsonMap.succeed()
                    .put("tree", treeables)
                    .put("map", departments.stream().collect(Collectors.toMap(Department::getId, Function.identity())));
        } else {
            return Treeable.tree(departments);
        }
    }

    /* 部门树, 含所有 */
    @RequestMapping("all")
    @Permissions("department.view")
    public Object all() {
        return departmentService.findAll(null);
    }

    /* 保存新增 */
    @Permissions("department.edit")
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@Valid Department department, BindingResult errors) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        departmentService.save(department);
        return json;
    }

    @RequestMapping(value = "priority", method = RequestMethod.PUT)
    public Object priority(
            @RequestParam(value = "id") Integer id,         // 权限ID
            @RequestParam(value = "from") Integer from,     // fromIndex
            @RequestParam(value = "to") Integer to) {       // toIndex
        int effected = departmentService.updatePriority(id, from, to);
        return new JsonMap(effected > 0);
    }


    /* 保存更新 */
    @Permissions("department.edit")
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@Valid Department department, BindingResult errors) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        departmentService.update(department);
        return json;
    }

    /* 删除 */
    @Permissions("department.del")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Integer id) {
        departmentService.deleteById(id);
        return JsonMap.succeed();
    }
}