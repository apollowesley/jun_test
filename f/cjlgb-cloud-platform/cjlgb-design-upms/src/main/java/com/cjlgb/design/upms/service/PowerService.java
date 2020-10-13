package com.cjlgb.design.upms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.SysPower;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
public interface PowerService extends BaseService<SysPower> {

    /**
     * 创建权限
     * @param params com.cjlgb.design.common.upms.entity.SysPower
     */
    void create(SysPower params);

    /**
     * 修改权限
     * @param params com.cjlgb.design.common.upms.entity.SysPower
     */
    void update(SysPower params);

    /**
     * 删除权限
     * @param powerId java.lang.Long
     */
    void delete(Long powerId);

    /**
     * 查询权限列表
     * @param parameter 查询条件
     * @return java.util.List
     */
    default List<SysPower> selectList(SysPower parameter){
        LambdaQueryWrapper<SysPower> wrapper = Wrappers.lambdaQuery();
        if (null != parameter){
            //  根据名称模糊查询
            if (StringUtils.hasText(parameter.getName())){
                wrapper.likeRight(SysPower::getName,parameter.getName());
            }
            //  根据标识模糊查询
            if (StringUtils.hasText(parameter.getPermission())){
                wrapper.likeRight(SysPower::getPermission,parameter.getPermission());
            }
            //  根据路径模糊查询
            if (StringUtils.hasText(parameter.getPath())){
                wrapper.likeRight(SysPower::getPath,parameter.getPath());
            }
            //  查询子节点列表
            if (null != parameter.getPid()){
                wrapper.eq(SysPower::getPid,parameter.getPid());
            }
            //  根据类型查询
            if (null != parameter.getType()){
                wrapper.eq(SysPower::getType,parameter.getType().getValue());
            }
        }
        return this.list(wrapper);
    }

    /**
     * 根据权限列表查询可见的系统权限
     * @param authorities 权限列表
     * @return java.util.List
     */
    List<SysPower> selectListTree(Collection<String> authorities);

    /**
     * 根据权限表示获取权限列表
     * @param authorities 权限标识列表
     * @return java.util.List
     */
    default List<SysPower> selectListByAuthorities(Collection<String> authorities){
        return this.list(Wrappers.<SysPower>lambdaQuery().in(SysPower::getPermission,authorities));
    }
}
