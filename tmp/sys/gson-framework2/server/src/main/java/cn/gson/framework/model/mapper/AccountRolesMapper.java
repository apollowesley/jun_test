package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.AccountRoles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface AccountRolesMapper extends BaseMapper<AccountRoles> {
    int insertOrUpdate(AccountRoles record);

    int insertOrUpdateSelective(AccountRoles record);
}