package com.jfast.web.admin.service.system;


import com.jfast.common.exception.BusinessException;
import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import com.jfast.web.admin.service.AdminApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:57
 */
@Service
@Slf4j
public class SystemRoleService extends AdminApiService {

    /**
     * 获取角色拥有的菜单
     * @param roleId
     * @return
     */
    public List<Map> getMenuListByRole(List<Integer> roleId) {
        return sqlSessionTemplate.selectList("system.menu.getMenuByRoles", roleId);
    }

    /**
     * @param roleId
     * @return
     */
    public List<Integer> getMenuListByRoleId(Integer roleId) {
        List<Map> menuList = sqlSessionTemplate.selectList("system.role.menu.getByRoleId", roleId);
        List<Integer> ids = new ArrayList<>();
        for (Map menu : menuList) {
            Integer menuId = (Integer)menu.get("id");
            if (!((Integer)menu.get("parent_id") == ResultCode.FAIL)) {//是否父级菜单
                List<Map> list = sqlSessionTemplate.selectList("system.menu.list.findByParentId", menuId);
                if (ObjectUtils.isEmpty(list)) {
                    ids.add(menuId);
                }
            }
        }
        return ids;
    }

    /**
     * 保存角色权限
     * @param params
     * @return
     */
    @Transactional
    public ResultCode savePermission(Map params) {
        try {
            sqlSessionTemplate.delete("system.role.menu.deleteByRoleId", params.get("roleId")); //先删除角色原有的权限
            String permission = (String)params.get("permission");
            if (ObjectUtils.isNotEmpty(permission)) {
                String permissions[] = permission.split(",");
                List<Map> list = new ArrayList<>();
                Integer roleId = (Integer)params.get("roleId");
                for (String item : permissions) {
                    Map map = new HashMap<>();
                    map.put("menuId", item);
                    map.put("roleId", roleId);
                    list.add(map);
                }
                Map dataMap = new HashMap<>();
                dataMap.put("list", list);
                int result = sqlSessionTemplate.insert("system.role.menu.batchSave", dataMap);
                if (result > 0) {
                    return new ResultCode(ResultCode.SUCCESS, "权限设置成功");
                }
                return new ResultCode(ResultCode.FAIL, "权限设置失败");
            }
            return new ResultCode(ResultCode.SUCCESS, "权限设置成功");
        } catch (Exception e) {
            log.error("权限设置异常", e);
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "权限设置异常"));
        }
    }
}
