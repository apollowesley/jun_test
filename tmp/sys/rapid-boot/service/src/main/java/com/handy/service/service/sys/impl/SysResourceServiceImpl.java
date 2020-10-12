package com.handy.service.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.sys.SysResource;
import com.handy.service.mapper.sys.SysResourceMapper;
import com.handy.service.service.sys.ISysResourceService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> findByIdList(List<Long> idList) {
        val wrapper = new QueryWrapper<SysResource>();
        LambdaQueryWrapper<SysResource> queryWrapper = wrapper.lambda();
        if (CollUtil.isNotEmpty(idList)) {
            queryWrapper.in(SysResource::getId, idList);
        }
        return sysResourceMapper.selectList(wrapper);
    }
}
