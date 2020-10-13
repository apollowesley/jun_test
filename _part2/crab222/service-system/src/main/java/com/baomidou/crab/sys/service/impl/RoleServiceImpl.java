package com.baomidou.crab.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.entity.Role;
import com.baomidou.crab.sys.mapper.RoleMapper;
import com.baomidou.crab.sys.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-09-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public IPage<Role> page(Page page, Role role) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if (RegexUtils.isEnglish(role.getName())) {
            role.setInitial(role.getName());
            role.setName(null);
        }
        qw.setEntity(role);
        return super.page(page, qw);
    }

    @Override
    public List<Role> listAll() {
        return super.list(Wrappers.<Role>query().select("id", "name")
                .eq("status", 0).orderByDesc("sort"));
    }

    @Override
    public boolean save(Role role) {
        if (null == role) {
            return false;
        }
        return super.save(role.initialName());
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status.intValue() > -1 ? 0 : -1);
        return updateById(role);
    }
}
