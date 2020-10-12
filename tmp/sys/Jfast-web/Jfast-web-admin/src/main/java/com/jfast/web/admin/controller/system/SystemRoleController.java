package com.jfast.web.admin.controller.system;

import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import com.jfast.web.admin.controller.ApiController;
import com.jfast.web.admin.service.system.SystemRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static javax.swing.UIManager.getInt;


/**
 * 角色管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:39
 */
@RestController
@RequestMapping("/system/role")
public class SystemRoleController extends ApiController {

    @Autowired
    private SystemRoleService systemRoleService;

    @GetMapping({"", "list"})
    @RequiresPermissions("system:role:list")
    public Map list(@RequestParam Map params) {
        return getPageData(params);
    }

    @PostMapping({"", "saveOrUpdate"})
    @RequiresPermissions({"system:role:save", "system:role:update"})
    public ResultCode saveOrUpdate(@RequestBody Map params) {
        Integer id = getInt(params.get("id"));
        boolean updateFlag = false;
        String sqlId = "system.role.save";
        if (ObjectUtils.isNotEmpty(id)) {
            updateFlag = true;
            sqlId = "system.role.update";
        } else {
            Integer createType = (Integer)params.get("create_type");
            if (createType == ResultCode.SUCCESS) {
                return new ResultCode(ResultCode.FAIL, "您不能编辑系统内置角色");
            }
        }
        params.put("sqlId", sqlId);
        return systemRoleService.saveOrUpdate(updateFlag, "角色", params);
    }

    /**
     * 删除角色
     * @param params
     * @return
     */
    @DeleteMapping
    @RequiresPermissions("system:role:deleteById")
    public ResultCode deleteRole(@RequestBody Map params) {
        Integer createType = (Integer)params.get("create_type");
        if (createType != null && createType == ResultCode.SUCCESS) {
            return new ResultCode(ResultCode.FAIL, "您不能删除系统内置角色");
        }
        params.put("operation", "角色" + params.get("name"));
        return systemRoleService.deleteById(params);
    }

    @PostMapping("savePermission")
    @RequiresPermissions("system:role:savePermission")
    public ResultCode savePermission(@RequestBody Map params) {
        return systemRoleService.savePermission(params);
    }

    /**
     * 批量删除
     * @param params
     * @return
     */
    @DeleteMapping("batchDelete")
    @RequiresPermissions("system:role:batchDelete")
    public ResultCode batchDelete(@RequestBody Map params) {
        params.put("operation", "角色");
        return systemRoleService.deleteById(params);
    }
}
