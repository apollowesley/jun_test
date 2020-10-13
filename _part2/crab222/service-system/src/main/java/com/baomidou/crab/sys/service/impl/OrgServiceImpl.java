package com.baomidou.crab.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.crab.sys.entity.Org;
import com.baomidou.crab.sys.mapper.OrgMapper;
import com.baomidou.crab.sys.service.IOrgService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统机构表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-11-07
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {


    @Override
    public List<Org> listByCompanyId(Long companyId) {
        return super.list(Wrappers.<Org>query().select("id", "pid", "name", "remark")
                .eq("company_id", companyId).orderByDesc("sort"));
    }

    @Override
    public Integer childNode(Serializable id) {
        return count(Wrappers.<Org>lambdaQuery().eq(Org::getPid,
                id).eq(Org::getDeleted, 0));
    }

    @Override
    public boolean save(Org org) {
        if (null == org) {
            return false;
        }
        return super.save(org.initialName());
    }

    @Override
    public boolean removeById(Serializable id) {
        Assert.fail(this.childNode(id) > 0, "存在子节点不允许删除");
        return super.removeById(id);
    }
}
