package org.iartisan.admin.template.authentication;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.iartisan.admin.template.authentication.service.entity.RoleEntity;
import org.iartisan.admin.template.authentication.service.entity.UserEntity;
import org.iartisan.admin.template.authentication.service.entity.UserRoleEntity;
import org.iartisan.admin.template.dao.mapper.SystemRoleMapper;
import org.iartisan.admin.template.dao.mapper.SystemRolePermissionMapper;
import org.iartisan.admin.template.dao.mapper.SystemUserMapper;
import org.iartisan.admin.template.dao.mapper.SystemUserRoleMapper;
import org.iartisan.admin.template.dao.model.SystemRoleDO;
import org.iartisan.admin.template.dao.model.SystemRolePermissionDO;
import org.iartisan.admin.template.dao.model.SystemUserDO;
import org.iartisan.admin.template.dao.model.SystemUserRoleDO;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.jdbc.PageHelper;
import org.iartisan.runtime.utils.CollectionUtil;
import org.iartisan.runtime.utils.StringUtils;
import org.iartisan.runtime.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * auth service
 *
 * @author King
 * @since 2018/2/28
 */
@Service
public class RoleSupportService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private SystemRolePermissionMapper systemRolePermissionMapper;

    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    public List<RoleEntity> getAllRoles() {
        List<SystemRoleDO> dbRoleResult = systemRoleMapper.selectList(Wrappers.emptyWrapper());
        List<RoleEntity> result = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(dbRoleResult)) {
            for (SystemRoleDO roleDO : dbRoleResult) {
                RoleEntity entity = new RoleEntity();
                entity.setRoleId(roleDO.getRoleId());
                entity.setRoleName(roleDO.getRoleName());
                result.add(entity);
            }
        }
        return result;
    }

    public RoleEntity getRoleDetail(String roleId) {
        SystemRoleDO dbResult = systemRoleMapper.selectById(roleId);
        RoleEntity result = new RoleEntity();
        if (null != dbResult) {
            result.setRoleId(dbResult.getRoleId());
            result.setRoleName(dbResult.getRoleName());
        }
        return result;
    }

    public PageWrapper<RoleEntity> getRolePageData(Pagination page, String roleName) {
        SystemRoleDO roleDO = new SystemRoleDO();
        if (StringUtils.isNotEmpty(roleName)) {
            roleDO.setRoleName(roleName);
        }
        PageWrapper<SystemRoleDO> dbResult = PageHelper.getPageData(systemRoleMapper, page, roleDO);
        PageWrapper<RoleEntity> result = new PageWrapper<>(dbResult.getPage());
        List<RoleEntity> pageList = new ArrayList<>();
        for (SystemRoleDO o : dbResult.getRows()) {
            RoleEntity bean = new RoleEntity();
            bean.setRoleId(o.getRoleId());
            bean.setRoleName(o.getRoleName());
            bean.setCreateTime(o.getCreateTime());
            pageList.add(bean);
        }
        result.setRows(pageList);
        return result;
    }

    public void addRole(RoleEntity roleEntity) {
        SystemRoleDO dbRoleInsert = new SystemRoleDO();
        dbRoleInsert.setRoleName(roleEntity.getRoleName());
        String roleId = roleEntity.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            roleId = UUIDUtil.shortId();
        } else {
            //如果不为空则证明之前存在该授权需要删除再添加
            deletePermissionByRoleId(roleId);
        }
        dbRoleInsert.setRoleId(roleId);
        dbRoleInsert.setCreateTime(new Date());
        systemRoleMapper.insert(dbRoleInsert);
        //添加permission
        String permissionStr = roleEntity.getPermissions();
        if (StringUtils.isNotEmpty(permissionStr)) {
            String[] permissions = permissionStr.split(",");
            for (String permission : permissions) {
                SystemRolePermissionDO dbPermission = new SystemRolePermissionDO();
                dbPermission.setRoleId(roleId);
                String[] types = permission.split("\\|");
                dbPermission.setPermissionId(types[0]);
                if (types.length < 2) {
                    dbPermission.setPermissionType("m");
                } else {
                    dbPermission.setPermissionType(types[1]);
                }
                dbPermission.setCreateTime(new Date());
                systemRolePermissionMapper.insert(dbPermission);
            }
        }
    }

    //获取角色列表
    public List<String> getRoleIdsByUserId(String userId) {
        SystemUserRoleDO systemUserRoleDO = new SystemUserRoleDO();
        systemUserRoleDO.setUserId(userId);
        Wrapper<SystemUserRoleDO> query = new QueryWrapper<>(systemUserRoleDO);
        List<SystemUserRoleDO> dbResult = systemUserRoleMapper.selectList(query);
        if (null == dbResult) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (SystemUserRoleDO userRoleDO : dbResult) {
            result.add(userRoleDO.getRoleId());
        }
        return result;
    }

    //获取角色列表
    public UserRoleEntity getRoleByUserId(String userId) {
        List<String> userRoleIds = getRoleIdsByUserId(userId);
        UserRoleEntity result = new UserRoleEntity();
        //查询用所有角色
        List<RoleEntity> dbRoleResult = getAllRoles();
        if (CollectionUtil.isNotEmpty(dbRoleResult)) {
            List<RoleEntity> roleEntities = new ArrayList<>();
            for (RoleEntity roleEntity : dbRoleResult) {
                if (userRoleIds.contains(roleEntity.getRoleId())) {
                    roleEntity.setOwn(true);
                }
                roleEntities.add(roleEntity);
            }
            result.setRoleEntities(roleEntities);
            //查询用户信息
            SystemUserDO userDO = systemUserMapper.selectById(userId);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userId);
            userEntity.setUserName(userDO.getUserName());
            result.setUserEntity(userEntity);
        }
        return result;
    }


    @Transactional
    public void deleteRole(String roleId) {
        //删除角色
        systemRoleMapper.deleteById(roleId);
        //删除角色关联的用户
        SystemUserRoleDO userRoleDO = new SystemUserRoleDO();
        userRoleDO.setRoleId(roleId);
        Wrapper<SystemUserRoleDO> userWrapper = new QueryWrapper<>(userRoleDO);
        systemUserRoleMapper.delete(userWrapper);
        deletePermissionByRoleId(roleId);
    }

    private void deletePermissionByRoleId(String roleId) {
        //删除角色关联的权限
        SystemRolePermissionDO permissionDO = new SystemRolePermissionDO();
        permissionDO.setRoleId(roleId);
        Wrapper<SystemRolePermissionDO> permissionWrapper = new QueryWrapper<>(permissionDO);
        systemRolePermissionMapper.delete(permissionWrapper);
    }

    @Transactional
    public void modifyRole(RoleEntity roleEntity) {
        RoleEntity entity = getRoleDetail(roleEntity.getRoleId());
        systemRoleMapper.deleteById(roleEntity.getRoleId());
        roleEntity.setRoleName(entity.getRoleName());
        addRole(roleEntity);
    }

    public String getUserRoleNames(String userId) {
        QueryWrapper<SystemUserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_ID", userId);
        List<SystemUserRoleDO> dbResult = systemUserRoleMapper.selectList(wrapper);
        StringBuffer buffer = new StringBuffer();
        if (null != dbResult && dbResult.size() > 0) {
            dbResult.forEach(v -> {
                buffer.append(getRoleDetail(v.getRoleId()).getRoleName());
                buffer.append("&nbsp;&nbsp;&nbsp;");
            });

        }
        return buffer.toString();
    }
}
