package com.leo.vhr.service;

import com.leo.vhr.mapper.DepartmentMapper;
import com.leo.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/18
 * @version: 1.0
 */
@Service
public class DepartmentService
{
    @Autowired
    DepartmentMapper departmentMapper;
    public List<Department> getAllDepByParentId()
    {
        return departmentMapper.getAllDepartmentsById(-1);
    }

    public void addDep(Department dep)
    {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    public void deleteDepById(Department dep)
    {
        departmentMapper.delDepById(dep);
    }
    public List<Department> getAllDepartmentsWithOutChildren()
    {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
