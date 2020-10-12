package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface AccountMapper extends BaseMapper<Account> {
    int insertOrUpdate(Account record);

    int insertOrUpdateSelective(Account record);
}