package com.baomidou.crab.sys.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.crab.sys.dto.RoleResourceDTO;
import com.baomidou.crab.sys.entity.RoleResource;
import com.baomidou.crab.sys.mapper.RoleResourceMapper;
import com.baomidou.crab.sys.service.IRoleResourceService;
import com.baomidou.crab.sys.vo.ResourceZTreeVO;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统角色资源关联表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-09-24
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

    @Override
    public List<ResourceZTreeVO> listZTreeVO(Long roleId) {
        return baseMapper.selectZTreeVO(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveByDto(RoleResourceDTO dto) {
        if (null == dto || null == dto.getRoleId()) {
            return false;
        }
        if (CollectionUtils.isEmpty(dto.getResourceIds())) {
            return removeByRoleId(dto.getRoleId());
        }
        return removeByRoleId(dto.getRoleId()) && saveBatch(dto.getResourceIds()
                .stream().map(r -> {
                    RoleResource roleResource = new RoleResource();
                    roleResource.setRoleId(dto.getRoleId());
                    roleResource.setResourceId(r);
                    return roleResource;
                }).collect(Collectors.toList()));
    }

    private boolean removeByRoleId(Long roleId) {
        return remove(Wrappers.<RoleResource>query().eq("role_id", roleId));
    }

    @Override
    public boolean relation(Serializable resourceId) {
        return count(Wrappers.<RoleResource>lambdaQuery().eq(RoleResource::getResourceId,
                resourceId)) > 0;
    }
}
