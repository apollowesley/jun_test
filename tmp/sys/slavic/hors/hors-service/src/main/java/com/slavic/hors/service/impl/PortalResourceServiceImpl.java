package com.slavic.hors.service.impl;

import com.slavic.hors.orm.entity.HorsPortalResource;
import com.slavic.hors.orm.mapper.HorsPortalResourceMapper;
import com.slavic.hors.service.PortalResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PortalResourceServiceImpl implements PortalResourceService {

    @Resource
    private HorsPortalResourceMapper horsPortalResourceMapper;

    @Override
    public List<HorsPortalResource> list(HorsPortalResource query) {
        return horsPortalResourceMapper.queryAll(query);
    }
}
