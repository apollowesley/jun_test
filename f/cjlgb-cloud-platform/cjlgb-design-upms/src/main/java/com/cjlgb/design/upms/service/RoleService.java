package com.cjlgb.design.upms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.SysRole;
import com.cjlgb.design.upms.constant.BasicData;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
public interface RoleService extends BaseService<SysRole> {

    /**
     * 查询角色列表
     * @param parameter 查询条件
     * @return java.util.List
     */
    default List<SysRole> selectList(SysRole parameter){
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        //  过滤基础数据
        wrapper.ne(SysRole::getId, BasicData.SUPER_ROLE_ID);
        if (null != parameter){
            //  根据名称模糊查询
            if (StringUtils.hasText(parameter.getRoleName())){
                wrapper.likeRight(SysRole::getRoleName,parameter.getRoleName());
            }
        }
        return this.list(wrapper);
    }

    /**
     * 创建系统角色
     * @param parameter com.cjlgb.design.common.upms.entity.SysRole
     */
    default void create(SysRole parameter){
        //  确保角色名称不存在
        int count = this.count(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleName,parameter.getRoleName()));
        Assert.isTrue(count == 0,"角色名称已存在");
        //  创建角色
        this.save(parameter);
    }

    /**
     * 修改系统角色
     * @param parameter com.cjlgb.design.common.upms.entity.SysRole
     */
    default void update(SysRole parameter){
        //  确保角色名称不存在
        int count = this.count(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName,parameter.getRoleName())
                .ne(SysRole::getId,parameter.getId()));
        Assert.isTrue(count == 0,"角色名称已存在");
        //  保存修改
        this.updateById(parameter);
    }

    /**
     * 删除系统角色
     * @param roleId java.lang.Long
     */
    void delete(Serializable roleId);
}
