package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.DepartmentAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface DepartmentAuthorityMapper extends BaseMapper<DepartmentAuthority> {
    int insertOrUpdate(DepartmentAuthority record);

    int insertOrUpdateSelective(DepartmentAuthority record);
}