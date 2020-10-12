package cn.gson.framework.model.mapper;

import cn.gson.framework.model.pojo.Employees;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
* Created by Mybatis Generator 2019/03/01
*/
public interface EmployeesMapper extends BaseMapper<Employees> {
    int insertOrUpdate(Employees record);

    int insertOrUpdateSelective(Employees record);

    IPage<Employees> selectPageVo(IPage<Employees> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
