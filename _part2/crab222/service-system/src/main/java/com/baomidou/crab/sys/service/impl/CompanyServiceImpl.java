package com.baomidou.crab.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.crab.common.ErrorCode;
import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.entity.Company;
import com.baomidou.crab.sys.mapper.CompanyMapper;
import com.baomidou.crab.sys.service.ICompanyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * <p>
 * 系统公司表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-10-21
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {


    @Override
    public IPage<Company> page(Page page, Company company) {
        QueryWrapper<Company> qw = new QueryWrapper<>();
        if (RegexUtils.isEnglish(company.getName())) {
            company.setInitial(company.getName());
            company.setName(null);
        }
        qw.setEntity(company);
        return super.page(page, qw);
    }


    @Override
    public List<Company> listEffective() {
        return list(Wrappers.<Company>lambdaQuery().select(Company::getId,
                Company::getName).eq(Company::getStatus, 0));
    }


    @Override
    public boolean save(Company company) {
        if (null == company) {
            return false;
        }
        return super.save(company.initialName());
    }


    @Override
    public boolean updateById(Company company) {
        Assert.fail(null == company.getId(), ErrorCode.ID_REQUIRED);
        return super.updateById(company);
    }


    @Override
    public boolean updateStatus(Long id, Integer status) {
        Company company = new Company();
        company.setId(id);
        company.setStatus(status);
        return updateById(company);
    }
}
