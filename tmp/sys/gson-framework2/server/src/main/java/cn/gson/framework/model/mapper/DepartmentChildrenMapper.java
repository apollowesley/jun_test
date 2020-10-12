package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.DepartmentChildren;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface DepartmentChildrenMapper extends BaseMapper<DepartmentChildren> {
    int insertOrUpdate(DepartmentChildren record);

    int insertOrUpdateSelective(DepartmentChildren record);
}