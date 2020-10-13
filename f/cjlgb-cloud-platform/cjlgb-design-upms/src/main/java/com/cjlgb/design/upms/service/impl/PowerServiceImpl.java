package com.cjlgb.design.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.core.util.TreeUtils;
import com.cjlgb.design.common.mybatis.service.impl.BaseServiceImpl;
import com.cjlgb.design.common.upms.entity.SysPower;
import com.cjlgb.design.upms.constant.BasicData;
import com.cjlgb.design.upms.mapper.SysPowerMapper;
import com.cjlgb.design.upms.service.PowerService;
import com.cjlgb.design.upms.service.RolePowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
@Service
@RequiredArgsConstructor
public class PowerServiceImpl extends BaseServiceImpl<SysPowerMapper, SysPower> implements PowerService {

    private final RolePowerService rolePowerService;

    /**
     * 保证权限标识不存在
     * @param permission java.lang.String
     */
    private void ensurePermissionNotExist(String permission){
        int count = this.count(Wrappers.<SysPower>lambdaQuery().eq(SysPower::getPermission, permission));
        Assert.isTrue(count == 0,"权限标识已存在");
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void create(SysPower params) {
        //  确保权限标识不存在
        this.ensurePermissionNotExist(params.getPermission());
        //  默认父级Id为0
        if (null == params.getPid()){
            params.setPid(0L);
        }
        //  创建权限
        if (this.save(params)){
            //  将此权限授权于超级角色
            this.rolePowerService.create(BasicData.SUPER_ROLE_ID,params.getId(),params.getPermission());
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(SysPower params) {
        SysPower power = this.getById(params.getId());
        Assert.notNull(power,"Id不存在");
        if (null != params.getPid()) {
            Assert.isTrue(power.getPid().equals(params.getPid()), "不允许修改父级Id");
        }
        //  判断是否修改了权限标识
        if (!power.getPermission().equals(params.getPermission())){
            //  确保权限标识不存在
            this.ensurePermissionNotExist(params.getPermission());
            //  修改关联表
            this.rolePowerService.updatePermissionByPowerId(params.getId(),params.getPermission());
        }
        //  保存修改
        this.updateById(params);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long powerId) {
        //  获取当前权限的子权限Id列表
        Wrapper<SysPower> wrapper = Wrappers.<SysPower>lambdaQuery()
                //  只查询Id
                .select(SysPower::getId)
                .eq(SysPower::getPid,powerId)
                .or()
                .eq(SysPower::getId,powerId);
        List<Long> powerIds = this.list(wrapper).stream().map(SysPower::getId).collect(Collectors.toList());
        if (!powerIds.isEmpty()) {
            //  删除关联记录
            this.rolePowerService.deleteByPowerId(powerId);
            //  删除子节点
            this.removeByIds(powerIds);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<SysPower> selectListTree(Collection<String> authorities) {
        if (authorities.isEmpty()){
            return new ArrayList<>(0);
        }
        StringBuilder builder = new StringBuilder();
        authorities.forEach(item -> builder.append("'").append(item).append("'").append(","));
        List<SysPower> powerList = this.baseMapper.selectByAuthorities(builder.substring(0,builder.length() - 1));
        return null == powerList ? new ArrayList<>(0) : TreeUtils.getInstance().build(powerList);
    }
}
