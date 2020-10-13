package com.cjlgb.design.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.core.util.StrUtils;
import com.cjlgb.design.common.mybatis.service.impl.BaseServiceImpl;
import com.cjlgb.design.common.upms.entity.Department;
import com.cjlgb.design.common.upms.entity.Employee;
import com.cjlgb.design.upms.mapper.EmployeeMapper;
import com.cjlgb.design.upms.service.DepartmentService;
import com.cjlgb.design.upms.service.EmployeeRoleService;
import com.cjlgb.design.upms.service.EmployeeService;
import com.cjlgb.design.upms.service.RolePowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends BaseServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    private final EmployeeRoleService employeeRoleService;
    private final RolePowerService rolePowerService;
    private final DepartmentService departmentService;

    @Override
    public Collection<String> selectAuthorities(Serializable id) {
        Collection<Long> roleIds = this.employeeRoleService.selectRoleIds(id);
        if (roleIds.isEmpty()){
            return new ArrayList<>(0);
        }
        return this.rolePowerService.selectAuthorities(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id) {
        if (this.removeById(id)){
            //  删除关联
            this.employeeRoleService.deleteByEmployId(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void create(Employee parameter) {
        //  校验手机号码是否已存在
        Assert.isTrue(null == this.selectByUserMobile(parameter.getUserMobile()),"手机号码已存在");
        //  获取部门信息
        Department department = this.departmentService.getById(parameter.getDeptId());
        Assert.notNull(department,"部门Id不存在");
        parameter.setDeptName(department.getDeptName());
        //  初始化密码 - 手机号码后8位
        parameter.setPassword(StrUtils.getInstance().md5(parameter.getUserMobile().substring(3),""));
        //  创建员工
        if (this.save(parameter)){
            //  创建与角色间的关联
            this.employeeRoleService.create(parameter.getId(),parameter.getRoleIdList());
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(Employee parameter) {
        //  获取修改前的数据
        Employee dbEmployee = this.getById(parameter.getId());
        Assert.notNull(dbEmployee,"员工Id不存在");
        //  禁止修改手机号码,账号等级,密码
        parameter.setUserMobile(dbEmployee.getUserMobile());
        parameter.setGradeFlag(dbEmployee.getGradeFlag());
        parameter.setPassword(dbEmployee.getPassword());
        //  判断是否修改了部门
        if (!dbEmployee.getDeptId().equals(parameter.getDeptId())){
            Department department = this.departmentService.getById(parameter.getDeptId());
            Assert.notNull(department,"部门Id不存在");
            parameter.setDeptName(department.getDeptName());
        }
        //  保存修改
        if (this.updateById(parameter)){
            //  修改与角色间的关联
            this.employeeRoleService.update(parameter.getId(),parameter.getRoleIdList());
        }
    }
}
