package cn.backflow.admin.service;

import cn.backflow.admin.dao.DictDao;
import cn.backflow.admin.dao.PermissionDao;
import cn.backflow.admin.dao.RoleDao;
import cn.backflow.admin.entity.Dict;
import cn.backflow.admin.entity.Permission;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@CacheConfig(cacheNames = "perm_cache")
public class PermissionService extends BaseService<Permission, Integer> {

    private final PermissionDao permissionDao;
    private final RoleDao roleDao;
    private final DictDao dictDao;

    @Autowired
    public PermissionService(PermissionDao permissionDao, RoleDao roleDao, DictDao dictDao) {
        this.permissionDao = permissionDao;
        this.roleDao = roleDao;
        this.dictDao = dictDao;
    }

    public List<Permission> findAll() throws DataAccessException {
        return super.findAll(null);
    }

    /**
     * 查询当前用户的所有权限
     *
     * @param user 当前登录用户
     * @return Map&lt;PK, Permission&gt;
     */
    public Map<Comparable, Permission> findMap(User user) {
        Map<String, Object> params = null;
        if (user != null && !user.isAdmin()) {
            params = Collections.singletonMap("userId", user.getId());
        }
        return permissionDao.findMap(params, "id");
    }

    /**
     * 查找子元素
     *
     * @param parent 父元素ID
     * @param direct 是否只查找直接子元素
     */
    public List<Permission> findByParent(Integer parent, boolean direct) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("parent", parent);
        parameter.put("direct", direct);
        return permissionDao.findAll(parameter);
    }


    /**
     * 设置权限层级与祖先路径
     */
    @Transactional
    private void setPermissionAncestorAndLevel(Permission permission, Permission parent) {
        if (permission.getId() == null)
            permissionDao.save(permission);
        int level = 1;
        String ancestors = permission.getId().toString();
        if (permission.getParent() != null) {
            if (parent == null || !Objects.equals(parent.getId(), permission.getParent())) {
                parent = getById(permission.getParent());
            }
            level = parent.getLevel() + 1;
            ancestors = parent.getAncestors() + "," + ancestors;
        }
        permission.setLevel(level);
        permission.setAncestors(ancestors);
    }

    /**
     * 更新权限排序
     *
     * @param id     权限ID
     * @param from   起始位置
     * @param to     结束位置
     * @return 影响记录数
     */
    @Transactional
    public int updatePriority(Integer id, Integer from, Integer to) {
        List<Permission> siblings = permissionDao.findSiblings(id, false);
        for (int i = 0, len = siblings.size(); i < len; i++) {
            Permission d = siblings.get(i);
            d.setPriority(i);
            if (i == from) {
                d.setPriority(to);
            }
            if (i == to) {
                d.setPriority(from);
            }
        }
        // TODO 优化: 通过UPDATE单个priority字段更新
        return permissionDao.updateBatch(siblings);
    }


    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public int save(Permission entity) throws DataAccessException {
        setPermissionAncestorAndLevel(entity, null);
        return super.saveOrUpdate(entity);
    }


    @Transactional
    @CacheEvict(allEntries = true)
    public int update(Permission entity) throws DataAccessException {
        setPermissionAncestorAndLevel(entity, null);
        int rows = super.saveOrUpdate(entity);
        permissionDao.updateChildrenAncestors(entity);
        return rows;
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public int deleteById(Integer id) throws DataAccessException {
        List<Permission> children = findByParent(id, true);
        if (children.size() > 0)
            throw new IncorrectResultSizeDataAccessException("您要删除的记录含有子元素, 删除失败!", 1);
        roleDao.deleteRolePermissionsByPermissionId(id);
        return super.deleteById(id);
    }

    /**
     * 与子权限一并保存
     *
     * @param parent   权限对象
     * @param children 子权限标识 (通过数据字典`default_permission_group`获取明细)
     */
    @Transactional
    public int saveWithChildren(Permission parent, String[] children) {
        int effected = save(parent);
        if (children == null || children.length == 0) {
            return effected;
        }
        // 从字典获取默认权限组
        Map<Comparable, Dict> dicts = dictDao.findMapByCode("default_permission_group", "key");

        // 保存相应的权限
        for (String child : children) {
            Dict d = dicts.get(child);
            Permission p = new Permission();
            p.setName(d.getValue() + parent.getName());
            p.setCode(parent.getCode() + "." + d.getKey());
            p.setIcon(d.getComment());
            p.setPriority(d.getPriority());
            p.setParent(parent.getId());
            d.setDescription(p.getName());
            setPermissionAncestorAndLevel(p, parent);
            update(p);
        }
        return effected;
    }
}