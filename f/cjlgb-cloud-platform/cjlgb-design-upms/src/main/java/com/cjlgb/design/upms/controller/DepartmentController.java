package com.cjlgb.design.upms.controller;

import com.cjlgb.design.common.core.bean.Validation;
import com.cjlgb.design.common.security.annotation.PreAuthorize;
import com.cjlgb.design.common.upms.entity.Department;
import com.cjlgb.design.upms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WFT
 * @date 2020/7/26
 * description:部门控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * 查询部门列表
     * @return java.util.List
     */
    @GetMapping
    @PreAuthorize
    public List<Department> list(){
        return this.departmentService.selectList();
    }

    /**
     * 添加部门
     * @param parameter com.cjlgb.design.common.upms.entity.Department
     */
    @PostMapping
    public void add(@RequestBody @Validated Department parameter){
        this.departmentService.create(parameter);
    }

    /**
     * 修改部门
     * @param parameter com.cjlgb.design.common.upms.entity.Department
     */
    @PutMapping
    public void edit(@RequestBody @Validated(value = Validation.Edit.class) Department parameter){
        this.departmentService.update(parameter);
    }

    /**
     * 删除部门
     * @param deptId 部门Id
     */
    @DeleteMapping(value = "/{deptId}")
    public void delete(@PathVariable(value = "deptId") Long deptId){
        this.departmentService.delete(deptId);
    }
}
