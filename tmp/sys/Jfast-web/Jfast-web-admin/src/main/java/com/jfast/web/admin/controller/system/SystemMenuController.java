package com.jfast.web.admin.controller.system;


import com.jfast.common.base.BaseController;
import com.jfast.common.utils.ObjectUtils;
import com.jfast.common.utils.ResultCode;
import com.jfast.web.admin.controller.ApiController;
import com.jfast.web.admin.service.system.SystemMenuService;
import com.jfast.web.admin.service.system.SystemRoleService;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:44
 */
@RestController
@RequestMapping("/system/menu")
public class SystemMenuController extends ApiController {

    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private SystemRoleService systemRoleService;

    @GetMapping("getMenuByUserId")
    public List<Map> getMenuByUserId() {
        return systemMenuService.getMenuByUser();
    }

    /**
     * 获取角色拥有菜单
     * @param roleId
     * @return
     */
    @GetMapping("getMenuByRole")
    public List<Integer> getMenuByRole(Integer roleId) {
        return systemRoleService.getMenuListByRoleId(roleId);
    }

    @GetMapping(value = "getMenuByParent")
    public Object getMenuByParent() {
        return systemMenuService.getMenuByParent();
    }

    @GetMapping({"", "list"})
    @RequiresPermissions("system:menu:list")
    public Map list(@RequestParam Map params) {
        return getPageData(params);
    }

    @DeleteMapping
    @RequiresPermissions("system:menu:deleteById")
    public ResultCode deleteById(@RequestBody Map params) {
        params.put("id", params.get("value"));
        return super.deleteById(params);
    }

    @GetMapping("findById")
    public Map findById(Integer id) {
        return systemMenuService.findById(id);
    }

    @PostMapping("saveOrUpdate")
    @RequiresPermissions(value = {"system:menu:save", "system:menu:update"}, logical = Logical.OR)
    public ResultCode saveOrUpdate(@RequestBody Map params) {
        Integer id = (Integer)params.get("id");
        boolean updateFlag = false;
        String name = (String)params.get("name");
        String sqlId = null;
        String message = "";
        if (ObjectUtils.isEmpty(id)) {
            sqlId = "system.menu.save";
            message = "创建菜单" + name;
        } else {
            updateFlag = true;
            sqlId = "system.menu.update";
            message = "更新菜单" + name;
        }
        params.put("sqlId", sqlId);
        return systemMenuService.saveOrUpdate(updateFlag, message, params);
    }
}
