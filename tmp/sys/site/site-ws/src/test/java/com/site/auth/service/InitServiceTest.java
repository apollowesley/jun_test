package com.site.auth.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.site.auth.model.Permission;
import com.site.auth.model.Role;
import com.site.auth.model.User;
import com.site.auth.service.AuthService;
import com.site.common.constants.Constants;
import com.site.framework.key.GeneratePrimaryKey;

/**
 * @author Vincent.wang
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-dao-*.xml",  "classpath*:spring/applicationContext-mybatis.xml", "classpath*:spring/applicationContext.xml", "classpath*:spring/service/applicationContext-*.xml" })
public class InitServiceTest {

    @Autowired
    private AuthService authService;

    /**
     * 创建角色
     */
    private void addRoles() {
        try {
            Role system = new Role();
            system.setRoleName(Constants.SYSTEM_ROLE_NAME);// 系统管理员
            system.setRoleCode(Constants.SYSTEM_ROLE_CODE);
            system.setRemark(Constants.SYSTEM_ROLE_NAME);
            system.setParentId("0");
            authService.addRole(system);

            Role common = new Role();
            common.setRoleName(Constants.COMMON_ROLE_NAME);// 普通用户
            common.setRoleCode(Constants.COMMON_ROLE_CODE);
            common.setRemark(Constants.COMMON_ROLE_NAME);
            common.setParentId("0");
            authService.addRole(common);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized static Permission newPermission(String menuName, String parentId, String url, Integer lev, Integer sort) {
        Permission per = new Permission();
        String id = GeneratePrimaryKey.getPkValue("t_auth_permission");
        per.setId(id);
        if (StringUtils.isBlank(parentId)) {
            per.setParentId(id);
        } else {
            per.setParentId(parentId);
        }
        per.setMenuName(menuName);
        per.setLev(lev);
        per.setUrl(url);
        per.setSort(sort);
        return per;
    }

    /**
     * 创建菜单
     */
    private void addPermission() {
        try {
            Permission a = newPermission("权限模块", null, null, 1, 1);
            a.setMenuCode(a.getId());
            authService.addPermission(a);

            Permission aa = newPermission("角色", a.getId(), null, 2, 2);
            aa.setMenuCode(a.getMenuCode() + aa.getId());
            authService.addPermission(aa);

            Permission ab = newPermission("用户", a.getId(), null, 2, 3);
            ab.setMenuCode(a.getMenuCode() + ab.getId());
            authService.addPermission(ab);

            Permission ac = newPermission("菜单", a.getId(), null, 2, 4);
            ac.setMenuCode(a.getMenuCode() + ac.getId());
            authService.addPermission(ac);

            Permission aaa = newPermission("角色管理", aa.getId(), "role/manager", 3, 5);
            aaa.setMenuCode(aa.getMenuCode() + aaa.getId());
            authService.addPermission(aaa);

            Permission aba = newPermission("用户管理", ab.getId(), "user/manager", 3, 6);
            aba.setMenuCode(ab.getMenuCode() + aba.getId());
            authService.addPermission(aba);

            Permission aca = newPermission("菜单管理", ac.getId(), "menu/manager", 3, 7);
            aca.setMenuCode(ac.getMenuCode() + aca.getId());
            authService.addPermission(aca);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建用户
     */
    private void addUsers() {
        try {
            Role commonRole = authService.findRoleByCode(Constants.COMMON_ROLE_CODE);// 普通用户
            User wangxin = new User();
            wangxin.setLoginName("wangxin");
            wangxin.setUserName("王鑫");
            wangxin.setEmail("infowangxin@139.com");
            wangxin.setPassword("123456");
            authService.addUser(wangxin, commonRole);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 给角色授权
    private void bindRolePermission() {
        try {
            List<Permission> pers = authService.getPermissions();
            for (Permission p : pers) {
                authService.addRolePermission(Constants.SYSTEM_ROLE_CODE, p.getId()); // 系统管理员
                authService.addRolePermission(Constants.COMMON_ROLE_CODE, p.getId()); // 普通用户
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void init() {
        try {
            addRoles();
            addPermission();
            addUsers();
            bindRolePermission();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
