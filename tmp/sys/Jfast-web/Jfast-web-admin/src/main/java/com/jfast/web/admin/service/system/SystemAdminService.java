package com.jfast.web.admin.service.system;

import com.jfast.common.constants.EnumConstants;
import com.jfast.common.model.ModelMap;
import com.jfast.common.model.UserSession;
import com.jfast.common.utils.Md5Utils;
import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import com.jfast.web.admin.service.AdminApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 22:05
 */
@Service
@Slf4j
public class SystemAdminService extends AdminApiService {

    @Autowired
    private SystemRoleService systemRoleService;

    public Map list(Map data) {
        List<Map> dataList = (List<Map>)data.get("data");
        for (Map admin : dataList) {
            List<Map> roleList = sqlSessionTemplate.selectList("system.admin.getRole", admin.get("id"));
            List<Integer> roleIds = new ArrayList<>();
            for (Map role : roleList) {
                roleIds.add((Integer)role.get("role_id"));
            }
            admin.put("roleIds", roleIds);
        }
        return data;
    }

    public void loadPermission(UserSession userSession) {
        int adminId = userSession.getUserId();
        List<Map> roleList = getRolesByAUserId(adminId);
        userSession.setRoleList(roleList);
        List<Integer> roleIds = new ArrayList<>();//用户角色id集合
        if (ObjectUtils.isNotEmpty(roleList)) {
            for (Map roleMap : roleList) {
                roleIds.add((Integer)roleMap.get("role_id"));
            }
        }
        List<Map> menuList = systemRoleService.getMenuListByRole(roleIds); //用户菜单集合
        if (ObjectUtils.isNotEmpty(menuList)) {
            for (Map menuMap : menuList) {
                String permissions = (String)menuMap.get("permissions");
                if (ObjectUtils.isNotEmpty(permissions)) {
                    userSession.addPermission(permissions);
                }
            }
        }
    }

    public ResultCode login(String loginName, String password) {
        ResultCode resultCode = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        try {
            subject.login(token);
            resultCode = new ResultCode(ResultCode.SUCCESS, "登录成功");
        } catch (Exception e) {
            log.error("登录失败", e);
            if (e instanceof UnknownAccountException) {
                resultCode = new ResultCode(ResultCode.FAIL, "用户不存在");
            } else {
                resultCode = new ResultCode(ResultCode.FAIL, "用户名或密码错误");
            }
        }
        return resultCode;
    }


    public ResultCode resettingPassword(ModelMap params) {
        try {
            String password = params.getStr("password");
            String newPassword = params.getStr("newPassword");
            Map userMap = getUser();
            String encrypt = (String)userMap.get("encrypt");
            password = Md5Utils.getMd5(password, encrypt);
            String userPassword = (String)userMap.get("password");
            if (!password.equals(userPassword)) {
                return new ResultCode(ResultCode.FAIL, "密码输入错误");
            }
            password = Md5Utils.getMd5(newPassword, (String)userMap.get("encrypt"));
            params.put("password", password);
            params.remove("newPassword");
            params.put("id", userMap.get("id"));
            int result = super.update("system.admin.update", params);
            if (result > 0) {
                saveSystemLog("修改管理员" + userMap.get("login_name") + "密码");
                return new ResultCode(ResultCode.SUCCESS, "密码修改成功, 退出后请使用新密码登录");
            }
        } catch (Exception e) {
            log.error("密码修改失败", e);
        }
        return new ResultCode(ResultCode.FAIL, "密码修改失败");
    }

    public ResultCode updatePassword(Map params) {
        try {
            String password = (String)params.get("password");
            Map userMap = sqlSessionTemplate.selectOne("system.admin.findById", params.get("id"));
            String encrypt = (String)userMap.get("encrypt");
            password = Md5Utils.getMd5(password,  encrypt);
            params.put("password", password);
            int result = super.update("system.admin.update", params);
            if (result > 0) {
                saveSystemLog("重置管理员" + userMap.get("login_name") + "密码");
                return new ResultCode(ResultCode.SUCCESS, "密码重置成功");
            }
        } catch (Exception e) {
            log.error("密码修改失败", e);
        }
        return new ResultCode(ResultCode.FAIL, "密码重置失败");
    }

    public Map findAdminByName(String loginName) {
        return sqlSessionTemplate.selectOne("system.admin.findByName", loginName);
    }

    /**
     * 获取用户角色
     * @param adminId
     * @return
     */
    public List<Map> getRolesByAUserId(int adminId) {
        return sqlSessionTemplate.selectList("system.admin.role.findByUserId", adminId);
    }

    public Map findById(Integer id) {
        try {
            Map result = new HashMap<>();
            List<Map> list = sqlSessionTemplate.selectList("system.admin.role.findByUserId", id);
            List<Integer> roleIds = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(list)) {
                for (Map map : list) {
                    roleIds.add((Integer)map.get("role_id"));
                }
            }
            result.put("roleIds", roleIds);
            return result;
        } catch (Exception e) {
            log.error("操作异常", e);
        }
        return null;
    }

    /**
     * 添加管理员
     * @param params
     * @return
     */
    @Transactional
    public ResultCode saveOrUpdate(Map params) {
        String message = "";
        try {
            checkParams(params);
            Integer id = (Integer)params.get("id");
            List<Integer> roleIds = (List<Integer>)params.get("roleIds");
            params.remove("roleIds");
            if (ObjectUtils.isEmpty(id)) {
                String password = (String)params.get("password");
                String confirmPassword = (String)params.get("confirmPassword");
                params.remove("confirmPassword");
                if (!password.equals(confirmPassword)) {
                    return new ResultCode(ResultCode.FAIL, "密码与确认密码不一致");
                }
                String encrypt = Md5Utils.encodeSalt(Md5Utils.generatorKey());
                params.put("encrypt", encrypt);
                password = Md5Utils.getMd5(password,  encrypt);
                params.put("password", password);
                params.put("create_date", new Date());
                params.put("create_type", EnumConstants.CreateType.ADMIN_CREATE.getValue()); //管理员创建
                message = "添加";
                id = super.save("system.admin.save", params);
            } else {
                super.update("system.admin.update", params);
                message = "修改";
            }
            if (ObjectUtils.isNotEmpty(roleIds)) {
                List<Map> adminRoleList = new ArrayList<>();
                for (Integer roleId : roleIds) {
                    Map adminRoleMap = new HashMap<>();
                    adminRoleMap.put("roleId", roleId);
                    adminRoleMap.put("adminId", id);
                    adminRoleList.add(adminRoleMap);
                }
                Map dataMap = new HashMap<>();
                dataMap.put("list", adminRoleList);
                sqlSessionTemplate.insert("system.admin.role.batchSave", dataMap);
            }
            saveSystemLog(message + "管理员" + params.get("login_name"));
            return new ResultCode(ResultCode.SUCCESS, message + "管理员成功");
        } catch (Exception e) {
            log.error(message + "管理员异常", e);
        }
        return new ResultCode(ResultCode.FAIL, "添加管理员失败");
    }
}
