package com.slavic.hors.service.impl;

import com.slavic.hors.orm.entity.*;
import com.slavic.hors.orm.mapper.*;
import com.slavic.hors.service.PortalRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortalRoleServiceImpl implements PortalRoleService {

    @Resource
    private HorsPortalRoleMapper horsPortalRoleMapper;

    @Resource
    private HorsPortalUserRoleMapper horsPortalUserRoleMapper;

    @Resource
    private HorsPortalRoleResourceMapper horsPortalRoleResourceMapper;

    @Resource
    private HorsPortalUserMapper horsPortalUserMapper;

    @Resource
    private HorsPortalResourceMapper horsPortalResourceMapper;


    @Override
    public List<HorsPortalRole> list(HorsPortalRole query) {
        return horsPortalRoleMapper.queryAll(query);
    }

    @Override
    public List<HorsPortalRole> userRoles(Long userId) {
        HorsPortalUserRole query = new HorsPortalUserRole();
        query.setUserId(userId);
        List<HorsPortalUserRole> userRoles = horsPortalUserRoleMapper.queryAll(query);
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }
        return horsPortalRoleMapper.listByIds(userRoles.stream().map(HorsPortalUserRole::getRoleId).collect(Collectors.toList()));
    }

    @Override
    public HorsPortalRole update(HorsPortalRole user) {
        return horsPortalRoleMapper.updateById(user) == 1 ? user : null;
    }

    @Override
    public HorsPortalRole findById(Long id) {
        return horsPortalRoleMapper.queryById(id);
    }

    @Override
    public boolean reRoles(Long userId, List<Long> roleIds) {
        horsPortalUserRoleMapper.deleteByUserId(userId);
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                Date now = new Date();
                HorsPortalUserRole userRole = new HorsPortalUserRole();
                userRole.setRoleId(roleId);
                userRole.setCreateTime(now);
                userRole.setUpdateTime(now);
                userRole.setUserId(userId);
                horsPortalUserRoleMapper.insert(userRole);
            }
        }
        return true;
    }

    @Override
    public boolean reResources(Long roleId, List<Long> resourceIds) {
        horsPortalRoleResourceMapper.deleteByRoleId(roleId);
        if (!CollectionUtils.isEmpty(resourceIds)) {
            for (Long resourceId : resourceIds) {
                Date now = new Date();
                HorsPortalRoleResource horsPortalRoleResource = new HorsPortalRoleResource();
                horsPortalRoleResource.setCreateTime(now);
                horsPortalRoleResource.setUpdateTime(now);
                horsPortalRoleResource.setResourceId(resourceId);
                horsPortalRoleResource.setRoleId(roleId);
                horsPortalRoleResourceMapper.insert(horsPortalRoleResource);
            }
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        return horsPortalRoleMapper.deleteById(id) == 1;
    }

    @Override
    public boolean add(HorsPortalRole role) {
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        role.setHorsVersion(0);
        return horsPortalRoleMapper.insert(role) == 1;
    }

    @Override
    public List<HorsPortalUser> roleUsers(Long roleId) {
        HorsPortalUserRole query = new HorsPortalUserRole();
        query.setRoleId(roleId);
        List<HorsPortalUserRole> roleResources = horsPortalUserRoleMapper.queryAll(query);
        if (CollectionUtils.isEmpty(roleResources)) {
            return null;
        }
        return horsPortalUserMapper.listByIds(roleResources.stream().map(HorsPortalUserRole::getUserId).collect(Collectors.toList()));
    }

    @Override
    public List<HorsPortalRoleResource> roleResources(Long roleId) {
        HorsPortalRoleResource query = new HorsPortalRoleResource();
        query.setRoleId(roleId);
        return horsPortalRoleResourceMapper.queryAll(query);
    }

    @Override
    public List<HorsPortalResource> userResources(Long userId) {
        HorsPortalUserRole query = new HorsPortalUserRole();
        query.setUserId(userId);
        List<HorsPortalUserRole> userRoles = horsPortalUserRoleMapper.queryAll(query);
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }
        List<HorsPortalRoleResource> roleResources = horsPortalRoleResourceMapper.listByRolesId(userRoles.stream().map(HorsPortalUserRole::getRoleId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(roleResources)) {
            return null;
        }
        return horsPortalResourceMapper.listByIds(roleResources.stream().map(HorsPortalRoleResource::getResourceId).collect(Collectors.toList()));
    }
}
