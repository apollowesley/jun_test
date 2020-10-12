package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface RoleMapper extends BaseMapper<Role> {
    int insertOrUpdate(Role record);

    int insertOrUpdateSelective(Role record);
}