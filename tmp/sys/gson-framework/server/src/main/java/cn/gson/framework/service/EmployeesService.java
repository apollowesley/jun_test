package cn.gson.framework.service;

import cn.gson.framework.model.mapper.DepartmentMapper;
import cn.gson.framework.model.mapper.EmployeesDepartmentsMapper;
import cn.gson.framework.model.mapper.EmployeesMapper;
import cn.gson.framework.model.pojo.Department;
import cn.gson.framework.model.pojo.Employees;
import cn.gson.framework.model.pojo.EmployeesDepartments;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeesService extends ServiceImpl<EmployeesMapper, Employees> {

    DepartmentMapper departmentMapper;
    EmployeesDepartmentsMapper employeesDepartmentsMapper;

    @Override
    public IPage<Employees> page(IPage<Employees> page, Wrapper<Employees> wrapper) {
        return baseMapper.selectPageVo(page, wrapper);
    }

    @Override
    public boolean save(Employees entity) {
        //获取部门所在公司 ID
        Department department = departmentMapper.selectById(entity.getMasterDepartmentId());
        entity.setCompanyId(department.getCompanyId());

        boolean result = super.save(entity);
        //保存多对多关系
        EmployeesDepartments ed = new EmployeesDepartments();
        ed.setDepartmentId(entity.getMasterDepartmentId());
        ed.setEmployeesId(entity.getId());
        employeesDepartmentsMapper.insertOrUpdate(ed);
        return result;
    }
}
