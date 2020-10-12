package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface DepartmentMapper extends BaseMapper<Department> {
    int insertOrUpdate(Department record);

    int insertOrUpdateSelective(Department record);
}
