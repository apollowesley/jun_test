package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.EmployeesDepartments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface EmployeesDepartmentsMapper extends BaseMapper<EmployeesDepartments> {
    int insertOrUpdate(EmployeesDepartments record);

    int insertOrUpdateSelective(EmployeesDepartments record);
}