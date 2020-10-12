package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.EmployeesPositions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface EmployeesPositionsMapper extends BaseMapper<EmployeesPositions> {
    int insertOrUpdate(EmployeesPositions record);

    int insertOrUpdateSelective(EmployeesPositions record);
}