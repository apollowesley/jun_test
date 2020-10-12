package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.DepartmentLeaders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface DepartmentLeadersMapper extends BaseMapper<DepartmentLeaders> {
    int insertOrUpdate(DepartmentLeaders record);

    int insertOrUpdateSelective(DepartmentLeaders record);
}