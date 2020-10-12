package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.RoleAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {
    int insertOrUpdate(RoleAuthority record);

    int insertOrUpdateSelective(RoleAuthority record);
}