package com.cjlgb.design.upms.service.impl;

import com.cjlgb.design.common.mybatis.service.impl.BaseServiceImpl;
import com.cjlgb.design.common.upms.entity.Department;
import com.cjlgb.design.upms.mapper.DepartmentMapper;
import com.cjlgb.design.upms.mapper.EmployeeMapper;
import com.cjlgb.design.upms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    private final EmployeeMapper employeeMapper;

    @Override
    public void create(Department parameter) {
        //  检测部门名称已经存在
        boolean isExist = this.isExistByName(parameter.getDeptName());
        Assert.isTrue(!isExist,"部门名称已存在");
        if (null == parameter.getSort()){
            parameter.setSort(Integer.MAX_VALUE);
        }
        //  保存数据
        this.save(parameter);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(Department parameter) {
        //  判断部门Id是否存在
        Department department = this.getById(parameter.getId());
        Assert.notNull(department,"部门Id不存在");
        //  修改名称字段?
        if (!department.getDeptName().equals(parameter.getDeptName())){
            //  检查部门名称是否存在
            boolean isExist = this.isExistByName(parameter.getDeptName());
            Assert.isTrue(!isExist,"部门名称已存在");
            //  修改关联记录
            this.employeeMapper.updateDeptName(parameter.getId(),parameter.getDeptName());
        }
        //  保存更改
        this.updateById(parameter);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Serializable deptId) {
        Long count = this.employeeMapper.selectCountByDeptId(deptId);
        Assert.isTrue(count == 0,"部门下还有员工,无法删除!");
        //  删除部门
        this.removeById(deptId);
    }


}
