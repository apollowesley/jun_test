package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.AccountAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface AccountAuthorityMapper extends BaseMapper<AccountAuthority> {
    int insertOrUpdate(AccountAuthority record);

    int insertOrUpdateSelective(AccountAuthority record);
}