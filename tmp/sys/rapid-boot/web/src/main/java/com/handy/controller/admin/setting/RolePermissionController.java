package com.handy.controller.admin.setting;

import com.handy.controller.BaseController;
import com.handy.service.service.sys.ISysRoleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author handy
 * @Description: {角色权限}
 * @date 2019/8/29 11:38
 */
@Controller
@RequestMapping("/admin/setting/rolePermission")
@ApiIgnore()
public class RolePermissionController extends BaseController {
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 角色列表
     *
     * @return
     */
    @GetMapping("/list")
    public String list() {
        return "admin/setting/role/list";
    }

    /**
     * 新增角色
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return "admin/setting/role/add";
    }

    /**
     * 编辑角色
     *
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        val sysRole = sysRoleService.getById(id);
        model.addAttribute("sysRole", sysRole);
        return "admin/setting/role/edit";
    }
}
