package com.handy.controller.admin.users;

import com.handy.service.entity.sys.SysRole;
import com.handy.service.entity.sys.SysRolesAccount;
import com.handy.service.service.sys.ISysAccountService;
import com.handy.service.service.sys.ISysRoleService;
import com.handy.service.service.sys.ISysRolesAccountService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author handy
 * @Description: {后台用户管理}
 * @date 2019/8/26 11:53
 */
@Controller
@RequestMapping("/admin/users/user")
@ApiIgnore()
public class UserController {
    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRolesAccountService sysRolesAccountService;

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("/list")
    public String list() {
        return "admin/users/user/list";
    }

    /**
     * 用户详情
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/view")
    public String view(Model model, Long id) {
        val sysAccount = sysAccountService.getById(id);
        model.addAttribute("sysAccount", sysAccount);
        val roles = sysRoleService.list();
        val sysRolesAccounts = sysRolesAccountService.findByAccountId(id);
        for (SysRole role : roles) {
            for (SysRolesAccount sysRolesAccount : sysRolesAccounts) {
                if (sysRolesAccount.getRoleId().equals(role.getId())) {
                    role.setIsChecked(true);
                }
            }
        }
        model.addAttribute("roles", roles);
        return "admin/users/user/view";
    }

    /**
     * 用户新增页面
     *
     * @return
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("roles", sysRoleService.list());
        return "admin/users/user/add";
    }

    /**
     * 用户编辑页面
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        val sysAccount = sysAccountService.getById(id);
        model.addAttribute("sysAccount", sysAccount);
        val roles = sysRoleService.list();
        val sysRolesAccounts = sysRolesAccountService.findByAccountId(id);
        for (SysRole role : roles) {
            for (SysRolesAccount sysRolesAccount : sysRolesAccounts) {
                if (sysRolesAccount.getRoleId().equals(role.getId())) {
                    role.setIsChecked(true);
                }
            }
        }
        model.addAttribute("roles", roles);
        return "admin/users/user/edit";
    }

}
