package com.baomidou.crab.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.crab.common.ErrorCode;
import com.baomidou.crab.common.utils.RegexUtils;
import com.baomidou.crab.sys.entity.Dict;
import com.baomidou.crab.sys.mapper.DictMapper;
import com.baomidou.crab.sys.service.IDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {


    @Override
    public IPage<Dict> page(Page page, Dict dict) {
        // 当 PID 非 0 时候查询字典子属性
        if (null == dict.getPid()) {
            dict.setPid(0L);
        }
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        if (RegexUtils.isEnglish(dict.getName())) {
            dict.setInitial(dict.getName());
            dict.setName(null);
        }
        qw.setEntity(dict);
        return super.page(page, qw);
    }

    @Override
    public List<Dict> listByParentCode(String parentCode) {
        return baseMapper.selectByParentCode(parentCode);
    }

    @Override
    public boolean save(Dict dict) {
        if (null == dict) {
            return false;
        }
        if (null != dict.getPid()) {
            Assert.fail(count(Wrappers.<Dict>lambdaQuery().eq(Dict::getCode,
                    dict.getCode()).eq(Dict::getPid, dict.getPid())) > 0, "保存失败，同级字典存在相同编码");
            Dict parentDict = getById(dict.getPid());
            Assert.fail(null == parentDict, "父字段异常，请联系管理员");
            dict.setSys(parentDict.getSys());
        } else {
            Assert.fail(count(Wrappers.<Dict>lambdaQuery().eq(Dict::getCode,
                    dict.getCode()).eq(Dict::getPid, 0)) > 0, "保存失败，存在相同编码");
        }
        return super.save(dict.initialName());
    }


    @Override
    public boolean updateById(Dict dict) {
        Assert.fail(null == dict.getId(), ErrorCode.ID_REQUIRED);
        return super.updateById(dict);
    }


    @Override
    public boolean updateSys(Long id, Integer sys) {
        return update(new Dict().setSys(sys), Wrappers.<Dict>lambdaUpdate()
                .eq(Dict::getId, id).or().eq(Dict::getPid, id));
    }


    @Override
    public boolean updateStatus(Long id, Integer status) {
        Dict dict = new Dict();
        dict.setId(id);
        dict.setStatus(status);
        return updateById(dict);
    }

    @Override
    public boolean removeById(Serializable id) {
        Assert.fail(count(Wrappers.<Dict>query().select("count(1)")
                        .eq("pid", id)) > 0, "请先删除子属性后操作");
        return super.removeById(id);
    }
}
