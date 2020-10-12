package cn.backflow.admin.service;

import cn.backflow.admin.dao.PermissionDao;
import cn.backflow.admin.dao.RoleDao;
import cn.backflow.admin.entity.Permission;
import cn.backflow.admin.entity.Role;
import cn.backflow.admin.entity.RolePermission;
import cn.backflow.admin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseService<Role, Integer> {

    private final PermissionDao permissionDao;
    private final RoleDao roleDao;

    @Autowired
    public RoleService(PermissionDao permissionDao, RoleDao roleDao) {
        this.permissionDao = permissionDao;
        this.roleDao = roleDao;
    }

    public List<Integer> findOwnedPermissionId(Object parameter) {
        return roleDao.findPermissionId(parameter);
    }

    public List<String> findOwnedPermission(Object parameter) {
        return roleDao.findPermission(parameter);
    }

    @Transactional
    private void deleteRolePermissionsByRoleId(Integer roleId) {
        roleDao.deleteRolePermissions(Collections.singletonMap("roleId", roleId));
    }

    /**
     * 保存角色权限
     *  @param id    角色ID
     * @param perms 权限ID集合
     */
    @Transactional
    public void saveRolePermissions(Integer id, Integer[] perms) {
        deleteRolePermissionsByRoleId(id);
        if (perms == null || perms.length == 0) {
            return;
        }
        List<Permission> permissions = permissionDao.findAll(Collections.singletonMap("ids", perms));
        if (permissions.isEmpty()) {
            return;
        }
        List<RolePermission> rolePermissions = permissions.stream()
                .map(p -> new RolePermission(id, p.getId(), p.getCode()))
                .collect(Collectors.toList());
        roleDao.insertRolePermission(rolePermissions);
    }

    @Transactional
    @CacheEvict(value = "perm_cache", allEntries = true)
    public int persistWithPermission(Role role, Set<Integer> perms) {
        // 保存角色
        int effected = role.getId() == null ? save(role) : update(role);
        // 清空角色权限
        deleteRolePermissionsByRoleId(role.getId());

        if (!perms.isEmpty()) {
            List<Permission> permissions = permissionDao.findByIds(perms); // 根据ID获取权限标识
            List<RolePermission> rps = permissions.stream()
                    .map(p -> new RolePermission(role.getId(), p.getId(), p.getCode()))
                    .collect(Collectors.toList());

            // 保存角色权限
            roleDao.insertRolePermission(rps);
        }
        return effected;
    }

    @Deprecated /* 已弃用, 用户只与单个角色关联 */
    @Transactional
    @CacheEvict(value = "perm_cache", allEntries = true)
    public void saveUserRoles(Integer userId, Integer[] roles) {
        roleDao.deleteUserRoles(userId);
        roleDao.saveUserRoles(userId, roles);
    }
}