package com.cjlgb.design.upms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.Employee;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
public interface EmployeeService extends BaseService<Employee> {

    /**
     * 根据用户Id获取员工信息
     * @param userId java.io.Serializable
     * @return com.cjlgb.design.common.upms.entity.Employee
     */
    default Employee selectByUserId(Serializable userId){
        return this.getOne(Wrappers.<Employee>lambdaQuery().eq(Employee::getUserId,userId));
    }

    /**
     * 获取员工权限
     * @param id java.io.Serializable
     * @return java.util.Collection
     */
    Collection<String> selectAuthorities(Serializable id);

    /**
     * 根据用户名获取员工信息
     * @param userMobile java.lang.String
     * @return com.cjlgb.design.common.upms.entity.Employee
     */
    default Employee selectByUserMobile(String userMobile){
        return this.getOne(Wrappers.<Employee>lambdaQuery().eq(Employee::getUserMobile,userMobile));
    }

    /**
     * 分页查询员工列表
     * @param page 分页条件
     * @param parameter 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     */
    default IPage<Employee> pagingQuery(Page<Employee> page, Employee parameter){
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        //  不查询密码字段
        wrapper.select(Employee.class,employee -> !"password".equals(employee.getColumn()));
        //  查询低等级员工
        wrapper.lt(Employee::getGradeFlag,parameter.getGradeFlag());
        //  根据部门Id查询
        if (null != parameter.getDeptId() && 0 != parameter.getDeptId()){
            wrapper.eq(Employee::getDeptId,parameter.getDeptId());
        }
        //  根据锁定标记查询
        if (null != parameter.getLockFlag()){
            wrapper.eq(Employee::getLockFlag,parameter.getLockFlag());
        }
        //  根据员工用户名称模糊查询
        if (StringUtils.hasText(parameter.getUsername())){
            wrapper.likeRight(Employee::getUsername,parameter.getUsername());
        }
        //  根据手机号码模糊查询
        if (StringUtils.hasText(parameter.getUserMobile())){
            wrapper.likeRight(Employee::getUserMobile,parameter.getUserMobile());
        }
        return this.page(page,wrapper);
    }

    /**
     * 删除员工
     * @param id java.lang.Long
     */
    void delete(Long id);

    /**
     * 创建员工
     * @param parameter com.cjlgb.design.common.upms.entity.Employee
     */
    void create(Employee parameter);

    /**
     * 修改员工信息
     * @param parameter com.cjlgb.design.common.upms.entity.Employee
     */
    void update(Employee parameter);
}
