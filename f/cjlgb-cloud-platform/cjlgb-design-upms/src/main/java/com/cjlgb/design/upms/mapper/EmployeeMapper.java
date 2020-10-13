package com.cjlgb.design.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjlgb.design.common.upms.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 修改部门名称字段
     * @param deptId 部门Id
     * @param deptName 部门名称
     */
    @Select(value = "UPDATE employee SET dept_name = #{deptName} WHERE dept_id = #{deptId}")
    void updateDeptName(@Param(value = "deptId") Serializable deptId,@Param(value = "deptName") String deptName);

    /**
     * 查询某部门下的员工数量
     * @param deptId 部门Id
     * @return java.lang.Long
     */
    @Select(value = "SELECT COUNT(1) FROM employee WHERE dept_id = #{deptId}")
    Long selectCountByDeptId(@Param(value = "deptId")Serializable deptId);

}
