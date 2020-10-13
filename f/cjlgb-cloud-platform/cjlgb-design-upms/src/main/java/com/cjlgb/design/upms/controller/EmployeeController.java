package com.cjlgb.design.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjlgb.design.common.core.bean.Validation;
import com.cjlgb.design.common.security.annotation.PreAuthorize;
import com.cjlgb.design.common.security.context.SecurityContextHolder;
import com.cjlgb.design.common.upms.entity.Employee;
import com.cjlgb.design.upms.service.EmployeeRoleService;
import com.cjlgb.design.upms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author WFT
 * @date 2020/7/19
 * description:员工控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRoleService employeeRoleService;

    /**
     * 获取当前用户信息
     * @return com.cjlgb.design.common.upms.entity.Employee
     */
    @PreAuthorize
    @GetMapping(value = "/getMyInfo")
    public Employee getMyInfo(){
        Long userId = SecurityContextHolder.getAuthentication().getId();
        return this.employeeService.getById(userId);
    }

    /**
     * 分页查询员工列表
     * @param page 分页条件
     * @param parameter 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     */
    @GetMapping
    @PreAuthorize
    public IPage<Employee> page(Page<Employee> page,Employee parameter){
        //  获取当前用户信息
        Employee employee = this.getMyInfo();
        //  只查询等级比自己低的员工
        parameter = null == parameter ? new Employee() : parameter;
        parameter.setGradeFlag(employee.getGradeFlag());
        //  分页查询
        return this.employeeService.pagingQuery(page,parameter);
    }

    /**
     * 添加员工
     * @param parameter com.cjlgb.design.common.upms.entity.Employee
     */
    @PostMapping
    @PreAuthorize
    public void add(@RequestBody @Validated Employee parameter){
        //  设置账户的等级
        parameter.setGradeFlag(this.getMyInfo().getGradeFlag() - 1);
        this.employeeService.create(parameter);
    }

    /**
     * 修改员工信息
     * @param parameter com.cjlgb.design.common.upms.entity.Employee
     */
    @PutMapping
    @PreAuthorize
    public void update(@RequestBody @Validated(value = Validation.Edit.class) Employee parameter){
        this.employeeService.update(parameter);
    }

    /**
     * 删除员工
     * @param id java.lang.Long
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize
    public void delete(@PathVariable(value = "id") Long id){
        this.employeeService.delete(id);
    }

    /**
     * 获取某员工的角色Id列表
     * @param id 员工Id
     * @return 角色Id列表
     */
    @GetMapping(value = "/getRoleIds/{id}")
    @PreAuthorize
    public Collection<Long> getRoleIds(@PathVariable(value = "id") Long id){
        return this.employeeRoleService.selectRoleIds(id);
    }

}
