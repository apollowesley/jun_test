package cn.backflow.admin.dao;

import cn.backflow.admin.dao.base.BaseMyBatisDao;
import cn.backflow.admin.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PermissionDao extends BaseMyBatisDao<Permission, Integer> {

    public List<String> findCode(Object parameter) {
        return getSqlSession().selectList("Permission.findCode", parameter);
    }

    public List<Permission> findByIds(Collection<Integer> ids) {
        return getSqlSession().selectList("Permission.findAll", Collections.singletonMap("ids", ids));
    }


    public List<Permission> findSiblings(Integer id, boolean excludeCurrent) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("excludeCurrent", excludeCurrent);
        parameter.put("id", id);
        return getSqlSession().selectList("Permission.findSiblings", parameter);
    }

    /**
     * 更新所有子权限的祖先ID路径
     *
     * @param parent 父权限
     * @return 更新记录数
     */
    public int updateChildrenAncestors(Permission parent) {
        return getSqlSession().update("Permission.updateChildrenAncestors", parent);
    }
}