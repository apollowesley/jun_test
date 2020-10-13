package org.iartisan.admin.template.authentication;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.iartisan.admin.template.authentication.service.entity.UserEntity;
import org.iartisan.admin.template.dao.mapper.SystemUserMapper;
import org.iartisan.admin.template.dao.mapper.SystemUserRoleMapper;
import org.iartisan.admin.template.dao.model.SystemUserDO;
import org.iartisan.admin.template.dao.model.SystemUserRoleDO;
import org.iartisan.admin.template.service.entity.StaffEntity;
import org.iartisan.admin.template.service.query.StaffQueryService;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.constants.DataStatus;
import org.iartisan.runtime.env.EnvContextConfig;
import org.iartisan.runtime.jdbc.PageHelper;
import org.iartisan.runtime.utils.MD5Util;
import org.iartisan.runtime.utils.StringUtils;
import org.iartisan.runtime.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理员管理
 * crud
 *
 * @author King
 * @since 2018/2/22
 */
@Service
public class UserSupportService {

    @Autowired
    private SystemUserMapper systemUserMapper;


    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Autowired
    private RoleSupportService roleSupportService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public SystemUserDO login(String userName, String userPwd) {
        SystemUserDO dbQuery = new SystemUserDO();
        dbQuery.setUserName(userName);
        dbQuery.setUserPwd(userPwd.toLowerCase());
        QueryWrapper<SystemUserDO> queryWrapper = new QueryWrapper<>(dbQuery);
        return systemUserMapper.selectOne(queryWrapper);
    }


    public PageWrapper<UserEntity> getUserPageData(Pagination page, String userName) {
        SystemUserDO userDO = new SystemUserDO();
        if (StringUtils.isNotEmpty(userName)) {
            userDO.setUserName(userName);
        }
        PageWrapper<SystemUserDO> dbResult = PageHelper.getPageData(systemUserMapper, page, userDO);
        PageWrapper<UserEntity> result = new PageWrapper<>(dbResult.getPage());
        List<UserEntity> pageList = new ArrayList<>();
        for (SystemUserDO o : dbResult.getRows()) {
            UserEntity bean = new UserEntity();
            bean.setUserName(o.getUserName());
            bean.setUserId(o.getUserId());
            bean.setUserStatus(o.getStatus());
            bean.setCreateDate(o.getCreateTime());
            bean.setRoles(roleSupportService.getUserRoleNames(o.getUserId()));
            pageList.add(bean);
        }
        result.setRows(pageList);
        return result;
    }

    @Autowired
    private StaffQueryService staffQueryService;

    public void addUser(UserEntity userEntity) {
        SystemUserDO dbInsert = new SystemUserDO();
        String userId = UUIDUtil.shortId();
        dbInsert.setUserId(userId);
        if (StringUtils.isNotEmpty(userEntity.getStaffId())) {
            dbInsert.setStaffId(userEntity.getStaffId());
            StaffEntity staffEntity = staffQueryService.getDataById(userEntity.getStaffId());
            userEntity.setUserName(staffEntity.getStaffName());
            userEntity.setUserStatus(DataStatus.E.name());
        }
        dbInsert.setUserName(userEntity.getUserName());
        dbInsert.setStatus(userEntity.getUserStatus());
        dbInsert.setCreateTime(new Date());
        try {
            //设置默认密码
            dbInsert.setUserPwd(MD5Util.MD5(EnvContextConfig.get("admin.default.password", "123456")).toLowerCase());
            systemUserMapper.insert(dbInsert);
            //插入角色列表
            addUserRole(userId, userEntity.getRoles());
        } catch (Exception e) {
            logger.error("addUser Exception:", e);
        }
    }

    public void changeStatus(String userId, String status) {
        //删除用户 非物理删除
        SystemUserDO dbModify = new SystemUserDO();
        dbModify.setUserId(userId);
        dbModify.setStatus(status);
        dbModify.setUpdateTime(new Date());
        systemUserMapper.updateById(dbModify);
    }

    public void modifyData(UserEntity userEntity) {
        SystemUserRoleDO entity = new SystemUserRoleDO();
        entity.setUserId(userEntity.getUserId());
        //更新角色信息
        Wrapper<SystemUserRoleDO> dbDel = new QueryWrapper<>(entity);
        systemUserRoleMapper.delete(dbDel);
        addUserRole(userEntity.getUserId(), userEntity.getRoles());
    }

    public void modifyPwd(UserEntity userEntity) {
        SystemUserDO dbModify = new SystemUserDO();
        dbModify.setUserId(userEntity.getUserId());
        dbModify.setUserPwd(userEntity.getUserPwd());
        dbModify.setUpdateTime(new Date());
        systemUserMapper.updateById(dbModify);
    }

    private void addUserRole(String userId, String roleData) {
        if (StringUtils.isNotEmpty(roleData)) {
            String[] roles = roleData.split(",");
            for (String role : roles) {
                SystemUserRoleDO db = new SystemUserRoleDO();
                db.setUserId(userId);
                db.setRoleId(role);
                db.setCreateTime(new Date());
                systemUserRoleMapper.insert(db);
            }
        }
    }

    public void deleteByUserId(String userId) {
        systemUserMapper.deleteById(userId);
    }

    public String getStaffId(String userId) {
        SystemUserDO dbResult = systemUserMapper.selectById(userId);
        if (null != dbResult) {
            return dbResult.getStaffId();
        }
        return "";
    }

}
