package cn.backflow.admin.dao;

import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.Role;
import cn.backflow.admin.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao extends BaseMyBatisDao<Role, Integer> {


    @Override
    public int saveOrUpdate(Role role) {
        return role.getId() == null ? save(role) : update(role);
    }

    public Page<Role> findByPageRequest(PageRequest pageRequest) {
        return pageQuery("Role.paging", pageRequest);
    }

    public List<String> findPermission(Object parameter) {
        return getSqlSession().selectList("Role.findPermission", parameter);
    }

    public List<Integer> findPermissionId(Object parameter) {
        return getSqlSession().selectList("Role.findPermissionId", parameter);
    }

    /* 已弃用, 用户只与单个角色关联 */
    @Deprecated
    public int saveUserRoles(Integer userId, Integer... roles) {
        if (roles == null || roles.length == 0) {
            return 0;
        }
        Map<String, Object> param = new HashMap<>(2);
        param.put("userId", userId);
        param.put("roles", roles);
        return getSqlSession().insert("Role.insertUserRole", param);
    }

    public int insertRolePermission(List<RolePermission> perms) {
        if (perms == null || perms.isEmpty()) {
            return 0;
        }
        return getSqlSession().insert("Role.insertRolePermission", perms);
    }

    public int deleteRolePermissions(Object parameter) {
        return getSqlSession().delete("Role.deleteRolePermission", parameter);
    }

    public int deleteUserRoles(Integer userId) {
        return getSqlSession().delete("Role.deleteUserRoles", userId);
    }

    public int deleteRolePermissionsByPermissionId(Integer permissionId) {
        return deleteRolePermissions(Collections.singletonMap("permId", permissionId));
    }
}