package com.cjlgb.design.upms.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.Department;

import java.io.Serializable;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
public interface DepartmentService extends BaseService<Department> {

    /**
     * 创建部门
     * @param parameter com.cjlgb.design.common.upms.entity.Department
     */
    void create(Department parameter);

    /**
     * 修改部门
     * @param parameter com.cjlgb.design.common.upms.entity.Department
     */
    void update(Department parameter);

    /**
     * 删除部门
     * @param deptId 部门Id
     */
    void delete(Serializable deptId);

    /**
     * 查询部门列表
     * @return java.util.List
     */
    default List<Department> selectList(){
        return this.list(Wrappers.<Department>lambdaQuery()
                .select(Department.class,dept -> !"create_time".equals(dept.getColumn()) && !"last_modify_time".equals(dept.getColumn()))
                .orderByAsc(Department::getSort)
                .orderByAsc(Department::getId)
        );
    }

    /**
     * 根据名称判断是否已存在
     * @param deptName 部门名称
     * @return true:存在
     */
    default boolean isExistByName(String deptName){
        return this.count(Wrappers.<Department>lambdaQuery().eq(Department::getDeptName,deptName)) > 0;
    }
}
