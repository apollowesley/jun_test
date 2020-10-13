package com.baomidou.crab.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.crab.common.ErrorCode;
import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.entity.Param;
import com.baomidou.crab.sys.mapper.ParamMapper;
import com.baomidou.crab.sys.service.IParamService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统参数表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
@Service
public class ParamServiceImpl extends ServiceImpl<ParamMapper, Param> implements IParamService {

    @Override
    public IPage<Param> page(Page page, Param param) {
        QueryWrapper<Param> qw = new QueryWrapper();
        // 首字母查询
        if (RegexUtils.isEnglish(param.getName())) {
            param.setInitial(param.getName());
            param.setName(null);
        }
        qw.setEntity(param);
        return super.page(page, qw);
    }


    @Override
    public boolean save(Param param) {
        if (null == param) {
            return false;
        }
        return super.save(param.initialName());
    }


    @Override
    public boolean updateById(Param param) {
        Assert.fail(null == param.getId(), ErrorCode.ID_REQUIRED);
        return super.updateById(param);
    }


    @Override
    public boolean updateSys(Long id, Integer sys) {
        Param param = new Param();
        param.setId(id);
        param.setSys(sys);
        return updateById(param);
    }
}
