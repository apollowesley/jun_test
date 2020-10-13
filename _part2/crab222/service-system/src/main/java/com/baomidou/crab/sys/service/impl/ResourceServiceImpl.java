package com.baomidou.crab.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.entity.Resource;
import com.baomidou.crab.sys.mapper.ResourceMapper;
import com.baomidou.crab.sys.service.IResourceService;
import com.baomidou.crab.sys.service.IRoleResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统资源表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-09-13
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private IRoleResourceService roleResourceService;

    @Override
    public IPage<Resource> page(Page page, Resource resource) {
        QueryWrapper<Resource> qw = new QueryWrapper<>();
        if (RegexUtils.isEnglish(resource.getName())) {
            resource.setInitial(resource.getName());
            resource.setName(null);
        }
        qw.setEntity(resource);
        return super.page(page, qw);
    }

    @Override
    public List<Resource> listMenuByUserId(Long userId) {
        return baseMapper.selectMenuByUserId(userId);
    }

    @Override
    public List<Resource> listEffective() {
        return list(Wrappers.<Resource>lambdaQuery().select(Resource::getId,
                Resource::getName).eq(Resource::getStatus, 0));
    }

    @Override
    public boolean changeStatus(Long id, Integer status) {
        Resource resource = new Resource();
        resource.setId(id);
        resource.setStatus(status.intValue() > -1 ? 0 : -1);
        return updateById(resource);
    }

    @Override
    public boolean save(Resource resource) {
        if (null == resource) {
            return false;
        }
        return super.save(resource.initialName());
    }

    @Override
    public boolean removeById(Serializable id) {
        Assert.fail(roleResourceService.relation(id), "存在关联角色操作无效");
        return super.removeById(id);
    }
}
