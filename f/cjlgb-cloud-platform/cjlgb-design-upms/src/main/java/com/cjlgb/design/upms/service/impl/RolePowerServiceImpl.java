package com.cjlgb.design.upms.service.impl;

import com.cjlgb.design.common.mybatis.service.impl.BaseServiceImpl;
import com.cjlgb.design.common.upms.entity.SysPower;
import com.cjlgb.design.common.upms.entity.SysRolePower;
import com.cjlgb.design.upms.mapper.SysRolePowerMapper;
import com.cjlgb.design.upms.service.RolePowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
@Service
@RequiredArgsConstructor
public class RolePowerServiceImpl extends BaseServiceImpl<SysRolePowerMapper,SysRolePower> implements RolePowerService {

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(Long roleId, List<SysPower> powerList) {
        //  删除当前角色的所有原关联记录
        this.deleteByRoleId(roleId);
        if (powerList.isEmpty()){
            return;
        }
        //  生成新的关联记录
        List<SysRolePower> list = new ArrayList<>(powerList.size());
        powerList.forEach(item -> list.add(new SysRolePower(roleId,item.getId(),item.getPermission())));
        //  批量保存新的关联记录
        this.saveBatch(list,50);
    }
}
