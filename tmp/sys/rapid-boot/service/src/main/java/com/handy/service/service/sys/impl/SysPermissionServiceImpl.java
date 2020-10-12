package com.handy.service.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.sys.SysPermission;
import com.handy.service.mapper.sys.SysPermissionMapper;
import com.handy.service.service.sys.ISysPermissionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 根据角色列表查询对应的权限
     *
     * @param roleIdList
     * @return
     */
    @Override
    public List<SysPermission> findByRoleIdList(List<Long> roleIdList) {
        val wrapper = new QueryWrapper<SysPermission>();
        LambdaQueryWrapper<SysPermission> queryWrapper = wrapper.lambda();
        if (CollUtil.isNotEmpty(roleIdList)) {
            queryWrapper.in(SysPermission::getRoleId, roleIdList);
        }
        return sysPermissionMapper.selectList(wrapper);
    }
}
