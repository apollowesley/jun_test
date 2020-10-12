package com.leo.vhr.mapper;

import com.leo.vhr.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentsById(Integer pid);

    void addDep(Department dep);

    void delDepById(Department department);

    List<Department> getAllDepartmentsWithOutChildren();
}