package com.slavic.hors.service.impl;

import com.slavic.hors.orm.entity.HorsCommonFileDisk;
import com.slavic.hors.orm.mapper.HorsCommonFileDiskMapper;
import com.slavic.hors.service.CommonFileDiskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CommonFileDiskServiceImpl implements CommonFileDiskService {

    @Resource
    private HorsCommonFileDiskMapper horsCommonFileDiskMapper;

    @Override
    public Long storeFile(String fileName, String context) {
        HorsCommonFileDisk file = new HorsCommonFileDisk();
        file.setCreateTime(new Date());
        file.setFileContent(context);
        file.setFileName(fileName);
        file.setFileStatus(0);
        file.setFileType("PORTAL_AVATAR");
        int insert = horsCommonFileDiskMapper.insert(file);
        return insert == 1 ? file.getId() : null;
    }

    @Override
    public HorsCommonFileDisk receiveFile(Long id) {
        return horsCommonFileDiskMapper.queryById(id);
    }
}
