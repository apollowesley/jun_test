package com.jfast.web.api.admin.service;

import com.jfast.web.api.admin.mapper.SystemRoleMapper;
import com.jfast.web.common.core.base.BaseService;

import com.jfast.web.common.core.model.Result;
import com.jfast.web.common.core.utils.MapTreeUtils;
import com.jfast.web.common.core.utils.ResultCode;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色管理业务层
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/28 14:09
 */
@Service
public class SystemRoleService extends BaseService<SystemRoleMapper> {

    public Result getMenuByUserRole(SystemSecurityUser systemSecurityUser) {
        Result result = new Result(ResultCode.SUCCESS);
        try {
            List<Map> menuList = systemSecurityUser.getMenuList();
            result.setData(MapTreeUtils.buildTreeData(menuList));
        } catch (Exception e) {
            logger.error("获取用户菜单异常", e);
            result.setCode(ResultCode.FAIL);
        }
        return result;
    }
}
